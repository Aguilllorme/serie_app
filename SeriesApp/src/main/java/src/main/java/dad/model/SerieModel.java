package src.main.java.dad.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class SerieModel {

    private static final String DEFAULT_USER = "https://img2.freepng.es/20180412/hwe/kisspng-question-mark-font-awesome-computer-icons-questions-5acfee46163c21.0359910715235763900911.jpg";
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty premiered;
    private StringProperty description;
    private IntegerProperty duration;
    private StringProperty status;
    private StringProperty contry;
    private DoubleProperty rating;
    private StringProperty imgSerie;
    private StringProperty producer;
    private StringProperty link;
    private StringProperty category;
    private ObjectProperty<Image> image;
    private StringProperty airDate;

    private ListProperty<SeasonModel> seasons;
    private ListProperty<ActorModel> actors;

    public SerieModel() {
        id = new SimpleIntegerProperty(this,"id");
        name = new SimpleStringProperty(this,"name");
        premiered = new SimpleStringProperty(this, "premiered");
        description = new SimpleStringProperty(this,"description");
        duration = new SimpleIntegerProperty(this,"duration");
        status = new SimpleStringProperty(this,"categoria");
        contry = new SimpleStringProperty(this,"contry");
        rating = new SimpleDoubleProperty(this, "rating");
        actors = new SimpleListProperty<>(this,"actors", FXCollections.observableArrayList());
        imgSerie = new SimpleStringProperty(this, "imgSerie");
        producer = new SimpleStringProperty(this, "producer");
        link = new SimpleStringProperty(this, "link");
        image = new SimpleObjectProperty<Image>(this, "image");
        seasons = new SimpleListProperty<>(this,"seasons", FXCollections.observableArrayList());
        category = new SimpleStringProperty(this, "category");
        airDate = new SimpleStringProperty(this, "airDate");
    }

    public static String getDefaultUser() {
        return DEFAULT_USER;
    }

    public String getAirDate() {
        return airDate.get();
    }

    public StringProperty airDateProperty() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate.set(airDate);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public String getPremiered() {
        return premiered.get();
    }

    public StringProperty premieredProperty() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered.set(premiered);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getContry() {
        return contry.get();
    }

    public StringProperty contryProperty() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry.set(contry);
    }

    public double getRating() {
        return rating.get();
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating.set(rating);
    }

    public String getImgSerie() {
        return imgSerie.get();
    }

    public StringProperty imgSerieProperty() {
        return imgSerie;
    }

    public void setImgSerie(String imgSerie) {
        this.imgSerie.set(imgSerie);
    }

    public String getProducer() {
        return producer.get();
    }

    public StringProperty producerProperty() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer.set(producer);
    }

    public String getLink() {
        return link.get();
    }

    public StringProperty linkProperty() {
        return link;
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public ObservableList<SeasonModel> getSeasons() {
        return seasons.get();
    }

    public ListProperty<SeasonModel> seasonsProperty() {
        return seasons;
    }

    public void setSeasons(ObservableList<SeasonModel> seasons) {
        this.seasons.set(seasons);
    }

    public ObservableList<ActorModel> getActors() {
        return actors.get();
    }

    public ListProperty<ActorModel> actorsProperty() {
        return actors;
    }

    public void setActors(ObservableList<ActorModel> actors) {
        this.actors.set(actors);
    }

}
