package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.html.FormSubmitEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        //create objects
        loginPageActions action = new loginPageActions();

        primaryStage.setTitle("Password Manager");

        //Create new grid
        GridPane grid = new GridPane();

        //Set alignment to center instead of top left corner
        grid.setAlignment(Pos.CENTER);

        //Manage spacing between the rows and columns
        grid.setHgap(10);
        grid.setVgap(10);

        //Manages space around the edges of the grid pane
        //Insets is in the order of top, right, bottom, left.  25 pixels padding on each side
        grid.setPadding(new Insets(25, 25, 25, 25));

        //Create scene
        Scene scene = new Scene(grid, 375, 375);

        //create title for the screen
        Text scenetitle = new Text("Welcome!");

        //sets the font for the scene title
        //scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setId("sceneTitle");

        /**
         * Adds the scene title to the grid.  Numbers for columns and rows starts at 0.
         * Scene Title is added at column 0, row 0.  The last two arguments of the function
         * set the column span to 2 and row span to 1.
         */
        grid.add(scenetitle, 0, 0, 2, 1);

        //Create label object with the text "User Name:" at column 0, row 1
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        //Creates a text field object that can be edited at column 1, row 1.
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        //Created in a similar fashion as above.
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        //Created in a similar fashion as above.
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        //Shows the grid lines. Useful for debugging purposes
        grid.setGridLinesVisible(false);

        //Creates a button called btn with the text "Log in"
        Button btn = new Button("Log in");
        Button btn2 = new Button("New User");

        //Creates a Hbox layout pane named hbBtn with spacing of 10 pixels
        HBox hbBtn = new HBox(10);

        /**
         * The HBox pane sets an alignment for the button that is different
         * from the alignment applied to the other controls in the grid pane.
         * The alignment property has a value of Pos.BOTTOM_RIGHT, which
         * positions a node at the bottom of the space vertically and at the
         * right edge of the space horizontally.
         */
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);

        //Adds the button as a child of the Hbox pane
        hbBtn.getChildren().add(btn2);
        hbBtn.getChildren().add(btn);


        //Hbox pane added to the grid in column 1, row 4
        grid.add(hbBtn, 1, 4);

        //Add a text control for displaying the message
        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6, 3, 1);

        //Add another message for attempts left
        final Text attemptsLeft = new Text();
        grid.add(attemptsLeft, 0, 7, 3, 1);

        //Make the columns a default size meant to hold the whole error msg without resizing
        grid.getColumnConstraints().add(new ColumnConstraints(100));
        grid.getColumnConstraints().add(new ColumnConstraints(250));


        //create an event handler
        btn.setOnAction(e -> {
            if((userTextField.getText().length() != 0) && (pwBox.getText().length() != 0)) {
                int credentialsAnswer = 0;

                credentialsAnswer = action.checkUserLogin(userTextField.getText(), pwBox.getText(), attemptsLeft);

                if (credentialsAnswer == 1) {
                    action.loginUser(primaryStage, scene.getWidth(), scene.getHeight());
                } else {
                    action.invalidUser(actiontarget);
                }
            }else{
                actiontarget.setId("actionTarget");
                actiontarget.setText("   Username and password fields are required.");
            }
        });

        btn2.setOnAction(e -> {
            action.newUser(primaryStage, scene.getWidth(), scene.getHeight());
            //actiontarget.setId("actionTarget");
            //actiontarget.setText("New User button pressed");
        });

        //Add listeners to submit/username boxes with the enter key
        userTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                //Fire the button as if you were clicking it
                btn.fire();
            }
        });

        pwBox.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                //Fire the button as if you were clicking it
                btn.fire();
            }
        });

        //Grid pane set as root node. Width, Height.
        //scene = new Scene(grid, 375, 375);

        //Add the scene to a list
        SceneList.AddScene(scene);

        primaryStage.setScene(scene);

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
