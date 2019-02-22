package src.main.java.dad.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class ActorModel {

    private IntegerProperty id;
    private StringProperty name;
    private ObjectProperty<LocalDate> bornDate;
    private StringProperty genre;
    private StringProperty caracter;
    private StringProperty contry;
    private ObjectProperty<LocalDate> deathday;
    private StringProperty img;

    public ActorModel() {
        id = new SimpleIntegerProperty(this,"id");
        name = new SimpleStringProperty(this, "name");
        bornDate = new SimpleObjectProperty<>(this, "bornDate");
        genre = new SimpleStringProperty(this,"genre");
        caracter = new SimpleStringProperty(this, "caracter");
        contry = new SimpleStringProperty(this,"contry");
        deathday = new SimpleObjectProperty<>(this, "deathday");
        img = new SimpleStringProperty(this,"img");
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

    public String getContry() {
        return contry.get();
    }

    public StringProperty contryProperty() {
        return contry;
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

    public LocalDate getBornDate() {
        return bornDate.get();
    }

    public ObjectProperty<LocalDate> bornDateProperty() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate.set(bornDate);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getCaracter() {
        return caracter.get();
    }

    public StringProperty caracterProperty() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter.set(caracter);
    }

    public void setContry(String name) {
    }

    public LocalDate getDeathday() {
        return deathday.get();
    }

    public ObjectProperty<LocalDate> deathdayProperty() {
        return deathday;
    }

    public void setDeathday(LocalDate deathday) {
        this.deathday.set(deathday);
    }
}
