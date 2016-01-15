package sample.Controllers;

import sample.Main;
import sample.Views.*;

/**
 * Created by augustus on 1/15/16.
 * Show all the views/
 */
public class ShowViews {
    public static void showHomeScreen(double width, double height){
        HomeScreen.createHomeScene(width, height);
        Main.getPrimaryStageVar().setScene(HomeScreen.getHomeScene());
    }
}
