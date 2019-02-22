package src.main.java.dad.api;

import org.json.JSONObject;

public class Image extends JSonItem {

    @JsonData(name = "medium")
    public String medium;

    @JsonData(name = "original")
    public String original;

    public Image(JSONObject ob) {
        super(ob);
    }
}
