package sample.Views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Models.EntryObjectList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by augustus on 1/24/16.
 */
public class createImportScreen {
    public static void createImport() {
        Stage importStage = new Stage();
        BorderPane importRoot = new BorderPane();
        Scene importScene = new Scene(importRoot, 600, 600);
        HBox hboxContainer = new HBox();
        Label title = new Label("Import Backup");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30, 40, 0, 0));
        Label choosePath = new Label("Choose File Location: ");
        grid.add(choosePath, 0, 0);
        Button choose = new Button("Choose File");
        choose.setId("dark-btn");
        choose.setStyle("-fx-padding: 5;");
        grid.add(choose, 1, 0);
        TextField text = new TextField();
        text.setPromptText("File Path");
        grid.add(text, 0, 1, 2, 1);
        Label choosePass = new Label("Enter Password For Decryption: ");
        grid.add(choosePass, 0, 2);
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        grid.add(pass, 0, 3, 2, 1);
        Button importStart = new Button("Import");
        importStart.setId("dark-btn");
        grid.add(importStart, 0, 5);
        Label msg = new Label("");
        grid.add(msg, 0, 6);
        importRoot.setCenter(grid);

        title.setStyle("-fx-font-size: 24;");
        hboxContainer.setAlignment(Pos.CENTER);
        hboxContainer.getChildren().add(title);
        importRoot.setTop(hboxContainer);

        importStage.setTitle("Import Backup");

        //Add css sheet
        importScene.getStylesheets().add("sample/Login.css");

        choose.setOnAction(event -> {
            msg.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Path");
            File exportClearFile = fileChooser.showOpenDialog(importStage);
            if (exportClearFile != null) {
                text.setText(exportClearFile.toString());
                //UserPasswordFileActions.exportClear(exportClearFile);
            }

        });

        importStart.setOnAction(event -> {
            msg.setText("");
            if ((pass.getText().length() != 0) && (text.getText().length() != 0)) {
                File file1 = new File(text.getText());
                File file2 = new File("tempFile");
                String password = pass.getText();
                //pad the password.
                while (password.length() < 16) {
                    password += "0";
                }
                try {
                    byte[] shaKey = password.getBytes("UTF-8");
                    MessageDigest sha = MessageDigest.getInstance("SHA-1");
                    shaKey = sha.digest(shaKey);
                    shaKey = Arrays.copyOf(shaKey, 16);
                    try {
                        sha = MessageDigest.getInstance("SHA-1");
                        SecretKeySpec secretKeySpec = new SecretKeySpec(shaKey, "AES");
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
                        FileInputStream inputStream = new FileInputStream(file1);
                        byte[] inputBytes = new byte[(int) file1.length()];
                        inputStream.read(inputBytes);

                        byte[] outputBytes = cipher.doFinal(inputBytes);

                        FileOutputStream outputStream = new FileOutputStream(file1);
                        outputStream.write(outputBytes);

                        inputStream.close();
                        outputStream.close();

                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                        System.out.println(e.getMessage());
                    }


                    //UserPasswordFileActions.encrypt(file1, file1);
                    msg.setText("Export completed succesfully.");
                } catch (IOException | NoSuchAlgorithmException e) {
                    msg.setText("Export completed with errors.");
                }

            } else {
                msg.setText("Path and password may not be empty.");
            }

            importStage.setScene(importScene);

            //Fill stage with content
            importStage.show();
        });
    }
}
