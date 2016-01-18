package sample.Models;

import java.io.Serializable;

/**
 * Created by augustus on 1/18/16.
 * Password entry objects and all their attributes
 */
public class EntryObjects implements Serializable{
    private String password;
    private String description;
    private String name;

    //getters
    public String getPassword(){ return password; }
    public String getDescription(){ return description; }
    public String getName(){ return name; }

    //setters.  Use "this" when you want to use same name but need different variables.
    public void setPassword(String password) { this.password = password; }
    public void setDescription(String description) { this.description = description; }
    public void setName(String name) { this.name = name; }
}
