package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by augustus on 1/13/16.
 */
public class createScenes {

    //Create the signup scene. Width and height of previous screen is passed in so no resizing.
    public void createSignUpScene(Stage primaryStage, double width, double height){
        Stage temp = primaryStage;
        GridPane grid = new GridPane();

        Button btn = new Button("Back");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        //Create a back button
        btn.setOnAction(e-> {
            ArrayList scenes = new ArrayList<>();
            scenes = SceneList.GetScene();
            primaryStage.setScene((Scene) scenes.get(0));
        });

        Scene newUserScene = new Scene(grid, width, height);
        primaryStage.setScene(newUserScene);
    }

    //Create the home scene.  Width and height of previous screen is passed in so no resizing.
    public void createHomeScene(Stage primaryStage, double width, double height){
        GridPane grid = new GridPane();
        Scene newUserScene = new Scene(grid, width, height);
        primaryStage.setScene(newUserScene);
    }
}
