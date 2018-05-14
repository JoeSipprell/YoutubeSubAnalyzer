/**
 * Sample Skeleton for 'subList.fxml' Controller Class
 */

package scenes;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;

/**
 * controller for sublist javafx scene
 */
public class SubListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="naList"
    private ListView<String> naList; // Value injected by FXMLLoader

    @FXML // fx:id="notTrackedPane"
    private Pane notTrackedPane; // Value injected by FXMLLoader

    @FXML // fx:id="subListPane"
    private Pane subListPane; // Value injected by FXMLLoader

    @FXML // fx:id="channelTable"
    private TableView<TrackedSub> channelTable; // Value injected by FXMLLoader

        @FXML // fx:id="nameColumn"
        private TableColumn<TrackedSub, String> nameColumn; // Value injected by FXMLLoader

        @FXML // fx:id="subCountColumn"
        private TableColumn<TrackedSub, Number> subCountColumn; // Value injected by FXMLLoader

        @FXML // fx:id="viewsColumn"
        private TableColumn<TrackedSub, Number> viewsColumn; // Value injected by FXMLLoader

        @FXML // fx:id="dateColumn"
        private TableColumn<TrackedSub, String> dateColumn; // Value injected by FXMLLoader

        @FXML // fx:id="earningsColumn"
        private TableColumn<?, ?> earningsColumn; // Value injected by FXMLLoader

            @FXML // fx:id="minColumn"
            private TableColumn<TrackedSub, Number> minColumn; // Value injected by FXMLLoader

            @FXML // fx:id="maxColumn"
            private TableColumn<TrackedSub, Number> maxColumn; // Value injected by FXMLLoader

        @FXML // fx:id="genreColumn"
        private TableColumn<TrackedSub, String> genreColumn; // Value injected by FXMLLoader

        @FXML // fx:id="countryColumn"
        private TableColumn<TrackedSub, String> countryColumn; // Value injected by FXMLLoader

        @FXML
        private TableColumn<TrackedSub, String> idColumn;

    @FXML // fx:id="subListTitle"
    private Label subListTitle; // Value injected by FXMLLoader

    @FXML // fx:id="goBackButton"
    private Button goBackButton; // Value injected by FXMLLoader

    @FXML
    private Label overall_stats_label;

    @FXML
    private ListView<?> channel_stat_list;

    @FXML
    private Button add_channel_button;

    @FXML
    private Button remove_channel_button;


    /**
     * fills out the rows for the 2 tables on this page
     */
    public void addTableRows(){
        ObservableList<TrackedSub> tSubs = FXCollections.observableArrayList();
        ObservableList<String> uSubs = FXCollections.observableArrayList();

        String DB_URL = "jdbc:mysql://db4free.net:3306/ytsubanalyzer?autoReconnect=true&useSSL=false";
        String USER = "jsipprell";
        String PASSWORD = "CMa9d*UVHrJr!2s7";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            Statement stmt = conn.createStatement();
            String sqlStatement = "SELECT * FROM Tracked_Subs";
            ResultSet result = stmt.executeQuery(sqlStatement);

            while(result.next()){
                tSubs.add(new TrackedSub(result.getString("userID"),
                        result.getString("Name"),
                        result.getString("Country"),
                        result.getString("Genre"),
                        result.getInt("SubCount"),
                        result.getLong("ViewCount"),
                        result.getDouble("MinInc"),
                        result.getDouble("MaxInc"),
                        result.getDate("DateCreated").toString()
                        ));
            }

            sqlStatement = "SELECT * FROM UNTRACKED_SUBS";
            result = stmt.executeQuery(sqlStatement);

            while(result.next()){
                uSubs.add(result.getString("userID"));
            }

            conn.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        subCountColumn.setCellValueFactory(cellData -> cellData.getValue().getSubCount());
        viewsColumn.setCellValueFactory(cellData -> cellData.getValue().getViewCount());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDateC_string());
        minColumn.setCellValueFactory(cellData -> cellData.getValue().getMinInc());
        maxColumn.setCellValueFactory(cellData -> cellData.getValue().getMaxInc());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().getGenre());
        countryColumn.setCellValueFactory(cellData -> cellData.getValue().getCountry());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().getChannelID());


        channelTable.setItems(tSubs);
        naList.setItems(uSubs);
    }

    private Scene loginScene;
    private Controller cont;

    public void setLoginScene(Scene scene, Controller controller){
        loginScene = scene;

        cont = controller;
    }

    public void goBackClicked(MouseEvent mouseEvent) {
        cont.reset();
        Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
    }


    @FXML
    void initialize() {
        assert viewsColumn != null : "fx:id=\"viewsColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert naList != null : "fx:id=\"naList\" was not injected: check your FXML file 'subList.fxml'.";
        assert minColumn != null : "fx:id=\"minColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert notTrackedPane != null : "fx:id=\"notTrackedPane\" was not injected: check your FXML file 'subList.fxml'.";
        assert countryColumn != null : "fx:id=\"countryColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert earningsColumn != null : "fx:id=\"earningsColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert subListPane != null : "fx:id=\"subListPane\" was not injected: check your FXML file 'subList.fxml'.";
        assert channelTable != null : "fx:id=\"channelTable\" was not injected: check your FXML file 'subList.fxml'.";
        assert subCountColumn != null : "fx:id=\"subCountColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert nameColumn != null : "fx:id=\"nameColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert subListTitle != null : "fx:id=\"subListTitle\" was not injected: check your FXML file 'subList.fxml'.";
        assert goBackButton != null : "fx:id=\"goBackButton\" was not injected: check your FXML file 'subList.fxml'.";
        assert dateColumn != null : "fx:id=\"dateColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert genreColumn != null : "fx:id=\"genreColumn\" was not injected: check your FXML file 'subList.fxml'.";
        assert maxColumn != null : "fx:id=\"maxColumn\" was not injected: check your FXML file 'subList.fxml'.";


    }

    public void removeChannelDialogue(MouseEvent mouseEvent) {
        removeChannelFromDB(channelTable.getSelectionModel().getSelectedItem().getChannelID().getValue());

        channelTable.getItems().removeAll(
                channelTable.getSelectionModel().getSelectedItems()
        );
    }


    public void addNewChannel(MouseEvent mouseEvent) {
        String soBladeURL = JOptionPane.showInputDialog("Please enter the URL for the SocialBlade page:");

        //System.out.println(soBladeURL);

        Document channelData = null;
        try {
            channelData = Jsoup.connect(soBladeURL).userAgent("Chrome/66.0.3359.139").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element subName, channelIDEl;
        try {
            subName = channelData.select("#YouTubeUserTopInfoBlockTop > div:nth-child(1) > h1").first();
            channelIDEl = channelData.select("#YouTubeUserTopSocial > div:nth-child(2) > a").first();

            String channelID = channelIDEl.attr("href").split("/channel/")[1];

            TrackedSub r = new TrackedSub(channelID, subName.text(), channelData);

        } catch (NullPointerException i) {
            //System.out.println("\nSorry, this channel is not tracked by socialblade");
            i.printStackTrace();
        }
    }

    public void removeChannelFromDB(String channelID){
        String DB_URL = "jdbc:mysql://db4free.net:3306/ytsubanalyzer?autoReconnect=true&useSSL=false";
        String USER = "jsipprell";
        String PASSWORD = "CMa9d*UVHrJr!2s7";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);

            Statement stmt = conn.createStatement();
            String deleteRows = "DELETE FROM Tracked_Subs WHERE userID = '" + channelID + "'";
            int rows = stmt.executeUpdate(deleteRows);
        }
        catch(SQLException e){ e.printStackTrace(); }
    }
}
