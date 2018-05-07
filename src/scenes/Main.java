package scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Loading the scene for entering channel URL
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("inputPage.fxml"));
        Parent loginPage = loginLoader.load();
        Scene loginScene = new Scene(loginPage);

        //Loading scene for displaying list of subscriptions
        FXMLLoader subListLoader = new FXMLLoader(getClass().getResource("subList.fxml"));
        Parent subListPage = subListLoader.load();
        Scene subListScene = new Scene(subListPage);

        //designating controller for login scene
        Controller loginC = (Controller) loginLoader.getController();
        //providing the login controller with the other scenes
        loginC.setSubListScene(subListScene);

        //designating controller for sub list scene
        subListController subListC = (subListController) subListLoader.getController();
        //providing the subList controller with the other scenes
        subListC.setLoginScene(loginScene);

        //Showing the first screen for program
        primaryStage.setTitle("YouTube Subscription Statistics");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }//end start method


    public static void main(String[] args) {
        launch(args);
    }
}
