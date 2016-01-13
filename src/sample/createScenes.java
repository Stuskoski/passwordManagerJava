package sample;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by augustus on 1/13/16.
 */
public class createScenes {

    public void createSignUpScene(Stage primaryStage){
        GridPane grid = new GridPane();
        Scene newUserScene = new Scene(grid, 900, 375);
        primaryStage.setScene(newUserScene);

    }

    public void createHomeScene(Stage primaryStage){
        GridPane grid = new GridPane();
        Scene newUserScene = new Scene(grid, 900, 375);
        primaryStage.setScene(newUserScene);

    }
}
