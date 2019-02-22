package src.main.java.dad.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SeasonModel {

    private IntegerProperty numSeason;
    private ListProperty<EpisodeModel> listEpisodes;

    public SeasonModel() {
        numSeason = new SimpleIntegerProperty(this,"numSeason");
        listEpisodes = new SimpleListProperty<>(this, "episodes", FXCollections.observableArrayList());
    }

    public int getNumSeason() {
        return numSeason.get();
    }

    public IntegerProperty numSeasonProperty() {
        return numSeason;
    }

    public void setNumSeason(int numSeason) {
        this.numSeason.set(numSeason);
    }

    public ObservableList<EpisodeModel> getListEpisodes() {
        return listEpisodes.get();
    }

    public ListProperty<EpisodeModel> listEpisodesProperty() {
        return listEpisodes;
    }

    public void setListEpisodes(ObservableList<EpisodeModel> listEpisodes) {
        this.listEpisodes.set(listEpisodes);
    }
}

