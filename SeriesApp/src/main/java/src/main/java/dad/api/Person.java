package src.main.java.dad.api;

import org.json.JSONObject;

public class Person extends JSonItem {

    public int id;

    @JsonData(name = "country")
    public Contry country;

    @JsonData(name = "name")
    public String name;

    @JsonData(name = "birthday")
	public String birthday;

    @JsonData(name = "deathday")
    public String deathday;

    @JsonData(name = "gender")
    public String gender;

    @JsonData(name = "image")
    public Image image;

    public Person(JSONObject ob) {
        super(ob);
    }
}
