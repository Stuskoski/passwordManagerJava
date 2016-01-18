package sample.Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import sample.Main;

import javax.swing.*;
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

        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(tabPane);

        root.setTop(topContainer);

        //Declare sub-menus and add to main menu.
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu help = new Menu("Help");

        //Create and add the "File" sub-menu options.
        MenuItem openFile = new MenuItem("Open File");
        MenuItem logout = new MenuItem("Logout");
        MenuItem exitApp = new MenuItem("Exit");
        file.getItems().addAll(openFile,logout,exitApp);

        //Create and add the "Edit" sub-menu options.
        MenuItem properties = new MenuItem("Properties");
        edit.getItems().add(properties);

        //Create and add the "Help" sub-menu options.
        MenuItem visitWebsite = new MenuItem("Visit Website");
        help.getItems().add(visitWebsite);

        mainMenu.getMenus().addAll(file, edit, help);

        for (int i = 0; i < 5; i++) {
            Tab tab = new Tab();
            tab.setText("Tab " + i);
            HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Create scene
        Scene scene = new Scene(root, width, height);

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        exitApp.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Are you ready to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.exit(0);
            }
        });

        logout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Logout?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                logoutUser();
            }
        });

        setHomeScene(scene);
    }

    private static void logoutUser() {
        Main.getPrimaryStageVar().setScene(LoginScreen.getLoginScene());
    }

    public static Scene getHomeScene(){
        return(homeScene);
    }

    public static void setHomeScene(Scene scene){ homeScene = scene; }


}
