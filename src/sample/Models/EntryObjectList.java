package sample.Models;

import sample.Views.LoginScreen;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustus on 1/19/16.
 */
public class EntryObjectList implements Serializable{
    private static List<EntryObjects> ObjectList = new ArrayList<>();

    public static void checkForObjInFile(File encFile){
        File decryptFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir" + "/.Decrypted");
        //File plainFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj");
        UserPasswordFileActions.decrypt(encFile, decryptFile);
        ObjectList = UserPasswordFileActions.getObjectsFromFile(decryptFile);
        try {
            Files.deleteIfExists(decryptFile.toPath());
            //Files.deleteIfExists(plainFile.toPath());
        } catch (IOException e) {
            System.out.println("Unable to delete decrypted files.");
        }
    }

    //Getter
    public static List<EntryObjects> getObjectList(){
        return ObjectList;
    }

    //Setter
    public static void setObjectList(List<EntryObjects> list){
        ObjectList = list;
    }
}
