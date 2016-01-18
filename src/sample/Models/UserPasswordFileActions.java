package sample.Models;

import com.sun.org.apache.xpath.internal.operations.Bool;
import sample.Views.LoginScreen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by augustus on 1/18/16.
 * All the actions needed for the userPasswordFiles
 */
public class UserPasswordFileActions {

    public static boolean isLinux;

    /**
     * This function will check for the userFiles.  If the directory/file is not present
     * it will create them. Checks for the Linux OS for hidden files with . notation.  Else
     * creates them via the Windows ways.
     */
    public static void createUserFile() {
        File linuxDirectory = new File(".UserFiles");
        File windowsDirectory = new File("UserFiles");
        File linuxPasswordFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser());
        File windowsPasswordFile = new File("UserFiles/." + LoginScreen.getLoggedInUser());


        //This guy just handles windows and linux directory creation.
        //If linux create a hidden directory with a . , else create it
        //with windows exec.  Just one extra step to find files.
        try {
            if (isLinux) {
                if (!linuxDirectory.exists()) {
                    if (linuxDirectory.mkdir()) {
                        System.out.println("Directory Created For Linux");
                       // Set<PosixFilePermission> perms =
                       //         PosixFilePermissions.fromString("rw-------");
                       // Files.setPosixFilePermissions(linuxDirectory.toPath(), perms);
                    } else {
                        System.out.println("Error:  Unable to create directory for Linux");
                    }
                }

                if (!linuxPasswordFile.exists()) {
                    try {
                        if(linuxPasswordFile.createNewFile()){
                            System.out.println("Linux user password file created");
                           // Set<PosixFilePermission> perms =
                           //         PosixFilePermissions.fromString("rw-------");
                           // Files.setPosixFilePermissions(linuxPasswordFile.toPath(), perms);
                        }else{
                            System.out.println("Unable to create Linux user password file.");
                        }
                    } catch (IOException e) {
                        System.out.println("Unable to create Linux user password file");
                    }
                }
            } else {
                //If directory doesnt exist, create it and set it as hidden in windows.
                if (!windowsDirectory.exists()) {
                    if (windowsDirectory.mkdir()) {
                        System.out.println("Directory Created For Windows");
                        Path windowsDir = Paths.get("UserFiles");
                        Files.setAttribute(windowsDir, "dos:hidden", true);
                    } else {
                        System.out.println("Error: Unable to create directory for Windows");
                    }
                }
                //If password doesnt exist, create it and set it as hidden in windows.
                if (!windowsPasswordFile.exists()) {
                    try {
                        if(windowsPasswordFile.createNewFile()){
                            System.out.println("Windows user password file created.");
                            Path windowsFile = Paths.get("UserFiles/." + LoginScreen.getLoggedInUser());
                            Files.setAttribute(windowsFile, "dos:hidden", true);
                        }else{
                            System.out.println("Unable to create Windows user password file.");
                        }
                    } catch (IOException e) {
                        System.out.println("Unable to create windows user password file.");
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Unsupported OS.  Only Linux/Windows are currently supported.");
        }
    }

    public static void writeObjectsToFile(List<EntryObjects> objectsToFile){
        File linuxPasswordFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser());
        File windowsPasswordFile = new File("UserFiles/." + LoginScreen.getLoggedInUser());

        //Quick check for user file if it exists, if not create it.
        createUserFile();

        //Need to add a check here instead that checks if their is any data in the file.

        if(isLinux){
            try {
                FileOutputStream fout = new FileOutputStream(linuxPasswordFile.toString());
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(objectsToFile);
            } catch (IOException e) {
                System.out.println("Error while trying to write objects to Linux file");
            }

        }else{
            try {
                FileOutputStream fout = new FileOutputStream(windowsPasswordFile.toString());
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(objectsToFile);
            } catch (IOException e) {
                System.out.println("Error while trying to write objects to Windows file");
            }

        }
    }

    //This function gets the objects stored in the users file.
    public static List<EntryObjects> getObjectsFromFile(){
        File linuxPasswordFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser());
        File windowsPasswordFile = new File("UserFiles/." + LoginScreen.getLoggedInUser());
        List<EntryObjects> entries = new ArrayList<>();
        EntryObjects userEntries;

        if(isLinux){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(linuxPasswordFile.toString()));
                entries = (List<EntryObjects>) objectInputStream.readObject();
                return entries;
            } catch (IOException e) {
                System.out.println("No objects to read from file.");
            } catch (ClassNotFoundException e) {
                System.out.println("Error while trying to read objects from Linux file.");
            }

        }else{
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(windowsPasswordFile.toString()));
                entries = (List<EntryObjects>) objectInputStream.readObject();
                return entries;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while trying to read objects from Windows file.");
            }
        }
        return entries;
    }

    public static void setIsLinux(Boolean bool) { isLinux = bool; }
    public static boolean getIsLinux() { return isLinux; }
}
