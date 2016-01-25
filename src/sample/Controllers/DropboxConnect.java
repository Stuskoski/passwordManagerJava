package sample.Controllers;

import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Views.HomeScreen;
import sample.Views.LoginScreen;
import sample.Views.Tabs.BackupTab;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by augustus on 1/22/16.
 * Dropbox api code.
 */
public class DropboxConnect {

    private static DbxClientV2 dropboxConnection;
    private static final String dropboxAccess = "OnH1oNyF_1sAAAAAAAB5LAXncmdSYiGQbjaTTAsZ5EHaskFCBtLEpNrsLXNE7UBi";
    private static final String key = "p71od6rg4hqctc8";
    private static final String secret = "k78f7mtp25ss8d7";

    public static void authDropbox()
            throws IOException, DbxException {
        Timer timer = new Timer();
        VBox authDropboxVbox = new VBox(15);
        DbxAuthInfo appText;
        DbxAppInfo appInfo;
        if(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey").exists()){
            try {
                appText = DbxAuthInfo.Reader.readFromFile(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey"));
                DbxRequestConfig dbxRequestConfig = new DbxRequestConfig(
                        "dropbox/passwordManager-Stus", Locale.getDefault().toString());

                DbxClientV2 connectionV2 = new DbxClientV2(dbxRequestConfig, appText.accessToken, appText.host);

                setDropboxConnection(connectionV2);
            } catch (JsonReader.FileLoadException e) {
                BackupTab.setStatusMsg("Something went wrong while trying to read from Dropbox Auth file.");
            }
        }else{
            try {

                URL location = DropboxConnect.class.getProtectionDomain().getCodeSource().getLocation();
                System.out.println(location.getFile()+"sample/Resources/dropboxAppAuth.txt");


                appInfo = DbxAppInfo.Reader.readFromFile(new File(System.getProperty("user.home")+"/dropboxAppAuth"));

                DbxRequestConfig dbxRequestConfig = new DbxRequestConfig(
                        "dropbox/passwordManager-Stus", Locale.getDefault().toString());

                DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(dbxRequestConfig, appInfo);

                String authorizeUrl = webAuth.start();

                Button authDropBoxBtn = new Button("Authenticate With Dropbox");
                authDropBoxBtn.setId("dark-btn");
                Label out1 = new Label("1. Go to :");
                TextField urlLink = new TextField(authorizeUrl);
                Label out2 = new Label("2. Click \"Allow\" (you might have to log in first).");
                Label out3 = new Label("3. Copy the authorization code.");

                HBox container = new HBox(7);
                Label out4 = new Label("Enter the authorization code here: ");
                TextField authCode = new TextField();
                container.getChildren().addAll(out4, authCode);

                authDropboxVbox.getChildren().addAll(out1, urlLink, out2, out3, container, authDropBoxBtn, BackupTab.getStatusMsg());

                BackupTab.getBackupGrid().add(authDropboxVbox, 0, 0);

                authDropBoxBtn.setOnAction(event -> {
                    if(authCode.getText().length() != 0){
                        String code;
                        code = authCode.getText().trim();

                        DbxAuthFinish authFinish;
                        try {
                            authFinish = webAuth.finish(code);
                        }
                        catch (DbxException ex) {
                            System.err.println("Error in DbxWebAuth.start: " + ex.getMessage());
                            BackupTab.setStatusMsg("Something went wrong when trying to authenticate with Dropbox.");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(DropboxConnect::removeWarning);
                                }
                            }, 10000);
                            //System.exit(1);
                            return;
                        }

                        // Save auth information to output file.
                        DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.accessToken, appInfo.host);

                        DbxClientV2 connectionV2 = new DbxClientV2(dbxRequestConfig, dropboxAccess);

                        setDropboxConnection(connectionV2);
                        try {
                            DbxAuthInfo.Writer.writeToFile(authInfo, new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey"));
                            System.out.println("Saved authorization information to \"" + new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey").toString() + "\".");
                        }
                        catch (IOException ex) {
                            System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
                            System.err.println("Dumping to stderr instead:");
                            BackupTab.setStatusMsg("Something went wrong while trying to authenticate");
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(DropboxConnect::removeWarning);
                                }
                            }, 10000);
                            try {
                                DbxAuthInfo.Writer.writeToStream(authInfo, System.err);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //System.exit(1);
                            return;
                        }
                        BackupTab.getBackupGrid().getChildren().clear();
                        BackupTab.createBackupTab(HomeScreen.getBackupTab());
                    }else{
                        //Label warning = new Label("Code should not be empty.");
                        //authDropboxVbox.getChildren().add(warning);
                        BackupTab.setStatusMsg("Code should not be empty.");
                        //Timer to remove the msg after 5 seconds
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(DropboxConnect::removeWarning);
                            }
                        }, 10000);
                    }
                });
            }
            catch (JsonReader.FileLoadException ex) {
                System.setErr(new PrintStream(new FileOutputStream(System.getProperty("user.home")+"/error.log")));
                System.err.println("Error reading <app-info-file>: " + ex.getMessage());
                BackupTab.setStatusMsg("Error reading <app-info-file>: " + ex.getMessage());
                //System.exit(1); return;
            }
        }
    }

    public static void setDropboxConnection(DbxClientV2 connection){
        dropboxConnection = connection;
    }
    public static DbxClientV2 getDropboxConnection(){
        return dropboxConnection;
    }
    public static void removeWarning(){
        BackupTab.removeWarning();
    }
}
