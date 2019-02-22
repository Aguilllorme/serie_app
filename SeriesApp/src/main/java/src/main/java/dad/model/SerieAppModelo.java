package src.main.java.dad.model;

import src.main.java.dad.api.Serie;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SerieAppModelo {

    private ListProperty<String> listFindSerie;
    private ListProperty<Serie> listSerie;

    public SerieAppModelo() {
        listSerie = new SimpleListProperty<>(this, "listSerie", FXCollections.observableArrayList());
        listFindSerie = new SimpleListProperty<>(this, "listFindSerie", FXCollections.observableArrayList());
    }

    public ObservableList<String> getListFindSerie() {
        return listFindSerie.get();
    }

    public ListProperty<String> listFindSerieProperty() {
        return listFindSerie;
    }

    public void setListFindSerie(ObservableList<String> listFindSerie) {
        this.listFindSerie.set(listFindSerie);
    }

    public ObservableList<Serie> getListSerie() {
        return listSerie.get();
    }

    public ListProperty<Serie> listSerieProperty() {
        return listSerie;
    }

    public void setListSerie(ObservableList<Serie> listSerie) {
        this.listSerie.set(listSerie);
    }

}
