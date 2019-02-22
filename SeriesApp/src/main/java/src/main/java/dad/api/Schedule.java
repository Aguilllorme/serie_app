package src.main.java.dad.api;

import org.json.JSONObject;

import java.util.ArrayList;

public class Schedule extends JSonItem {

    @JsonData(name="time")
    public String time;

    @JsonData(name="days")
    public ArrayList<String> days;

    public Schedule(JSONObject ob) {
        super(ob);
    }
}
