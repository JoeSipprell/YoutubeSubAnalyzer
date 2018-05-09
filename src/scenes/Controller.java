/**
 * Sample Skeleton for 'FinalProjectGUI.fxml' Controller Class
 */


package scenes;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

//import java.awt.datatransfer.*;
//import java.awt.Toolkit;

/**
 * @author Ken Sipprell
 * Controller class for the login page
 * Maybe should be renamed
 */
public class Controller {

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

    public String userID;

    private Scene subListScene;

    /**
     * @param scene JavaFX scene for the list of channels scraped
     */
    public void setSubListScene(Scene scene) {
        subListScene = scene;
    }// end setSubListScene

    /**
     * @param mouseEvent when the go button is clicked
     */
    public void buttonClicked(MouseEvent mouseEvent) {
        //String URL = channelInputBox.getText();
        String URL = "https://www.youtube.com/channel/UCb81rLqF7RVbnqmOEO0IGMg/channels?view=56&shelf_id=0";

        //URL = URL.substring(0 , URL.lastIndexOf('/') + 1 ) + "channels?view=56&shelf_id=0";

        if (URL.split("(/channel[/(s?)])").length == 3) {
            checkChannelInput(URL);
        } else {
            warningLabel.setText("Sorry, your input was not valid, please try again.");
        }

    }// end buttonClicked

    /**
     *
     * @param URL the url of the user's youtube channel, which has been checked already
     */
    private void checkChannelInput(String URL){
        try {
            userID = URL.split("(/channel[/(s?)])")[1];

            Document userChannel = Jsoup.connect(URL).userAgent("Chrome/66.0.3359.139").get();

            Elements subURLs = userChannel.select("a[href]:not([title])");

            System.out.println("boi");

            System.out.println(subURLs.size());


            for (Element subURL : subURLs) {
                addChannel(subURL.attr("href"));
            }

            System.out.println("enboi");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //warningLabel.setText(URL);

            /*StringSelection stringSelection = new StringSelection(URL);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);*/



        /*Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(subListScene);*/
    }// end checkChannelInput

    public void addChannel(String channelID){
        /** checking href text
         * only lets through if link is directed to a channel
         * blocks if the URL found contains the user's channel ID
         */
        if (channelID.contains("/channel/") && !channelID.contains(userID)) {

            System.out.println(channelID);

            Document channelData = null;
            try {
                channelData = Jsoup.connect("https://socialblade.com/youtube" + channelID).userAgent("Chrome/66.0.3359.139").get();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Element subName;
            try {
                subName = channelData.select("#YouTubeUserTopInfoBlockTop > div:nth-child(1) > h1").first();

                System.out.println(subName.text());
            } catch (NullPointerException i) {
                System.out.println("\nSorry, this channel is not tracked by socialblade\n");
            }// end try catch block

        }// end checking href text
    }


    /*@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert channelInputBox != null : "fx:id=\"channelInputBox\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert goButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert warningLabel != null : "fx:id=\"warningLabel\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputGridPane != null : "fx:id=\"inputGridPane\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputPane != null : "fx:id=\"inputPane\" was not injected: check your FXML file 'inputPage.fxml'.";

    }*/
}
