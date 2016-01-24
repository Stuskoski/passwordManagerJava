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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.Models.EntryObjectList;
import sample.Models.UserPasswordFileActions;

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
public class createExportScreen {
    public static void createExport(){
        Stage exportStage = new Stage();
        BorderPane exportRoot = new BorderPane();
        Scene exportScene = new Scene(exportRoot,600, 600);
        HBox hboxContainer = new HBox();
        Label title = new Label("Export Backup");
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30,40,0,0));
        Label choosePath = new Label("Choose Save Location: ");
        grid.add(choosePath, 0, 0);
        Button choose = new Button("Choose Path");
        choose.setId("dark-btn");
        choose.setStyle("-fx-padding: 5;");
        grid.add(choose, 1, 0);
        TextField text = new TextField();
        text.setPromptText("Save Path");
        grid.add(text, 0, 1, 2, 1);
        Label choosePass = new Label("Enter Password For Encryption: ");
        grid.add(choosePass, 0, 2);
        PasswordField pass = new PasswordField();
        pass.setPromptText("Password");
        grid.add(pass, 0, 3, 2, 1);
        Button exportStart = new Button("Export");
        exportStart.setId("dark-btn");
        grid.add(exportStart, 0, 5);
        Label msg = new Label("");
        grid.add(msg, 0, 6);
        exportRoot.setCenter(grid);

        title.setStyle("-fx-font-size: 24;");
        hboxContainer.setAlignment(Pos.CENTER);
        hboxContainer.getChildren().add(title);
        exportRoot.setTop(hboxContainer);

        exportStage.setTitle("Export Backup");

        //Add css sheet
        exportScene.getStylesheets().add("sample/Login.css");

        choose.setOnAction(event -> {
            msg.setText("");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Path");
            File exportClearFile = fileChooser.showSaveDialog(exportStage);
            if (exportClearFile != null) {
                text.setText(exportClearFile.toString());
                //UserPasswordFileActions.exportClear(exportClearFile);
            }

        });
        exportStart.setOnAction(event -> {
            msg.setText("");
            if((pass.getText().length() != 0) && (text.getText().length() != 0)){
                File file1 = new File(text.getText());
                String password=pass.getText();
                //pad the password.
                while(password.length() < 16){
                    password += "0";
                }
                System.out.println(password);
                try {
                    FileOutputStream fout = new FileOutputStream(text.getText());
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(EntryObjectList.getObjectList());

                    byte[] shaKey = password.getBytes("UTF-8");
                    MessageDigest sha = MessageDigest.getInstance("SHA-1");;
                    shaKey = sha.digest(shaKey);
                    shaKey = Arrays.copyOf(shaKey, 16);
                    try {
                        sha = MessageDigest.getInstance("SHA-1");
                        SecretKeySpec secretKeySpec = new SecretKeySpec(shaKey, "AES");
                        Cipher cipher = Cipher.getInstance("AES");
                        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
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

            }else{
                msg.setText("Path and password may not be empty.");
            }
        });

        exportStage.setScene(exportScene);

        //Fill stage with content
        exportStage.show();
    }


}
