package scenes;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage window;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        root = FXMLLoader.load(getClass().getResource("FinalProjectGUI.fxml"));
        window.setTitle("Hello World");
        window.setScene(new Scene(root));
        window.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
