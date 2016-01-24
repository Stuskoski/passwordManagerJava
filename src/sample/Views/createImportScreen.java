package sample.Views;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by augustus on 1/24/16.
 */
public class createImportScreen {
    public static void createImport(){
        Stage importStage = new Stage();
        BorderPane importRoot = new BorderPane();
        Scene importScene = new Scene(importRoot,500, 500);

        importStage.setTitle("Import Back");

        //Add css sheet
        importScene.getStylesheets().add("sample/Login.css");

        importStage.setScene(importScene);

        //Fill stage with content
        importStage.show();
    }
}
