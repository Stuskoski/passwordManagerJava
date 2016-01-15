package sample.Models;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by augustus on 1/14/16.
 * This class will check if a config file for registered users exists
 * if it does not, then it will create the file and add the user.
 * Email is not required so there is a quick check if it exists
 * or not.
 */
public class RegisterUser {
    public static void registerUserWithEmail(String userName, String password, String email){

        String salt;
        String start = "Start:";
        String end = "End:";
        String seperator = "--------------------------------------------------------------";

        //Use the randomPassword function to get a salt of length 10.
        salt = HashThoseStrings.generateRandomPassword(10);

        //Concat the hash and password to make one String to be hashed.
        password = salt+password;

        //Run the hash 1000 times to increase cracking time
        for (int i = 0; i<=1000; i++){
            try {
                password = HashThoseStrings.myHashPasswordFunction(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        //Write all the account information to a file
        List<String> lines = Arrays.asList(("User: "+userName), start, password, end, salt, ("Email:"+email), seperator);
        File testFile = new File(".accountConfig-DoNotEdit");
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

        String salt;
        String start = "Start:";
        String end = "End:";
        String seperator = "--------------------------------------------------------------";
        File test = new File(".accountConfig-DoNotEdit");

        //Use the randomPassword function to get a salt of length 10.
        salt = HashThoseStrings.generateRandomPassword(10);

        //Concat the hash and password to make one String to be hashed.
        password = salt+password;

        //Run the hash 1000 times to increase cracking time
        for (int i = 0; i<=1000; i++){
            try {
                password = HashThoseStrings.myHashPasswordFunction(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        //Write all the account information to a file
        List<String> lines = Arrays.asList(("User: "+userName), start, password, end,  salt, seperator);
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

        //If file doesn't exist, then user doesn't exist.
        if(!test.exists()){
            return(false);
        }

        BufferedReader bf = new BufferedReader(new FileReader(".accountConfig-DoNotEdit"));

        while((line = bf.readLine()) != null){
            if(line.startsWith("User: ")){
                String[] testUser = line.split("User: ");
                if(testUser[1].equals(userName)){
                    bf.close();
                    return (true);
                }else{
                    bf.close();
                    return(false);
                }
            }
        }

        bf.close();
        return(true);
    }

}
