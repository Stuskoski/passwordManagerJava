package sample.Views;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import sample.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by augustus on 1/14/16.
 * This is the main landing page after logging in.  It has a menu bar
 * that lets you access the functions of the whole application.
 */
public class HomeScreen {
    private static Scene homeScene;

    public static void createHomeScene(double width, double height){

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();  //Creates a container to hold all Menu Objects.
        MenuBar mainMenu = new MenuBar();  //Creates our main menu to hold our Sub-Menus.
        TabPane tabPane = new TabPane();
        //FlowPane flow = new FlowPane(Orientation.HORIZONTAL);

        //topContainer.getChildren().add(flow);
        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(tabPane);

        root.setTop(topContainer);

        //Declare sub-menus and add to main menu.
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //Create and add the "File" sub-menu options.
        MenuItem export = new MenuItem("Export");
        MenuItem logout = new MenuItem("Logout");
        MenuItem exitApp = new MenuItem("Exit");
        file.getItems().addAll(export,logout,exitApp);

        //Create and add the "Edit" sub-menu options.
        MenuItem properties = new MenuItem("Properties");
        edit.getItems().add(properties);

        //Create and add the "Help" sub-menu options.
        MenuItem visitWebsite = new MenuItem("Visit Website");
        help.getItems().add(visitWebsite);

        final Menu leftSpacer = new Menu();
        leftSpacer.setText("        ");


        mainMenu.getMenus().addAll(file, edit, help);


        //Create the tabs and then add them
        Tab homeTab1 = new Tab();
        homeTab1.setText("Home");
        homeTab1 = createHomeTab(homeTab1);
        //homeTab1.setContent(hbox);
        tabPane.getTabs().add(homeTab1);

        //Unable to close tabs
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Create scene
        Scene scene = new Scene(root, width, height);

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        exitApp.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Exit Password Manager?");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.exit(0);
            }
        });

        //Add a hotkey for the listener.  Automatically adds the Ctrl+E in the menu bar.
        exitApp.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        logout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Logout?");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                logoutUser();
            }
        });

        logout.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        setHomeScene(scene);
    }

    //Logout the user by sending them back to the login screen
    private static void logoutUser() {
        LoginScreen.setLoggedInUser("");
        Main.getPrimaryStageVar().setScene(LoginScreen.getLoginScene());
    }

    //Create the home tab for the home screen.
    private static Tab createHomeTab(Tab homeTab){
        //Create the default pane that will hold everything
        BorderPane home = new BorderPane();

        //Create the welcome msg for alignment at the top center.  Set it on the pane as well.
        HBox welcomeMsg = new HBox();
        welcomeMsg.setAlignment(Pos.CENTER);
        welcomeMsg.getChildren().add(new Label("Welcome " + LoginScreen.getLoggedInUser() + "!"));
        home.setTop(welcomeMsg);

        GridPane homeGrid = new GridPane();
        home.setCenter(homeGrid);


        homeTab.setContent(home);

        return homeTab;
    }

    public static Scene getHomeScene(){
        return(homeScene);
    }
    public static void setHomeScene(Scene scene){ homeScene = scene; }
}
