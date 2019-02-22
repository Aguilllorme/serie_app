package src.main.java.dad.api;

import org.json.JSONObject;

import java.util.ArrayList;

public class Show extends JSonItem {

    @JsonData(name = "id")
    public int Id;

    @JsonData(name = "name")
    public String name;

    @JsonData(name = "language")
    public String language;

    @JsonData(name = "genres")
    public ArrayList<String> genres;

    @JsonData(name = "status")
    public String status;

    @JsonData(name = "runtime")
    public int runtime;

    @JsonData(name = "premiered")
    public String premiered;

    @JsonData(name = "officialSite")
    public String officialSite;

    @JsonData(name = "schedule")
    public Schedule schedule;

    @JsonData(name = "rating")
    public Rating rating;

    @JsonData(name = "network")
    public Network network;

    @JsonData(name = "image")
    public Image image;

    @JsonData(name = "summary")
    public String summary;

    public Show(JSONObject ob) {
        super(ob);
        if (summary == null){
            summary = "No description";
        }
    }
}
