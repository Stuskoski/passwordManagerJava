package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Models.UserPasswordFileActions;
import sample.Views.*;

import javax.swing.*;


public class Main extends Application {
    private static Stage primaryStageVar;

    @Override
    public void start(Stage primaryStageStart) {
        //Create variables/objects
        Scene login;
        LoginScreen loginScreen = new LoginScreen();

        //Quick OS check.
        if (System.getProperty("os.name").equals("Linux")) {
            UserPasswordFileActions.setIsLinux(true);
        } else {
            UserPasswordFileActions.setIsLinux(false);
        }

        //Create the login screen with initial width and height
        LoginScreen.createLoginScene(850,675);

        //Get the login screen
        login = loginScreen.getLoginScene();

        //Set the title for the primary stage
        primaryStageStart.setTitle("Password Manager");

        //Set the scene for the primary stage
        primaryStageStart.setScene(login);

        //Creates a variable that mirrors primaryStageStart, will use that from now on.
        setPrimaryStageVar(primaryStageStart);

        //Show the screen with our new variable
        primaryStageVar.show();
    }

    //Getters
    public static Stage getPrimaryStageVar(){
        return primaryStageVar;
    }

    //Setters
    public static void setPrimaryStageVar(Stage primaryStage){
        primaryStageVar = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
