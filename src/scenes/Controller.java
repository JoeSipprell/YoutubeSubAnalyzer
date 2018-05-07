/**
 * Sample Skeleton for 'FinalProjectGUI.fxml' Controller Class
 */

package scenes;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainAPanel"
    private AnchorPane mainAPanel; // Value injected by FXMLLoader

    @FXML // fx:id="loginPanel"
    private Pane loginPanel; // Value injected by FXMLLoader

    @FXML // fx:id="minC"
    private TableColumn<?, ?> minC; // Value injected by FXMLLoader

    @FXML // fx:id="nameC"
    private TableColumn<?, ?> nameC; // Value injected by FXMLLoader

    @FXML // fx:id="viewsC"
    private TableColumn<?, ?> viewsC; // Value injected by FXMLLoader

    @FXML // fx:id="genreC"
    private TableColumn<?, ?> genreC; // Value injected by FXMLLoader

    @FXML // fx:id="goButton"
    private Button goButton; // Value injected by FXMLLoader

    @FXML // fx:id="subListPane"
    private Pane subListPane; // Value injected by FXMLLoader

    @FXML // fx:id="maxC"
    private TableColumn<?, ?> maxC; // Value injected by FXMLLoader

    @FXML // fx:id="channelTable"
    private TableView<?> channelTable; // Value injected by FXMLLoader

    @FXML // fx:id="tablePanel"
    private AnchorPane tablePanel; // Value injected by FXMLLoader

    @FXML // fx:id="earningsC"
    private TableColumn<?, ?> earningsC; // Value injected by FXMLLoader

    @FXML // fx:id="subC"
    private TableColumn<?, ?> subC; // Value injected by FXMLLoader

    @FXML // fx:id="warningLabel"
    private Label warningLabel; // Value injected by FXMLLoader

    @FXML // fx:id="urlEntry"
    private TextField urlEntry; // Value injected by FXMLLoader

    @FXML // fx:id="loginTitle"
    private Label loginTitle; // Value injected by FXMLLoader

    @FXML // fx:id="dateC"
    private TableColumn<?, ?> dateC; // Value injected by FXMLLoader

    @FXML // fx:id="tableTitle"
    private Label tableTitle; // Value injected by FXMLLoader

    @FXML // fx:id="countryC"
    private TableColumn<?, ?> countryC; // Value injected by FXMLLoader

    public void mouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getSource().equals(goButton)){
            mainAPanel.setPrefSize(600,600);
            loginPanel.setVisible(false);
            subListPane.setVisible(true);
        }
    }


    /*@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert channelInputBox != null : "fx:id=\"channelInputBox\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert warningLabel != null : "fx:id=\"warningLabel\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputGridPane != null : "fx:id=\"inputGridPane\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'inputPage.fxml'.";
        assert inputPane != null : "fx:id=\"inputPane\" was not injected: check your FXML file 'inputPage.fxml'.";

    }*/
}
