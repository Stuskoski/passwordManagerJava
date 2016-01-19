package sample.Models;

import sample.Views.LoginScreen;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by augustus on 1/18/16.
 * All the actions needed for the userPasswordFiles
 */
public class UserPasswordFileActions {

    private static final String TRANSFORMATION = "AES";
    public static boolean isLinux;

    /**
     * This function will check for the userFiles.  If the directory/file is not present
     * it will create them. Checks for the Linux OS for hidden files with . notation.  Else
     * creates them via the Windows ways.
     */
    public static void createUserFile() {
        File linuxDirectory = new File(".UserFiles");
        File linuxPassDirectory = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir");
        File linuxObjFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj");

        File windowsDirectory = new File("UserFiles");
        File windowsPassDirectory = new File("UserFiles/" + LoginScreen.getLoggedInUser() + "Dir");
        File windowsObjFile = new File("UserFiles/" + LoginScreen.getLoggedInUser() + "Dir/" + LoginScreen.getLoggedInUser() + "Obj");


        //This guy just handles windows and linux directory creation.
        //If linux create a hidden directory with a . , else create it
        //with windows exec.  Just one extra step to find files.
        try {
            if (isLinux) {
                if (!linuxDirectory.exists()) {
                    if (linuxDirectory.mkdir()) {
                        System.out.println("Main Directory Created For Linux");
                       // Set<PosixFilePermission> perms =
                       //         PosixFilePermissions.fromString("rw-------");
                       // Files.setPosixFilePermissions(linuxDirectory.toPath(), perms);
                    } else {
                        System.out.println("Error:  Unable to create directory for Linux");
                    }
                }

                if (!linuxPassDirectory.exists()) {
                    try {
                        if(linuxPassDirectory.mkdir()){
                            System.out.println("Linux user directory created");
                            if(!linuxObjFile.exists()){
                                if(linuxObjFile.createNewFile()){
                                    System.out.println("Linux User Obj File Created.");
                                }else{
                                    System.out.println("Unable to create Linux User Obj File.");
                                }
                            }
                           // Set<PosixFilePermission> perms =
                           //         PosixFilePermissions.fromString("rw-------");
                           // Files.setPosixFilePermissions(linuxPasswordFile.toPath(), perms);
                        }else{
                            System.out.println("Unable to create Linux user directory.");
                        }
                    } catch (IOException e) {
                        System.out.println("Unable to create Linux user password file");
                    }
                }
            } else {
                //If directory doesn't exist, create it and set it as hidden in windows.
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
                if (!windowsPassDirectory.exists()) {
                    try {
                        if(windowsPassDirectory.mkdir()){
                            System.out.println("Windows user directory created.");
                            Path windowsFile = Paths.get("UserFiles/" + LoginScreen.getLoggedInUser()+"Dir");
                            Files.setAttribute(windowsFile, "dos:hidden", true);
                            if(!windowsObjFile.exists()){
                                if(windowsObjFile.createNewFile()){
                                    System.out.println("Windows Obj File Created.");
                                    Path windowsObjFilePath = Paths.get("UserFiles/" + LoginScreen.getLoggedInUser()+"Dir/" + LoginScreen.getLoggedInUser() + "Obj");
                                    Files.setAttribute(windowsObjFilePath, "dos:hidden", true);
                                }else{
                                    System.out.println("Unable to create Windows Obj File.");
                                }
                            }
                        }else{
                            System.out.println("Unable to create Windows Pass Directory.");
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
        File linuxObjFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj" );
        File linuxEncrypted = new File(".UserFiles/." + LoginScreen.getLoggedInUser()+ "Dir" + "/.EncryptedObj");
        File linuxDecrypted = new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir" + "/.Decrypted");
        File windowsPasswordFile = new File("UserFiles/." + LoginScreen.getLoggedInUser());

        //Quick check for user file if it exists, if not create it.
        createUserFile();

        //Need to add a check here instead that checks if their is any data in the file.

        if(isLinux){
            try {
                FileOutputStream fout = new FileOutputStream(linuxObjFile.toString());
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

        encrypt(linuxObjFile, linuxEncrypted);
        decrypt(linuxEncrypted, linuxDecrypted);
    }

    //This function gets the objects stored in the users file.
    public static List<EntryObjects> getObjectsFromFile(){
        File linuxObjFile = new File(".UserFiles/." + LoginScreen.getLoggedInUser()+"Dir/." + LoginScreen.getLoggedInUser() + "Obj" );
        File windowsPasswordFile = new File("UserFiles/." + LoginScreen.getLoggedInUser());
        List<EntryObjects> entries = new ArrayList<>();

        if(isLinux){
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new FileInputStream(linuxObjFile.toString()));
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


    public static void encrypt(File inputFile, File outputFile) {
        doCrypto(Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    public static void decrypt(File inputFile, File outputFile) {
        doCrypto(Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, File inputFile, File outputFile){
        try {

            String salt = "5s9J";

            byte[] shaKey = (salt + LoginScreen.getLoggedInUser() + LoginScreen.getUserPass()).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            shaKey = sha.digest(shaKey);
            shaKey = Arrays.copyOf(shaKey, 16); // use only first 128 bit

            SecretKeySpec secretKeySpec = new SecretKeySpec(shaKey, "AES");

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKeySpec);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void setIsLinux(Boolean bool) { isLinux = bool; }
    public static boolean getIsLinux() { return isLinux; }
}
