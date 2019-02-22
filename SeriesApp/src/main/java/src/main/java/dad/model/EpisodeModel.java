package src.main.java.dad.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EpisodeModel {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty season;
    private IntegerProperty number;
    private StringProperty airdate;
    private IntegerProperty runtime;
    private StringProperty img;
    private StringProperty sinopsis;


    public EpisodeModel() {
        id = new SimpleIntegerProperty(this,"id");
        name = new SimpleStringProperty(this,"name");
        season = new SimpleStringProperty(this,"season");
        number = new SimpleIntegerProperty(this,"airdate");
        airdate = new SimpleStringProperty(this,"airdate");
        runtime = new SimpleIntegerProperty(this,"runtime");
        img = new SimpleStringProperty(this,"img");
        sinopsis = new SimpleStringProperty(this, "sinopsis");
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSeason() {
        return season.get();
    }

    public StringProperty seasonProperty() {
        return season;
    }

    public void setSeason(String season) {
        this.season.set(season);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getAirdate() {
        return airdate.get();
    }

    public StringProperty airdateProperty() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate.set(airdate);
    }

    public int getRuntime() {
        return runtime.get();
    }

    public IntegerProperty runtimeProperty() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime.set(runtime);
    }

    public String getImg() {
        return img.get();
    }

    public StringProperty imgProperty() {
        return img;
    }

    public void setImg(String img) {
        this.img.set(img);
    }

    public String getSinopsis() {
        return sinopsis.get();
    }

    public StringProperty sinopsisProperty() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis.set(sinopsis);
    }
}
