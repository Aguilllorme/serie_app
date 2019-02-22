package src.main.java.dad.api;

import org.json.JSONObject;

public class Character extends JSonItem{

    @JsonData(name = "name")
    public String name;

    public Character(JSONObject ob) {
        super(ob);
    }
}
