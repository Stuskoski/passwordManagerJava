package sample.Views.Tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.Models.EntryObjectList;
import sample.Models.EntryObjects;
import sample.Views.LoginScreen;
import sun.security.util.Password;

import static javafx.geometry.Pos.CENTER;

/**
 * Created by augustus on 1/18/16.
 * This function creates the home tab
 */
public class CreateEntryTab {
    public static Tab createEntryTab(Tab createEntryTab) {

        //Create the default pane that will hold everything
        BorderPane createEntryPane = new BorderPane();

        createEntryPane.setPadding(new Insets(50, 50, 50, 50));

        //Manage spacing between the rows and columns
        //grid.setHgap(10);
        //grid.setVgap(10);

        //Manages space around the edges of the grid pane
        //Insets is in the order of top, right, bottom, left.  25 pixels padding on each side
        //grid.setPadding(new Insets(25, 25, 25, 25));


        //Create grid for center
        GridPane homeGrid = new GridPane();

        homeGrid.setHgap(10);
        homeGrid.setVgap(10);
        homeGrid.setPadding(new Insets(50,50,50,50));

        Label title = new Label("New Entry");
        homeGrid.add(title, 1, 0);
        //title.setAlignment(Pos.CENTER);
        title.setFont(new Font(60));

        //Create the page contents
        Label name = new Label("Name: ");
        homeGrid.add(name, 0, 1);

        //Created in a similar fashion as above.
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        homeGrid.add(nameField, 1, 1);

        Label password = new Label("Password: ");
        homeGrid.add(password, 0, 3);

        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Password");
        homeGrid.add(pwBox, 1, 3);

        Label description = new Label("Description: ");
        homeGrid.add(description, 0, 5);

        //Created in a similar fashion as above.
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Description");
        homeGrid.add(descriptionField, 1, 5);

        Button clearBtn = new Button("Clear Entry");
        Button addBtn = new Button("Add Entry");
        HBox holdBtns = new HBox(10);
        holdBtns.getChildren().addAll(addBtn, clearBtn);

        homeGrid.add(holdBtns, 1,7);

        clearBtn.setOnAction(event -> {
            nameField.setText("");
            pwBox.setText("");
            descriptionField.setText("");
        });

        addBtn.setOnAction(event -> {
            EntryObjects newEntry = new EntryObjects();
            newEntry.setName(nameField.getText());
            newEntry.setPassword(pwBox.getText());
            newEntry.setDescription(descriptionField.getText());
            EntryObjectList.getObjectList().add(newEntry);
        });

        homeGrid.setGridLinesVisible(true);

        //add grid to center
        //createEntryPane.getChildren().add(homeGrid);
        createEntryPane.setCenter(homeGrid);

        //Add the content to display
        createEntryTab.setContent(createEntryPane);

        return createEntryTab;
    }
}
