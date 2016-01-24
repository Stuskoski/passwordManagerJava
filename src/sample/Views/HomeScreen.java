package sample.Views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.Models.EntryObjectList;
import sample.Models.UserPasswordFileActions;
import sample.Views.Tabs.BackupTab;
import sample.Views.Tabs.CreateEntryTab;
import sample.Views.Tabs.HomeTab;
import sample.Views.Tabs.PasswordGeneratorTab;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Created by augustus on 1/14/16.
 * This is the main landing page after logging in.  It has a menu bar
 * that lets you access the functions of the whole application.
 */
public class HomeScreen{
    private static Scene homeScene;
    private static Tab homeTab;
    private static Tab createEntryTabPrivate;
    private static Tab passwordGenTabPrivate;
    private static Tab backupTabPrivate;

    public static void createHomeScene(double width, double height){

        BorderPane root = new BorderPane();
        VBox topContainer = new VBox();  //Creates a container to hold all Menu Objects.
        MenuBar mainMenu = new MenuBar();  //Creates our main menu to hold our Sub-Menus.
        TabPane tabPane = new TabPane();
        //FlowPane flow = new FlowPane(Orientation.HORIZONTAL);

        mainMenu.setId("mainMenu");
        tabPane.setId("tabPane");


        //topContainer.getChildren().add(flow);
        topContainer.getChildren().add(mainMenu);
        topContainer.getChildren().add(tabPane);

        root.setTop(topContainer);

        //Declare sub-menus and add to main menu.
        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu tabs = new Menu("Tabs");
        Menu help = new Menu("Help");
        Menu backup = new Menu("Backup");

        //Create and add the "File" sub-menu options.
        MenuItem save = new MenuItem("Save");
        save.setStyle("-fx-text-fill: black;");
        Menu export = new Menu("Export");
        export.setStyle("-fx-text-fill: black;");
        MenuItem logout = new MenuItem("Logout");
        logout.setStyle("-fx-text-fill: black;");
        MenuItem exitApp = new MenuItem("Exit");
        exitApp.setStyle("-fx-text-fill: black;");
        Menu showTabs = new Menu("Show");
        showTabs.setStyle("-fx-text-fill: black;");

        //Create submenu items for backup
        MenuItem importBackup = new MenuItem("Import Backup File");
        MenuItem exportBackup = new MenuItem("Export Backup File");
        importBackup.setStyle("-fx-text-fill: black;");
        exportBackup.setStyle("-fx-text-fill: black;");

        //create submenu items for export
        MenuItem exportClear = new MenuItem("Export Passwords in cleartext (Not Encrypted)");
        MenuItem exportEncrypted = new MenuItem("Export Passwords in ciphertext (Encrypted)");
        exportEncrypted.setStyle("-fx-text-fill: black;");
        exportClear.setStyle("-fx-text-fill: black;");

        //Create submenu items for showTabs
        MenuItem showCreateEntry = new MenuItem("Create Entry");
        showCreateEntry.setStyle("-fx-text-fill: black;");
        MenuItem showPasswordGeneratorTab = new MenuItem("Password Generator");
        showPasswordGeneratorTab.setStyle("-fx-text-fill: black;");
        MenuItem showBackupTab = new MenuItem("Backup");
        showBackupTab.setStyle("-fx-text-fill: black;");

        //Add the submenu items to backup
        backup.getItems().addAll(importBackup, exportBackup);

        //Add the submenu items to showTabs
        showTabs.getItems().addAll(showCreateEntry, showPasswordGeneratorTab, showBackupTab);

        //Add the submenu items to export
        export.getItems().addAll(exportEncrypted, exportClear);

        //Add all the buttons to the file menu
        file.getItems().addAll(save, export,logout,exitApp);

        //Add to tabs menu
        tabs.getItems().addAll(showTabs);

        //Create and add the "Edit" sub-menu options.
        MenuItem properties = new MenuItem("Properties");
        properties.setStyle("-fx-text-fill: black;");
        edit.getItems().add(properties);

        //Create and add the "Help" sub-menu options.
        MenuItem visitWebsite = new MenuItem("Visit Website");
        help.getItems().add(visitWebsite);
        visitWebsite.setStyle("-fx-text-fill: black;");

        //Add the 3 main options
        mainMenu.getMenus().addAll(file, edit, tabs, backup, help);

        //Create the tabs and then add them
        Tab homeTab1 = new Tab();
        homeTab1.setText("Home");
        homeTab1 = HomeTab.createHomeTab(homeTab1);
        homeTab1.setClosable(false); //Unable to close tab
        tabPane.getTabs().add(homeTab1);
        homeTab = homeTab1;

        Tab createEntryTab2 = new Tab();
        createEntryTab2.setText("New Entry");
        createEntryTab2 = CreateEntryTab.createEntryTab(createEntryTab2);
        createEntryTab2.setClosable(true);
        tabPane.getTabs().add(createEntryTab2);
        createEntryTabPrivate = createEntryTab2;
        final Tab test = createEntryTab2;

        Tab passwordGeneratorTab3 = new Tab();
        passwordGeneratorTab3.setText("Password Generator");
        passwordGeneratorTab3 = PasswordGeneratorTab.createPasswordGeneratorTab(passwordGeneratorTab3);
        passwordGeneratorTab3.setClosable(true);
        tabPane.getTabs().add(passwordGeneratorTab3);
        passwordGenTabPrivate = passwordGeneratorTab3;

        Tab backupTab4 = new Tab();
        backupTab4.setText("Backup");
        backupTab4 = BackupTab.createBackupTab(backupTab4);
        backupTab4.setClosable(true);
        tabPane.getTabs().add(backupTab4);
        backupTabPrivate = backupTab4;

        //Create scene
        Scene scene = new Scene(root, width, height);

        //Add css sheet
        scene.getStylesheets().add("sample/Login.css");

        exitApp.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Exit Password Manager?");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //save progress
                UserPasswordFileActions.writeObjectsToFile(EntryObjectList.getObjectList());
                UserPasswordFileActions.encrypt(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj"),
                        new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/.EncryptedObj"));
                try {
                    Files.deleteIfExists(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj").toPath());
                } catch (IOException e) {
                    System.out.println("An error occurred when exiting.");
                }
                System.exit(0);
            }
        });

        //Add a hotkey for the listener.  Automatically adds the Ctrl+E in the menu bar.
        exitApp.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        logout.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Logout?");
            alert.setHeaderText(null);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //save progress
                UserPasswordFileActions.writeObjectsToFile(EntryObjectList.getObjectList());
                UserPasswordFileActions.encrypt(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj"),
                        new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/.EncryptedObj"));
                try {
                    Files.deleteIfExists(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj").toPath());
                } catch (IOException e) {
                    System.out.println("An error occured when exiting.");
                }
                logoutUser();
            }
        });

        logout.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        showCreateEntry.setOnAction(event -> {
            Tab createEntryTabWithBtn = new Tab();
            createEntryTabWithBtn.setText("New Entry");
            createEntryTabWithBtn = CreateEntryTab.createEntryTab(createEntryTabWithBtn);
            createEntryTabWithBtn.setClosable(true);
            tabPane.getTabs().add(createEntryTabWithBtn);
        });

        showPasswordGeneratorTab.setOnAction(event -> {
            Tab passwordGeneratorWithBtn = new Tab();
            passwordGeneratorWithBtn.setText("Password Generator");
            passwordGeneratorWithBtn = PasswordGeneratorTab.createPasswordGeneratorTab(passwordGeneratorWithBtn);
            passwordGeneratorWithBtn.setClosable(true);
            tabPane.getTabs().add(passwordGeneratorWithBtn);
        });

        showBackupTab.setOnAction(event1 -> {
            Tab backupTabWithBtn = new Tab();
            backupTabWithBtn.setText("Backup");
            backupTabWithBtn = BackupTab.createBackupTab(backupTabWithBtn);
            backupTabWithBtn.setClosable(true);
            tabPane.getTabs().add(backupTabWithBtn);
        });

        save.setOnAction(event -> {
            UserPasswordFileActions.writeObjectsToFile(EntryObjectList.getObjectList());
            UserPasswordFileActions.encrypt(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj"),
                    new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/.EncryptedObj"));
        });

        //If user tries to close app, save info and delete file
        Main.getPrimaryStageVar().setOnCloseRequest(event -> {
            UserPasswordFileActions.writeObjectsToFile(EntryObjectList.getObjectList());
            UserPasswordFileActions.encrypt(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj"),
                    new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/.EncryptedObj"));
            try {
                Files.deleteIfExists(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/." + LoginScreen.getLoggedInUser() + "Obj").toPath());
            } catch (IOException e) {
                System.out.println("An error occurred when exiting.");
            }
        });

        //Exporting to a cleartext file
        exportClear.setOnAction(event -> {

                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save File");
                    File exportClearFile = fileChooser.showSaveDialog(Main.getPrimaryStageVar());
                    if (exportClearFile != null) {
                        UserPasswordFileActions.exportClear(exportClearFile);
                        System.out.println(exportClearFile.toString());
                    }
        });

        importBackup.setOnAction(event -> {
            createImportScreen.createImport();
        });

        exportBackup.setOnAction(event -> {
            createExportScreen.createExport();
        });

        setHomeScene(scene);
    }

    //Logout the user by sending them back to the login screen
    private static void logoutUser() {
        LoginScreen.setLoggedInUser("");
        Main.getPrimaryStageVar().setScene(LoginScreen.getLoginScene());
    }



    public static Scene getHomeScene(){
        return(homeScene);
    }
    public static void setHomeScene(Scene scene){ homeScene = scene; }
    public static Tab getHomeTab(){ return homeTab; }
    public static Tab getBackupTab(){ return backupTabPrivate; }
}
