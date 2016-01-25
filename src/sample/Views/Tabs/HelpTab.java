package sample.Views.Tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;

/**
 * Created by augustus on 1/25/16.
 * Creates the home tab.
 */
public class HelpTab {
    public static Tab createHelpTab(Tab tab){
        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();
        grid.setAlignment(Pos.CENTER);

        Label title = new Label("Help Page");
        title.setStyle("-fx-font-size: 24;");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(title);

        borderPane.setTop(hBox);

        VBox vBox = new VBox(10);
        Label label1 = new Label("This application is designed to provide a way to generate strong passwords\n"
                                +"for various other different applications.  The passwords are encrypted\n"
                                +"and stored locally on the machine in hidden files.\n\n"
                                +"You are able to export and import your passwords in cleartext or ciphertext.\n"
                                +"You are also able to upload your encrypted objects file to your Dropbox while\n"
                                +"online.  In order to restore your passwords you will have to put the object file\n"
                                +"back into your .UserFiles directory with the same username and password to ensure\n"
                                +"it is decrypted correctly.  Native backup imports/exports only require a correct password.");

        label1.setStyle("-fx-font-size: 16;");
        vBox.getChildren().addAll(label1);
        grid.add(vBox, 0,0);


        borderPane.setCenter(grid);

        tab.setContent(borderPane);

        return tab;
    }
}
