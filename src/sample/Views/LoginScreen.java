package sample.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;
import sample.SceneList;
import sample.loginPageActions;

/**
 * Created by augustus on 1/14/16.
 */

public class LoginScreen {
    /**
     * A static variable belongs to that class.
     * A non static variable belongs to each object
     * that is created from the class
     **/
    private static Scene loginScene;

    public static void createLoginScene(double width, double height){
        //create objects
        loginPageActions action = new loginPageActions();

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
        Scene scene = new Scene(grid, width, height);

        //create title for the screen
        Text scenetitle = new Text("Welcome!");

        //Set the ID for css
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
        userTextField.setPromptText("User Name");
        grid.add(userTextField, 1, 1);

        //Created in a similar fashion as above.
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        //Created in a similar fashion as above.
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Password");
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
                    attemptsLeft.setText("Logged in");
                    //action.loginUser(primaryStage, scene.getWidth(), scene.getHeight());
                } else {
                    action.invalidUser(actiontarget);
                }
            }else{
                actiontarget.setId("actionTarget");
                actiontarget.setText("   Username and password fields are required.");
            }
        });

        btn2.setOnAction(e -> {
            /**
             * This little button listen creates the signup screen.
             * It then sets the primary stage var as the new screen.
             * At last it shows the scene, taking the user to the
             * signup scene.
             */
            SignUpScreen signUpScreenObj = new SignUpScreen();
            signUpScreenObj.createSignUpScene(loginScene.getWidth(), loginScene.getHeight());
            Main.getPrimaryStageVar().setScene(signUpScreenObj.getSignUpScene());
            Main.getPrimaryStageVar().show();
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

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        setLoginScene(scene);
    }

    //Setters
    public static void setLoginScene(Scene scene){
        loginScene = scene;
    }

    //Getters
    public Scene getLoginScene(){
        return(loginScene);
    }
    public double getLoginSceneWidth(){
        return(loginScene.getWidth());
    }
    public double getLoginSceneHeight(){
        return (loginScene.getHeight());
    }

}
