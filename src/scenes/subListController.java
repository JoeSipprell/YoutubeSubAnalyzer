/**
 * Sample Skeleton for 'subList.fxml' Controller Class
 */

package scenes;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class subListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="viewsColumn"
    private TableColumn<?, ?> viewsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="naList"
    private ListView<?> naList; // Value injected by FXMLLoader

    @FXML // fx:id="minColumn"
    private TableColumn<?, ?> minColumn; // Value injected by FXMLLoader

    @FXML // fx:id="notTrackedPane"
    private Pane notTrackedPane; // Value injected by FXMLLoader

    @FXML // fx:id="countryColumn"
    private TableColumn<?, ?> countryColumn; // Value injected by FXMLLoader

    @FXML // fx:id="earningsColumn"
    private TableColumn<?, ?> earningsColumn; // Value injected by FXMLLoader

    @FXML // fx:id="subListPane"
    private Pane subListPane; // Value injected by FXMLLoader

    @FXML // fx:id="channelTable"
    private TableView<?> channelTable; // Value injected by FXMLLoader

    @FXML // fx:id="subCountColumn"
    private TableColumn<?, ?> subCountColumn; // Value injected by FXMLLoader

    @FXML // fx:id="nameColumn"
    private TableColumn<?, ?> nameColumn; // Value injected by FXMLLoader

    @FXML // fx:id="subListTitle"
    private Label subListTitle; // Value injected by FXMLLoader

    @FXML // fx:id="goBackButton"
    private Button goBackButton; // Value injected by FXMLLoader

    @FXML // fx:id="dateColumn"
    private TableColumn<?, ?> dateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="genreColumn"
    private TableColumn<?, ?> genreColumn; // Value injected by FXMLLoader

    @FXML // fx:id="maxColumn"
    private TableColumn<?, ?> maxColumn; // Value injected by FXMLLoader


    private Scene loginScene;

    public void setLoginScene(Scene scene){
        loginScene = scene;
    }

    public void goBackClicked(MouseEvent mouseEvent) {
        Stage primaryStage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
    }
}
