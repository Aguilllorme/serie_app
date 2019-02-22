package src.main.java.dad.api;

import org.json.JSONObject;

public class Rating extends JSonItem{

    @JsonData(name = "average")
    public int average;

    public Rating(JSONObject ob) {
        super(ob);
    }
}
