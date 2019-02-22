package src.main.java.dad.api;

import org.json.JSONObject;

public class Serie extends JSonItem {

    @JsonData(name = "score")
    public double score;

    @JsonData(name = "show")
    public Show show;

    public Serie(JSONObject ob) {
        super(ob);
    }
}
