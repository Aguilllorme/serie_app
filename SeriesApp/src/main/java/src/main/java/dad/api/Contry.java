package src.main.java.dad.api;

import org.json.JSONObject;

public class Contry extends JSonItem {

    @JsonData(name = "name")
    public String name;


    public Contry(JSONObject ob) {
        super(ob);
    }
}
