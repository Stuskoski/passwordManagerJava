package sample.Views.Tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Views.LoginScreen;

/**
 * Created by augustus on 1/19/16.
 * Password generator tab.
 */
public class PasswordGeneratorTab {
    public static Tab createPasswordGeneratorTab(Tab genTab){

        GridPane genPassGrid = new GridPane();
        BorderPane genPassPane = new BorderPane();
        VBox options = new VBox(20);
        VBox passwords = new VBox(5);

        genPassGrid.setHgap(10);
        genPassGrid.setVgap(10);
        genPassGrid.setAlignment(Pos.TOP_CENTER);
        genPassGrid.setPadding(new Insets(30,40,0,0));

        HBox title = new HBox();
        title.setAlignment(Pos.CENTER);
        Label msg = new Label("Password Generator");
        msg.setStyle("-fx-font-size: 24;");
        title.getChildren().add(msg);
        genPassPane.setTop(title);

        HBox passOptionsBox = new HBox();
        passOptionsBox.setAlignment(Pos.CENTER);
        Label passOptionsTitle = new Label("Password Options");
        passOptionsTitle.setStyle("-fx-font-size: 20;");
        passOptionsBox.getChildren().add(passOptionsTitle);

        ChoiceBox passLength = new ChoiceBox();
        passLength.getItems().addAll(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        Label passLengthLabel = new Label("Password Length:     ");

        HBox passLengthBox = new HBox(5);
        passLengthBox.getChildren().addAll(passLengthLabel, passLength);

        Label addNumbers = new Label("Numbers:                 ");
        CheckBox addNumbersCheck = new CheckBox();
        HBox numbersBox = new HBox(5);
        numbersBox.getChildren().addAll(addNumbers, addNumbersCheck);

        Label addSymbols = new Label("Symbols:                  ");
        CheckBox addSymbolsCheck = new CheckBox();
        HBox symbolsBox = new HBox(5);
        symbolsBox.getChildren().addAll(addSymbols, addSymbolsCheck);

        Label lowerCase = new Label("Lowercase Only:      ");
        CheckBox lowerCaseCheck = new CheckBox();
        HBox lowerCaseBox = new HBox(5);
        lowerCaseBox.getChildren().addAll(lowerCase, lowerCaseCheck);

        Label upperCase = new Label("Uppercase Only:      ");
        CheckBox upperCaseCheck = new CheckBox();
        HBox upperCaseBox = new HBox(5);
        upperCaseBox.getChildren().addAll(upperCase, upperCaseCheck);

        Button genPassBtn = new Button("Generate Passwords");
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(genPassBtn);


        options.getChildren().addAll(passOptionsBox, passLengthBox, numbersBox, symbolsBox, lowerCaseBox, upperCaseBox, buttonBox);

        genPassGrid.add(options, 0, 0);


        HBox genPasswordsTitleBox = new HBox();
        genPasswordsTitleBox.setAlignment(Pos.CENTER);
        Label genPasswordsTitle = new Label("Generated Passwords");
        genPasswordsTitle.setStyle("-fx-font-size: 18;");
        genPasswordsTitleBox.getChildren().add(genPasswordsTitle);

        passwords.getChildren().add(genPasswordsTitleBox);

        genPassGrid.add(passwords, 1, 0);

        options.setStyle("-fx-background-color: DAE6F3;");
        options.setPrefWidth(300);
        passwords.setStyle("-fx-background-color: DAE6F3;");
        passwords.setPrefWidth(300);

        genPassPane.setCenter(genPassGrid);

        genTab.setContent(genPassPane);

        return genTab;
    }
}
