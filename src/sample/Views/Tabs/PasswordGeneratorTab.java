package sample.Views.Tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import sample.Views.LoginScreen;

import java.util.ArrayList;
import java.util.List;

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

        ChoiceBox numOfPass = new ChoiceBox();
        numOfPass.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        numOfPass.getSelectionModel().selectFirst();
        Label numOfPassLabel = new Label("Number of Passwords:     ");
        HBox numOfPassBox = new HBox(5);
        numOfPassBox.getChildren().addAll(numOfPassLabel, numOfPass);
        numOfPassBox.setAlignment(Pos.CENTER);

        ChoiceBox passLength = new ChoiceBox();
        passLength.getItems().addAll(5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        passLength.getSelectionModel().selectFirst();
        Label passLengthLabel = new Label("Password Length:            ");
        HBox passLengthBox = new HBox(5);
        passLengthBox.getChildren().addAll(passLengthLabel, passLength);
        passLengthBox.setAlignment(Pos.CENTER);

        Label addNumbers = new Label("Numbers:                  ");
        CheckBox addNumbersCheck = new CheckBox();
        HBox numbersBox = new HBox(5);
        numbersBox.setAlignment(Pos.CENTER);
        numbersBox.getChildren().addAll(addNumbers, addNumbersCheck);

        Label addSymbols = new Label("Symbols:                  ");
        CheckBox addSymbolsCheck = new CheckBox();
        HBox symbolsBox = new HBox(5);
        symbolsBox.setAlignment(Pos.CENTER);
        symbolsBox.getChildren().addAll(addSymbols, addSymbolsCheck);

        Label lowerCase = new Label("Lowercase Only:       ");
        CheckBox lowerCaseCheck = new CheckBox();
        HBox lowerCaseBox = new HBox(5);
        lowerCaseBox.setAlignment(Pos.CENTER);
        lowerCaseBox.getChildren().addAll(lowerCase, lowerCaseCheck);

        Label upperCase = new Label("Uppercase Only:      ");
        CheckBox upperCaseCheck = new CheckBox();
        HBox upperCaseBox = new HBox(5);
        upperCaseBox.setAlignment(Pos.CENTER);
        upperCaseBox.getChildren().addAll(upperCase, upperCaseCheck);

        Button genPassBtn = new Button("Generate Passwords");
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(genPassBtn);
        VBox.setVgrow(buttonBox, Priority.ALWAYS);


        genPassBtn.setOnMouseClicked(event -> {
            passwords.getChildren().clear();

            //Re add the header
            HBox genPasswordsTitleBoxRefresh = new HBox();
            genPasswordsTitleBoxRefresh.setAlignment(Pos.CENTER);
            Label genPasswordsTitleRefresh = new Label("Generated Passwords");
            genPasswordsTitleRefresh.setStyle("-fx-font-size: 18;");
            genPasswordsTitleBoxRefresh.getChildren().add(genPasswordsTitleRefresh);
            passwords.getChildren().add(genPasswordsTitleBoxRefresh);

            List<HBox> passwordList= new ArrayList<>();
            passwordList = genPasswordList((int)numOfPass.getValue(), (int)passLength.getValue(), addNumbersCheck.isSelected(), addSymbolsCheck.isSelected(), lowerCaseCheck.isSelected(), upperCaseCheck.isSelected());
            for (HBox pass: passwordList) {
                passwords.getChildren().addAll(pass);
            }
        });

        options.getChildren().addAll(passOptionsBox, numOfPassBox, passLengthBox, numbersBox, symbolsBox, lowerCaseBox, upperCaseBox, buttonBox);

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
        options.setPrefHeight(450);
        passwords.setStyle("-fx-background-color: DAE6F3;");
        passwords.setPrefWidth(300);
        passwords.setPrefHeight(450);

        genPassPane.setCenter(genPassGrid);

        genTab.setContent(genPassPane);

        return genTab;
    }

    public static List<HBox> genPasswordList(int numOfPass, int passLength, boolean numbers, boolean symbols, boolean lower, boolean upper){
        List<HBox> passwordList = new ArrayList<>();

        for (int i = 0; i < numOfPass; i++){
            HBox passBox = new HBox();
            passBox.setAlignment(Pos.CENTER);
            Label pass = new Label(genPasswords(passLength, numbers, symbols, lower, upper));
            Button copyPass = new Button("Copy to Clipboard");
            passBox.getChildren().addAll(pass, copyPass);

            copyPass.setOnMouseClicked(event -> {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(pass.getText());
                clipboard.setContent(content);
            });

            passwordList.add(passBox);
        }
        return passwordList;

    }

    //Return a password as a string after generating it with the options
    private static String genPasswords(int passLength, boolean numbers, boolean symbols, boolean lower, boolean upper) {
        String password = "";



        return(password);
    }
}
