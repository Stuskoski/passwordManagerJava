package sample.Views.Tabs;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxFiles;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import sample.Controllers.DropboxConnect;
import sample.Views.LoginScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by augustus on 1/23/16.
 * Generate backup tab
 */
public class BackupTab {
    private static Label statusMsg = new Label("");

    public static Tab createBackupTab(Tab backupTab4) {
        GridPane backupGrid = new GridPane();
        BorderPane backupPane = new BorderPane();
        VBox backupVBox = new VBox(20);
        HBox titleHolder = new HBox();
        Label status = new Label("");

        backupVBox.setId("backupVBox");

        backupGrid.setAlignment(Pos.CENTER);
        backupGrid.setHgap(10);
        backupGrid.setVgap(10);
        backupGrid.setPadding(new Insets(30,40,0,0));

        Label backupTitle = new Label("Backup via Dropbox");
        backupTitle.setStyle("-fx-font-size: 24;");
        titleHolder.setAlignment(Pos.CENTER);
        titleHolder.getChildren().add(backupTitle);

        if(new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey").exists()){
            Button backup = new Button("Backup To Dropbox");
            Button deleteBackupFile = new Button("Delete Dropbox Authentication File");

            backup.setStyle("-fx-padding: 10 70 10 65");
            backup.setId("dark-btn");
            deleteBackupFile.setId("dark-btn");

            backupVBox.getChildren().addAll(backup, deleteBackupFile);

            backupGrid.add(backupVBox, 0, 0);
            backupGrid.add(statusMsg, 0 , 1);

            backup.setOnAction(event -> {
                statusMsg.setText("");
                try {
                    DropboxConnect.authDropbox();

                    DbxFiles.FileMetadata metadata = null;

                    try {
                        InputStream in = new FileInputStream(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/.EncryptedObj");
                        try {
                            metadata = DropboxConnect.getDropboxConnection().files.uploadBuilder("/passwordDropboxBackup").run(in);
                        } finally {
                            in.close();

                            statusMsg.setText("Uploaded to dropbox successfully.");

                            //Timer to remove the msg after 5 seconds
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(new Runnable() {
                                        public void run() {
                                            removeWarning();
                                        }
                                    });
                                }
                            }, 5000);

                        }
                    }
                    catch (DbxFiles.UploadException ex) {
                        System.out.println("Error uploading to Dropbox: " + ex.getMessage());
                    }

                    System.out.print(metadata.toStringMultiline());

                } catch (IOException | DbxException e) {
                    System.out.println("Something went wrong while trying to backup to dropbox.");
                }

            });

            deleteBackupFile.setOnAction(event -> {
                Path filePath = Paths.get(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey");

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText("Delete Dropbox authentication?  You will have to \n reauthenticate with Dropbox next time.");
                alert.setHeaderText("Warning! You are about to delete your Dropbox authentication!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        Files.deleteIfExists(filePath);
                    } catch (IOException e) {
                        System.out.println("Unable to delete dropbox authentication.");
                    }
                }
            });
        }else{

        }


        backupPane.setCenter(backupGrid);

        backupPane.setTop(titleHolder);

        backupTab4.setContent(backupPane);

        return backupTab4;
    }

    private static void removeWarning() {
        statusMsg.setText("");
    }
}
