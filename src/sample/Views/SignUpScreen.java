package sample.Views;

import com.sun.deploy.panel.ExceptionListDialog;
import javafx.beans.property.StringProperty;
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
import sample.Main;
import sample.Models.RegisterUser;

import java.io.File;
import java.io.IOException;


/**
 * Created by augustus on 1/14/16.
 * This is the signup view.  Action listeners are created here
 * along with the view of the whole signup screen.
 */
public class SignUpScreen {
    private static Scene signUpScene;
    private static Boolean validEmail = false;
    private static Boolean emailExists = false;
    private static Boolean validUser;

    public static void createSignUpScene(double width, double height){
        File test = new File(".accountConfig-DoNotEdit");
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
        Text scenetitle = new Text("Sign Up New User");

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
        userTextField.setPromptText("Required");
        grid.add(userTextField, 1, 1);

        //Created in a similar fashion as above.
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        //Created in a similar fashion as above.
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Required");
        grid.add(pwBox, 1, 2);

        //Create label object with the text "User Name:" at column 0, row 1
        Label email = new Label("Email:");
        grid.add(email, 0, 3);

        //Creates a text field object that can be edited at column 1, row 1.
        TextField emailTextField = new TextField();
        emailTextField.setPromptText("Optional");
        grid.add(emailTextField, 1, 3);

        //Shows the grid lines. Useful for debugging purposes
        grid.setGridLinesVisible(false);

        //Creates a button called btn with the text "Log in"
        Button register = new Button("Register");
        Button back = new Button("Back");

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
        hbBtn.getChildren().add(back);
        hbBtn.getChildren().add(register);


        //Hbox pane added to the grid in column 1, row 4
        grid.add(hbBtn, 1, 4);

        //Add a text control for displaying the message
        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6, 3, 1);

        //Add another message for attempts left
        final Text userValidMsg = new Text();
        grid.add(userValidMsg, 0, 7, 3, 1);

        //Make the columns a default size meant to hold the whole error msg without resizing
        grid.getColumnConstraints().add(new ColumnConstraints(100));
        grid.getColumnConstraints().add(new ColumnConstraints(250));

        //create an event handler
        register.setOnAction(e -> {
            actiontarget.setText(null);
            if((userTextField.getText().length() != 0) && (pwBox.getText().length() != 0)) {
                //Email is not required but lets check if it exists and is valid
                if(emailTextField.getText().length()>0){
                    emailExists = true;
                    if(isValidEmailAddress(emailTextField.getText())){
                        validEmail = true;
                    }else{
                        actiontarget.setId("actionTarget");
                        actiontarget.setText("   You must use a valid email.");
                    }

                }

                //Now check if the password is of valid length.  If so, check the email flag earlier.
                if((pwBox.getText().length() >= 8)) {
                    if(emailExists && validEmail) {
                        if(validUser) {
                            RegisterUser.registerUserWithEmail(userTextField.getText(), pwBox.getText(), emailTextField.getText());
                        }
                    }else{
                        if(validUser) {
                            RegisterUser.registerUserWithoutEmail(userTextField.getText(), pwBox.getText());
                        }
                    }
                }else{
                    actiontarget.setId("actionTarget");
                    actiontarget.setText("   Your password must be at LEAST 8 characters.");
                }
            }else{
                actiontarget.setId("actionTarget");
                actiontarget.setText("   Username and password fields are required.");
            }
        });

        back.setOnAction(e -> {
            /**
             * This little button listen creates the signup screen.
             * It then sets the primary stage var as the new screen.
             * At last it shows the scene, taking the user to the
             * signup scene. Must recreate scene for resizing issues.
             */
            LoginScreen loginScreenObj = new LoginScreen();
            loginScreenObj.createLoginScene(signUpScene.getWidth(), signUpScene.getHeight());
            Main.getPrimaryStageVar().setScene(loginScreenObj.getLoginScene());
            Main.getPrimaryStageVar().show();
        });

        //Add listeners to submit/username boxes with the enter key
        userTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                //Fire the button as if you were clicking it
                register.fire();
            }
        });
        pwBox.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                register.fire();
            }
        });
        emailTextField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)){
                register.fire();
            }
        });
        //Every time a key is released, it checks if the user exists
        userTextField.setOnKeyReleased(event -> {
            try {
                if(RegisterUser.checkIfUserExists(userTextField.getText())){
                    validUser = false;
                    userValidMsg.setId("actionTarget");
                    userValidMsg.setText("   That user name exists already.");
                }else{
                    if(userTextField.getText().length() == 0){
                        validUser = false;
                        userValidMsg.setId("actionTarget");
                        userValidMsg.setText("   User name may not be empty.");
                    }else {
                        validUser = true;
                        userValidMsg.setId("userNameAvailable");
                        userValidMsg.setText("   That user name is available.");
                    }
                }
            } catch (IOException userCheck) {
                userCheck.printStackTrace();
            }
        });

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        setSignUpSceneLocal(scene);

    }

    //This function checks if the email passed is in a valid format.  Email is optional so not always called.
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private static void setSignUpSceneLocal(Scene scene) {
        signUpScene = scene;
    }

    public Scene getSignUpScene(){
        return(signUpScene);
    }

    public void setSignUpScene(Scene scene){
        signUpScene = scene;
    }
}
