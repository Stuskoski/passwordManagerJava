package sample;

import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by augustus on 1/14/16.
 */
public class SceneList {
    private static ArrayList<Scene> SceneArray = new ArrayList<>();

    public static ArrayList GetScene(){
        SceneArray.forEach(System.out::println);
        return(SceneArray);
    }

    public static void AddScene(Scene sceneToAdd){
        SceneArray.add(sceneToAdd);
    }


}
