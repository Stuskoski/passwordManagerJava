package sample.Views.Tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.Models.EntryObjectList;
import sample.Models.EntryObjects;
import sample.Views.HomeScreen;


/**
 * Created by augustus on 1/18/16.
 * This function creates the home tab
 */
public class CreateEntryTab {
    public static Tab createEntryTab(Tab createEntryTab) {

        //Create grid for center
        GridPane homeGrid = new GridPane();

        homeGrid.setAlignment(Pos.CENTER);

        homeGrid.setHgap(10);
        homeGrid.setVgap(10);
        //top right bottom left
        homeGrid.setPadding(new Insets(100,50,50,50));

        Label title = new Label("New Entry");
        homeGrid.add(title, 1, 0);
        //title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 24;");

        //Create the page contents
        Label name = new Label("Name: ");
        homeGrid.add(name, 0, 1);

        //Created in a similar fashion as above.
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        homeGrid.add(nameField, 1, 1);

        Label password = new Label("Password: ");
        homeGrid.add(password, 0, 3);

        //Create two fields, one visible and the other invisible
        TextField pwBox = new TextField();
        PasswordField pwBox2 = new PasswordField();
        pwBox.setPromptText("Password");
        pwBox2.setPromptText("Password");
        pwBox.setManaged(false);
        pwBox.setVisible(false);

        //Add the check box
        CheckBox showPassBox = new CheckBox("Show");
        showPassBox.setId("showPassText");
        homeGrid.add(showPassBox, 2,3);

        //Bind the text field to the check box when selected
        pwBox.managedProperty().bind(showPassBox.selectedProperty());
        pwBox.visibleProperty().bind(showPassBox.selectedProperty());

        //Bind the pwBox to the check box when not selected
        pwBox2.managedProperty().bind(showPassBox.selectedProperty().not());
        pwBox2.visibleProperty().bind(showPassBox.selectedProperty().not());

        // Bind the textField and passwordField text values bidirectionally.
        pwBox.textProperty().bindBidirectional(pwBox2.textProperty());

        //Add the two boxes on top of another.  Only one shows at a time.
        homeGrid.add(pwBox, 1, 3);
        homeGrid.add(pwBox2, 1, 3);


        Label description = new Label("Description: ");
        homeGrid.add(description, 0, 5);

        //Created in a similar fashion as above.
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        homeGrid.add(descriptionField, 1, 5);

        Button clearBtn = new Button("Clear Entry");
        Button addBtn = new Button("Add Entry");
        clearBtn.setId("dark-btn");
        addBtn.setId("dark-btn");
        HBox holdBtns = new HBox(10);
        holdBtns.getChildren().addAll(addBtn, clearBtn);

        homeGrid.add(holdBtns, 1,7);

        Label confirmEntry = new Label("");
        homeGrid.add(confirmEntry, 1, 9);

        clearBtn.setOnAction(event -> {
            nameField.setText("");
            pwBox.setText("");
            descriptionField.setText("");
            confirmEntry.setText("");
        });

        addBtn.setOnAction(event -> {
            if((nameField.getText().length() > 0) && (pwBox.getText().length() > 0)){
                EntryObjects newEntry = new EntryObjects();
                newEntry.setName(nameField.getText());
                newEntry.setPassword(pwBox.getText());
                newEntry.setDescription(descriptionField.getText());
                EntryObjectList.getObjectList().add(newEntry);
                confirmEntry.setText("Entry Successfully Added.");
                HomeTab.refreshTable(HomeScreen.getHomeTab()); //refresh the tab in the background
                confirmEntry.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
                nameField.setText("");
                pwBox.setText("");
                descriptionField.setText("");
            }else{
                confirmEntry.setText("Name and password are required.");
                confirmEntry.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
            }
        });

        nameField.setOnKeyPressed(event -> {
            confirmEntry.setText("");
        });
        nameField.setOnKeyReleased(event1 -> {
            if(checkIfNameExists(nameField.getText())){
                confirmEntry.setText("That name is already in use.");
                confirmEntry.setStyle("-fx-text-fill: red; -fx-font-size: 16;");
            }
        });
        pwBox.setOnKeyPressed(event -> {
            confirmEntry.setText("");
        });
        pwBox2.setOnKeyPressed(event -> {
            confirmEntry.setText("");
        });
        nameField.setOnKeyPressed(event -> {
            confirmEntry.setText("");
        });
        descriptionField.setOnKeyPressed(event -> {
            confirmEntry.setText("");
        });

        homeGrid.setGridLinesVisible(false);

        //Add the content to display
        createEntryTab.setContent(homeGrid);

        return createEntryTab;
    }

    private static boolean checkIfNameExists(String text){
        for (EntryObjects obj : EntryObjectList.getObjectList()) {
            if(text.toLowerCase().equals(obj.getName().toLowerCase())){
                return true;
            }
        }

        return false;
    }
}
