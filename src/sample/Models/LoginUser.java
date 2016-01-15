package sample.Models;

import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by augustus on 1/14/16.
 * This class will attempt to log in the user.
 * The class will open the account file and look for
 * a valid user and see if their hash matches the
 * hash generated from password inputted.  I chose to
 * do a file instead of a database just to see how it
 * will be.  Probably not as secure.
 */
public class LoginUser {
    private static File testFile = new File(".accountConfig-DoNotEdit");
    private static int counter = 1;
    private static boolean check = false;

    public static boolean loginUser(String userName, String password, Text attemptsLeft){

        checkTries(attemptsLeft, userName);

        try {
            return (checkFile() && checkUserName(userName) && checkPassWord(password, userName) && check);
        } catch (IOException e) {
            System.out.println("Error while logging in user");
            return (false);
        }
    }
    private static boolean checkFile(){
        return (testFile.exists());
    }

    private static boolean checkUserName(String userName){

        try {
            return(RegisterUser.checkIfUserExists(userName));
        } catch (IOException e) {
            System.out.println("Unable to check for user in LoginUser.");
            return (false);
        }
    }

    /**
     * This function will check the password for the userName.  It gets passed a password
     * pulls the matching hash and salt from our file and then checks if the hashes match.
     * @param password is the password from the login form
     * @param userName is the username from the login form
     * @return returns a boolean
     * @throws IOException
     */
    private static boolean checkPassWord(String password, String userName) throws IOException {
        String line, passwordLine="";
        File checkUserPasswordFile = new File(".accountConfig-DoNotEdit");
        boolean flag=false, correctUser=false, done=true;

        //If file doesn't exist, then user/pass doesn't exist.
        if(!checkUserPasswordFile.exists()){
            return(false);
        }

        BufferedReader bf = new BufferedReader(new FileReader(".accountConfig-DoNotEdit"));

        while(((line = bf.readLine()) != null) && done){

            if(line.startsWith("User: ")){
                String[] testUser = line.split("User: ");
                if(testUser[1].equals(userName)){
                    correctUser=true;
                }
            }

            if(line.startsWith("Start: ") && correctUser){
                flag=true;
            }

            if(flag) {
                passwordLine += line;
                done = false;
            }
        }

        //Get rid of the start line identifier
        passwordLine = passwordLine.replaceAll("Start: ", "");

        //This code section will automatically check the hash with the entered password.
        try {
            bf.close();
            return (SecureHashing.validatePassword(password, passwordLine));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error while validating password");
        }

        //Always return false unless otherwise
        bf.close();
        return(false);
    }


    private static void checkTries(Text attemptsLeft, String userName){
        int left;
        if(LoginUser.checkUserName(userName)) {
            if (counter >= 4) {
                attemptsLeft.setId("actionTarget");
                attemptsLeft.setText("User attempts exceeded 3 tries.");
                check = false;
            } else {
                left = (3 - counter);
                attemptsLeft.setId("actionTarget");
                if (left == 1) {
                    attemptsLeft.setText(left + " attempt left.");
                } else {
                    attemptsLeft.setText(left + " attempts left.");
                }
                check = true;
                counter++;
            }
        }else{
            attemptsLeft.setId("actionTarget");
            attemptsLeft.setText("That user does not exist");
        }
    }

    public static void resetPasswordCounter(){
        counter = 0;
    }
}

