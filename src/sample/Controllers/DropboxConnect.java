package sample.Controllers;

import com.dropbox.core.*;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v2.DbxClientV2;
import sample.Views.LoginScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Created by augustus on 1/22/16.
 */
public class DropboxConnect {

    private static DbxClientV2 dropboxConnection;
    private static final String dropboxAccess = "OnH1oNyF_1sAAAAAAAB5LAXncmdSYiGQbjaTTAsZ5EHaskFCBtLEpNrsLXNE7UBi";

    public static void authDropbox()
            throws IOException, DbxException {

        DbxAppInfo appInfo;
        try {
            appInfo = DbxAppInfo.Reader.readFromFile(new File("dropboxAppAuth"));
        }
        catch (JsonReader.FileLoadException ex) {
            System.err.println("Error reading <app-info-file>: " + ex.getMessage());
            System.exit(1); return;
        }


        DbxRequestConfig dbxRequestConfig = new DbxRequestConfig(
                "dropbox/passwordManager-Stus", Locale.getDefault().toString());

        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(dbxRequestConfig, appInfo);

        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first).");
        System.out.println("3. Copy the authorization code.");
        System.out.print("Enter the authorization code here: ");

        String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
        if (code == null) {
            System.exit(1); return;
        }
        code = code.trim();

        DbxAuthFinish authFinish;
        try {
            authFinish = webAuth.finish(code);
        }
        catch (DbxException ex) {
            System.err.println("Error in DbxWebAuth.start: " + ex.getMessage());
            System.exit(1); return;
        }

        System.out.println("Authorization complete.");
        System.out.println("- User ID: " + authFinish.userId);
        System.out.println("- Access Token: " + authFinish.accessToken);

        // Save auth information to output file.
        DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.accessToken, appInfo.host);
        try {
            DbxAuthInfo.Writer.writeToFile(authInfo, new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey"));
            System.out.println("Saved authorization information to \"" + new File(".UserFiles/." + LoginScreen.getLoggedInUser() + "Dir/dropboxAuthKey").toString() + "\".");
        }
        catch (IOException ex) {
            System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
            System.err.println("Dumping to stderr instead:");
            DbxAuthInfo.Writer.writeToStream(authInfo, System.err);
            System.exit(1); return;
        }


        DbxClientV2 connectionV2 = new DbxClientV2(dbxRequestConfig, dropboxAccess);

        setDropboxConnection(connectionV2);
    }

    public static void setDropboxConnection(DbxClientV2 connection){
        dropboxConnection = connection;
    }
    public static DbxClientV2 getDropboxConnection(){
        return dropboxConnection;
    }
}
