package src.main.java.dad.api;

import org.json.JSONObject;

public class Network extends JSonItem{

    @JsonData(name= "id")
    public int id;

    @JsonData(name= "name")
    public String name;

    @JsonData(name= "country")
    public Contry country;

    public Network(JSONObject ob) {
        super(ob);
    }
}
