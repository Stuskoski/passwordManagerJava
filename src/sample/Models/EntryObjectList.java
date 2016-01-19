package sample.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by augustus on 1/19/16.
 */
public class EntryObjectList implements Serializable{
    private static List<EntryObjects> ObjectList = new ArrayList<>();

    //Getter
    public static List<EntryObjects> getObjectList(){
        return ObjectList;
    }

    //Setter
    public static void setObjectList(List<EntryObjects> list){
        ObjectList = list;
    }
}
