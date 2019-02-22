package src.main.java.dad.api;

import org.json.JSONObject;

public class Episode extends JSonItem{

    @JsonData(name = "id")
    public int id;

    @JsonData(name = "name")
    public String name;

    @JsonData(name = "season")
    public int season;

    @JsonData(name = "number")
    public int number;

    @JsonData(name = "airdate")
    public String airdate;

    @JsonData(name = "runtime")
    public int runtime;

    @JsonData(name = "image")
    public src.main.java.dad.api.Image image;

    @JsonData(name = "summary")
    public String summary;

    public Episode(JSONObject ob) {
        super(ob);
    }


}
