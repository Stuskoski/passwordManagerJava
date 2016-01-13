package sample;

import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by augustus on 1/13/16.
 */
public class loginPageActions {
        private static int counter = 0;


    public int checkUserLogin(String userName, String password, Text attemptsLeft){

        int check=0;

        check=checkCounter(counter, attemptsLeft);

        System.out.println(userName + " " + password + " " + counter);
        if(check == 1) {
            if (userName.equals("Augustus") && password.equals("Rutkoski")) {
                return 1;
            } else {
                counter++;
                return 0;
            }
        }else{
            return 0;
        }
    }

    //Go to the newUser signup scene
    public void newUser(Stage primaryStage){
        createScenes newScene = new createScenes();
        newScene.createSignUpScene(primaryStage);
    }

    //Login the user and go to home scene
    public void loginUser(Stage primaryStage){
        createScenes newScene = new createScenes();
        newScene.createHomeScene(primaryStage);
    }

    //Prints that the user/password is invalid
    public void invalidUser(Text actiontarget) {
        actiontarget.setId("actionTarget");
        actiontarget.setText("Invalid Username/Password");

    }

    private int checkCounter(int counter, Text attemptsLeft){
        int left;
        if(counter >= 3){
            attemptsLeft.setId("actionTarget");
            attemptsLeft.setText("User attempts exceeded 3 tries.");
            return 0;
        }else{
            left = (3 - counter);
            attemptsLeft.setId("actionTarget");
            attemptsLeft.setText(left + " attempts left.");
            return 1;
        }
    }
}
