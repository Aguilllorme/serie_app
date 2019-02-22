package src.main.java.dad.api;

import org.json.JSONObject;

public class Cast extends JSonItem {

    @JsonData(name = "person")
    public Person person;

    @JsonData(name = "character")
    public src.main.java.dad.api.Character character;

    public Cast(JSONObject ob) {
        super(ob);
    }
}
