package scenes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.sql.*;



//import java.awt.datatransfer.*;
//import java.awt.Toolkit;

/**
 * Controller class for the login page
 * Maybe should be renamed
 */
public class Controller {

    ArrayList<TrackedSub> tSubs = new ArrayList<>();
    ArrayList<UntrackedSub> uSubs = new ArrayList<>();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="channelInputBox"
    private TextField channelInputBox; // Value injected by FXMLLoader

    @FXML // fx:id="startButton"
    private Button startButton; // Value injected by FXMLLoader

    @FXML // fx:id="warningLabel"
    private Label warningLabel; // Value injected by FXMLLoader

    @FXML // fx:id="inputGridPane"
    private GridPane inputGridPane; // Value injected by FXMLLoader

    @FXML // fx:id="title"
    private Label title; // Value injected by FXMLLoader

    @FXML // fx:id="inputPane"
    private AnchorPane inputPane; // Value injected by FXMLLoader

    @FXML
    private Label loadLabel;

    public String userID;

    private Scene subListScene;

    private SubListController subListCont;

    /**
     * @param scene JavaFX scene for the list of channels scraped
     * @param subCont controller for the sublist scene
     */
    public void setSubListScene(Scene scene, SubListController subCont) {
        subListScene = scene;
        subListCont = subCont;
    }// end setSubListScene

    /**
     * @param mouseEvent when the go button is clicked
     */
    public void buttonClicked(MouseEvent mouseEvent) {
        String URL = channelInputBox.getText();

        if(channelInputBox.getText().isEmpty()){
            URL = "https://www.youtube.com/channel/UCmDyVjHsavnb35lFW4Vms8w/channels?view=56&shelf_id=0";
        }
        else{
            URL = URL.substring(0 , URL.lastIndexOf('/') + 1 ) + "channels?view=56&shelf_id=0";
        }
        //String URL = "https://www.youtube.com/channel/UCmDyVjHsavnb35lFW4Vms8w/channels?view=56&shelf_id=0";
        //URL = "https://www.youtube.com/channel/UCb81rLqF7RVbnqmOEO0IGMg/channels?view=56&shelf_id=0";

        if (URL.split("(/channel[/(s?)])").length == 3) {
            System.out.println("Loading...");
            checkChannelInput(URL, mouseEvent);
        } else {
            warningLabel.setText("Sorry, your input was not valid, please try again.");
        }

    }// end buttonClicked

    /**
     * scrapes annoyingly complex youtube page to find urls for subscribed channels
     * @param URL the url of the user's youtube channel, which has been checked already
     */
    private void checkChannelInput(String URL, MouseEvent mouseEvent){
        try {
            //splits channel url to just contain '/channel/<channelID>'
            userID = URL.split("(/channel[/(s?)])")[1];

            Document userChannel = Jsoup.connect(URL).userAgent("Chrome/66.0.3359.139").get();

            //skips over the channels in sidebar
            Elements subURLs = userChannel.select("a[href]:not([title])");

            //tells user what is happening
            warningLabel.setText("Loading data from YouTube and SocialBlade...");
            for (Element subURL : subURLs) {
                addChannel(subURL.attr("href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        warningLabel.setText("Creating tables in database...");

        // create tables in database
        saveToDataBase();

        subListCont.addTableRows();

        // switching scenes to show subList
        Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(subListScene);
    }// end checkChannelInput

    public void addChannel(String channelID){
        if (channelID.contains("/channel/") && !channelID.contains(userID)) {

            //System.out.println(channelID);

            Document channelData = null;
            try {
                channelData = Jsoup.connect("https://socialblade.com/youtube" + channelID).userAgent("Chrome/66.0.3359.139").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element subName;
            try {
                subName = channelData.select("#YouTubeUserTopInfoBlockTop > div:nth-child(1) > h1").first();

                tSubs.add(new TrackedSub(channelID, subName.text(), channelData));

            } catch (NullPointerException i) {
                //System.out.println("\nSorry, this channel is not tracked by socialblade");

                uSubs.add(new UntrackedSub(channelID));
            }// end try catch block

        }// end checking href text
    }

    /**
     * saves the list of channels to the database
     */
    public void saveToDataBase(){
        String DB_URL = "jdbc:mysql://db4free.net:3306/ytsubanalyzer?autoReconnect=true&useSSL=false";
        String USER = "jsipprell";
        String PASSWORD = "CMa9d*UVHrJr!2s7";

        try
        {
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            deleteTables(conn);

            createTables(conn);

            //add tracked subs to db
            for (TrackedSub ts: tSubs) {
                addTrackedSub(conn, ts);
            }

            //add untracked subs to db
            for (UntrackedSub us: uSubs) {
                addUntrackedSub(conn, us);
            }

            conn.close();

            System.out.println("done");
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }// end saveToDataBase

    /**
     * deletes the tables already in the database
     * @param conn connection to db
     * @throws SQLException
     */
    public static void deleteTables(Connection conn) throws SQLException
    {
        Statement dropTables = conn.createStatement();
        String getGone = "DROP TABLE IF EXISTS Tracked_Subs";
        dropTables.executeUpdate(getGone);
        getGone = "DROP TABLE IF EXISTS UNTRACKED_SUBS";
        dropTables.executeUpdate(getGone);
    }// end deleteTables

    /**
     * creates a table of trackedSubs and a table of unTrackedSubs
     * @param conn connection to db
     * @throws SQLException
     */
    public static void createTables(Connection conn) throws SQLException
    {
        Statement addtTable = conn.createStatement();
        String createT = "CREATE TABLE Tracked_Subs " +
                " (Name VARCHAR(60), " +
                " userID CHAR(24), " +
                " Country CHAR(2), " +
                " Genre VARCHAR(50), " +
                " DateCreated Date, " +
                " SubCount INT, " +
                " ViewCount DECIMAL(19,0), " +
                " MinInc DOUBLE, " +
                " MaxInc DOUBLE, " +
                " PRIMARY KEY ( userID ))";

        Statement addUTable = conn.createStatement();
        String createU = "CREATE TABLE UNTRACKED_SUBS (userID CHAR(24), PRIMARY KEY (userID))";

        addtTable.executeUpdate(createT);
        addUTable.executeUpdate(createU);
    }// end createTable

    /**
     * fills out a row in the table of trackedSubs
     * @param conn connection to db
     * @param ts the TrackedSub currently being added
     * @throws SQLException
     */
    public static void addTrackedSub(Connection conn, TrackedSub ts) throws SQLException
    {
        Statement addRow = conn.createStatement();

        String row = "INSERT INTO Tracked_Subs VALUES " + ts.getTableRow();

        addRow.executeUpdate(row);
    } // end addTrackedSub

    /**
     * adds a row to table of untracked subs
     * @param conn connection to db
     * @param us untracked sub
     * @throws SQLException
     */
    public static void addUntrackedSub(Connection conn, UntrackedSub us) throws SQLException
    {
        Statement addRow = conn.createStatement();

        String row = "INSERT INTO UNTRACKED_SUBS VALUES ('" + us.getChannelID() + "')";

        addRow.executeUpdate(row);
    }//end addUntrackedSub

    /**
     * resets to aloow entry of a different URL
     */
    public void reset(){
        warningLabel.setVisible(false);
        tSubs.clear();
        uSubs.clear();
        warningLabel.setText("Please ensure that you have made your subscriptions public");
    }


    @FXML
    void initialize() {
        assert channelInputBox != null : "fx:id=\"channelInputBox\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert warningLabel != null : "fx:id=\"warningLabel\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputGridPane != null : "fx:id=\"inputGridPane\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputPane != null : "fx:id=\"inputPane\" was not injected: check your FXML file 'inputPage.fxml'.";
    }
}
