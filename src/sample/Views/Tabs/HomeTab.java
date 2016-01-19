package sample.Views.Tabs;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import sample.Models.EntryObjectList;
import sample.Models.EntryObjects;
import sample.Models.GetUsersPasswordEntries;
import sample.Models.UserPasswordFileActions;
import sample.Views.LoginScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustus on 1/18/16.
 * This function creates the home tab
 */
public class HomeTab {
    //Create the home tab for the home screen.
    public static Tab createHomeTab(Tab homeTab){
        List<EntryObjects> userEntries;

        //Create the default pane that will hold everything
        BorderPane home = new BorderPane();

        //Create the welcome msg for alignment at the top center.  Set it on the pane as well.
        HBox welcomeMsg = new HBox();
        welcomeMsg.setAlignment(Pos.CENTER);
        welcomeMsg.getChildren().add(new Label("Welcome " + LoginScreen.getLoggedInUser() + "!"));
        home.setTop(welcomeMsg);

        GridPane homeGrid = new GridPane();

        List<EntryObjects> userEntrie = new ArrayList<>();
        EntryObjects testd = new EntryObjects();
        testd.setName("BoA");
        testd.setDescription("This is my BoA Account Pass");
        testd.setPassword("summer123");
        userEntrie.add(testd);
        EntryObjectList.setObjectList(userEntrie);
        UserPasswordFileActions.writeObjectsToFile(EntryObjectList.getObjectList());

        //Get the objects from the file
        userEntries = UserPasswordFileActions.getObjectsFromFile();

        for(EntryObjects fileEntries : userEntries){
            System.out.println(fileEntries.getName() + "\n" + fileEntries.getDescription() + "\n" + fileEntries.getPassword());
        }

        home.setCenter(homeGrid);


        homeTab.setContent(home);

        return homeTab;
    }
}
