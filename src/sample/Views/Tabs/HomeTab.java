package sample.Views.Tabs;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxFiles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import sample.Controllers.DropboxConnect;
import sample.Models.EntryObjectList;
import sample.Models.EntryObjects;
import sample.Views.LoginScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Optional;
import sample.Controllers.*;

/**
 * Created by augustus on 1/18/16.
 * This function creates the home tab
 */
public class HomeTab {
    //Create the home tab for the home screen.
    public static Tab createHomeTab(Tab homeTab){
        //Header to be used later, at the bottom of the code
        Label paneHeader = new Label("Select An Entry");
        paneHeader.setStyle("-fx-font-size: 16;");

        //Flow pane to hold the items inside the grid
        VBox showEntryPane = new VBox(20);
        showEntryPane.setPrefWidth(450);

        //Create the default pane that will hold everything
        BorderPane home = new BorderPane();

        //Create the welcome msg for alignment at the top center.  Set it on the pane as well.
        HBox welcomeMsg = new HBox();
        welcomeMsg.setAlignment(Pos.CENTER);
        Label msg = new Label("Welcome " + LoginScreen.getLoggedInUser() + "!");
        msg.setStyle("-fx-font-size: 24;");
        welcomeMsg.getChildren().add(msg);
        home.setTop(welcomeMsg);

        GridPane homeGrid = new GridPane();
        homeGrid.setHgap(30);
        homeGrid.setVgap(10);

        homeGrid.setPadding(new Insets(30,10,0,0));

        homeGrid.setAlignment(Pos.CENTER);

        TreeItem<Label> root = new TreeItem<>(new Label("Entries"));
        root.setExpanded(false);


        //This creates the tree and adds the listeners to it
        for (EntryObjects obj : EntryObjectList.getObjectList()) {
            TreeItem<Label> nodeT = new TreeItem<>(new Label(obj.getName()));
            Label show = new Label("Show");
            Label edit = new Label("Edit");
            Label delete = new Label("Delete");

            show.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    showDetails(paneHeader, obj, showEntryPane);
                }
            });
            edit.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    editDetails(paneHeader, obj, showEntryPane, homeTab);
                }
            });
            delete.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setContentText("Delete Entry?");
                    alert.setHeaderText(null);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        deleteEntry(obj, homeTab);
                    }
                }
            });
            nodeT.getChildren().addAll(
                    new TreeItem<>(show),
                    new TreeItem<>(edit),
                    new TreeItem<>(delete)
            );
            root.getChildren().add(
              nodeT
            );
        }

        TreeView<Label> treeView = new TreeView<>(root);
        treeView.setId("treeStyle");

        homeGrid.add(treeView, 0, 0);

        Button refresh = new Button("Refresh");
        refresh.setId("dark-btn");
        refresh.setOnAction(event -> refreshTable(homeTab));
        homeGrid.add(refresh, 0, 1);


        showEntryPane.setId("paneStyle");

        showEntryPane.setAlignment(Pos.TOP_CENTER);
        showEntryPane.getChildren().add(paneHeader);

        homeGrid.add(showEntryPane, 1, 0);

        home.setCenter(homeGrid);

       // homeGrid.setGridLinesVisible(true);

        homeTab.setContent(home);

        return homeTab;
    }

    private static void deleteEntry(EntryObjects obj, Tab homeTab) {
        EntryObjectList.getObjectList().remove(obj);
        refreshTable(homeTab);
    }

    private static void editDetails(Label paneHeader, EntryObjects obj, VBox showEntryPane, Tab homeTab) {
        showEntryPane.getChildren().clear();

        HBox nameHBox = new HBox(5);
        HBox passwordHBox = new HBox(5);
        HBox descriptionHBox = new HBox(5);

        paneHeader.setText("Edit Details For: " + obj.getName());

        Label name = new Label("Name:           ");
        Label password = new Label("Password:     ");
        Label description = new Label("Description:  ");
        name.setStyle("-fx-font-size: 16;");
        password.setStyle("-fx-font-size: 16;");
        description.setStyle("-fx-font-size: 16;");

        TextField nameField = new TextField(obj.getName());
        TextField passwordField = new TextField(obj.getPassword());
        PasswordField passwordField2 = new PasswordField();
        TextArea descriptionField = new TextArea(obj.getDescription());
        descriptionField.setPrefWidth(200);

        passwordField2.setManaged(false);
        passwordField2.setVisible(false);

        CheckBox hidePass = new CheckBox("Hide");
        hidePass.setStyle("-fx-text-fill: white");

        //Bind the text field to the check box when selected
        passwordField2.managedProperty().bind(hidePass.selectedProperty());
        passwordField2.visibleProperty().bind(hidePass.selectedProperty());

        //Bind the pwBox to the check box when not selected
        passwordField.managedProperty().bind(hidePass.selectedProperty().not());
        passwordField.visibleProperty().bind(hidePass.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        passwordField2.textProperty().bindBidirectional(passwordField.textProperty());

        nameField.setEditable(true);
        passwordField.setEditable(true);
        passwordField2.setEditable(true);
        descriptionField.setEditable(true);

        nameHBox.getChildren().addAll(name, nameField);
        passwordHBox.getChildren().addAll(password, passwordField, passwordField2, hidePass);
        descriptionHBox.getChildren().addAll(description, descriptionField);

        Button submit = new Button("Submit Changes");
        submit.setId("dark-btn");

        submit.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Update Entry?");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                obj.setName(nameField.getText());
                obj.setPassword(passwordField.getText());
                obj.setDescription(descriptionField.getText());
                refreshTable(homeTab);
            }
        });

        showEntryPane.getChildren().addAll(paneHeader, nameHBox, passwordHBox, descriptionHBox, submit);
    }

    private static void showDetails(Label paneHeader, EntryObjects obj, VBox showEntryPane) {

        showEntryPane.getChildren().clear();

        HBox nameHBox = new HBox(5);
        HBox passwordHBox = new HBox(5);
        HBox descriptionHBox = new HBox(5);

        paneHeader.setText("Showing Details For: " + obj.getName());

        Label name = new Label("Name:           ");
        Label password = new Label("Password:     ");
        Label description = new Label("Description:  ");
        name.setStyle("-fx-font-size: 16;");
        password.setStyle("-fx-font-size: 16;");
        description.setStyle("-fx-font-size: 16;");

        TextField nameField = new TextField(obj.getName());
        TextField passwordField = new TextField(obj.getPassword());
        PasswordField passwordField2 = new PasswordField();
        TextArea descriptionField = new TextArea(obj.getDescription());
        descriptionField.setPrefWidth(240);

        Button copyPassInShow = new Button("Copy");
        copyPassInShow.setPrefHeight(20);
        copyPassInShow.setPrefWidth(20);
        copyPassInShow.setId("copyToClipboardBtn");
        copyPassInShow.setStyle("-fx-background-image: url('../../clipboard.jpg');");

        passwordField2.setManaged(false);
        passwordField2.setVisible(false);

        CheckBox hidePass = new CheckBox("Hide");
        hidePass.setStyle("-fx-text-fill: white");

        //Bind the text field to the check box when selected
        passwordField2.managedProperty().bind(hidePass.selectedProperty());
        passwordField2.visibleProperty().bind(hidePass.selectedProperty());

        //Bind the pwBox to the check box when not selected
        passwordField.managedProperty().bind(hidePass.selectedProperty().not());
        passwordField.visibleProperty().bind(hidePass.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        passwordField2.textProperty().bindBidirectional(passwordField.textProperty());

        nameField.setEditable(false);
        passwordField.setEditable(false);
        passwordField2.setEditable(false);
        descriptionField.setEditable(false);

        nameHBox.getChildren().addAll(name, nameField);
        passwordHBox.getChildren().addAll(password, passwordField, passwordField2, hidePass, copyPassInShow);
        descriptionHBox.getChildren().addAll(description, descriptionField);

        copyPassInShow.setOnMouseClicked(e -> {
            final Clipboard clip = Clipboard.getSystemClipboard();
            final ClipboardContent addContent = new ClipboardContent();
            addContent.putString(passwordField.getText());
            clip.setContent(addContent);
        });


        showEntryPane.getChildren().addAll(paneHeader, nameHBox, passwordHBox, descriptionHBox);
    }

    public static void refreshTable(Tab homeTab) {
        createHomeTab(homeTab);
    }
}
