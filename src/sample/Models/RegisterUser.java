package sample.Models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by augustus on 1/14/16.
 * This class will check if a config file for registered users exists
 * if it does not, then it will create the file and add the user.
 * Email is not required so there is a quick check if it exists
 * or not.
 */
public class RegisterUser {
    public static void registerUserWithEmail(String userName, String password, String email){
        String start = "Start: ";
        String seperator = "--------------------------------------------------------------";
        File testFile = new File(".accountConfig-DoNotEdit");

        try {
            password = SecureHashing.createHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error creating hash for password while registering user.");
        }

        //Write all the account information to a file
        List<String> lines = Arrays.asList(("User: "+userName), start+password, ("Email:"+email), seperator);
        Path filePath = Paths.get(".accountConfig-DoNotEdit");
        try {
            if(testFile.exists()) {
                Files.write(filePath, lines, StandardOpenOption.APPEND);
            }else{
                Files.write(filePath, lines);
            }
        } catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

    public static void registerUserWithoutEmail(String userName, String password){
        String start = "Start: ";
        String seperator = "--------------------------------------------------------------";
        File test = new File(".accountConfig-DoNotEdit");

        try {
            password = SecureHashing.createHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("Error creating hash for password while registering user.");
        }

        //Write all the account information to a file
        List<String> lines = Arrays.asList(("User: "+userName), start+password, seperator);
        Path file = Paths.get(".accountConfig-DoNotEdit");
        try {
            if(test.exists()) {
                Files.write(file, lines, StandardOpenOption.APPEND);
            }else{
                Files.write(file, lines);
            }
        } catch (IOException e) {
            System.out.println("Error While writing to file");
        }
    }

    public static boolean checkIfUserExists(String userName) throws IOException {

        String line;
        File test = new File(".accountConfig-DoNotEdit");
        ArrayList<String> user = new ArrayList<>();

        //If file doesn't exist, then user doesn't exist.
        if(!test.exists()){
            return(false);
        }

        BufferedReader bf = new BufferedReader(new FileReader(".accountConfig-DoNotEdit"));

        while((line = bf.readLine()) != null){
            if(line.startsWith("User: ")){
                String[] testUser = line.split("User: ");
                user.add(testUser[1]);
            }
        }

        if(user.contains(userName)){
            bf.close();
            return(true);
        }else{
            bf.close();
            return(false);
        }
    }
}
