package sample.Views;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sample.Main;
import sample.Models.LoginUser;

/**
 * Created by augustus on 1/14/16.
 */
public class HomeScreen {
    private static Scene homeScene;

    public static void createHomeScene(double width, double height){
        //Create new grid
        GridPane grid = new GridPane();

        //Set alignment to center instead of top left corner
        grid.setAlignment(Pos.TOP_LEFT);

        //Create scene
        Scene scene = new Scene(grid, width, height);

        //Shows the grid lines. Useful for debugging purposes
        grid.setGridLinesVisible(false);

        TabPane tabPane = new TabPane();
        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < 5; i++) {
            Tab tab = new Tab();
            tab.setText("Home");
            HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            hbox.setOnMouseClicked(Event->{
                System.out.print("you hit me");
            });
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        grid.getChildren().add(borderPane);

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        setHomeScene(scene);
    }

    public static Scene getHomeScene(){
        return(homeScene);
    }

    public static void setHomeScene(Scene scene){ homeScene = scene; }


}
