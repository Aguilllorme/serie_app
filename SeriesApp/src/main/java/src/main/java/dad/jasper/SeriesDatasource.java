package src.main.java.dad.jasper;

import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import src.main.java.dad.model.ActorModel;
import src.main.java.dad.model.EpisodeModel;
import src.main.java.dad.model.SeasonModel;
import src.main.java.dad.model.SerieModel;

public class SeriesDatasource implements JRDataSource {

    private SerieModel serieModel;

    private boolean isFirst = true;

    public SeriesDatasource(SerieModel serieModel) {
        this.serieModel = serieModel;
    }

    @Override
    public boolean next() throws JRException {
        if (isFirst){
            isFirst = false;
            return  true;
        } else {
            return false;
        }
    }


    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        {
            Object valor = null;
            if ("id".equals(jrField.getName())) {
                valor = serieModel.getId();
            } else if ("name".equals(jrField.getName())) {
                valor = serieModel.getName();
            } else if ("description".equals(jrField.getName())) {
                valor = serieModel.getDescription();
            } else if ("duration".equals(jrField.getName())) {
                valor = serieModel.getDuration();
            } else if ("status".equals(jrField.getName())) {
                valor = serieModel.getStatus();
            } else if ("contry".equals(jrField.getName())) {
                valor = serieModel.getContry();
            } else if ("rating".equals(jrField.getName())) {
                valor = serieModel.getRating();
            } else if ("producer".equals(jrField.getName())) {
                valor = serieModel.getProducer();
            } else if ("link".equals(jrField.getName())) {
                valor = serieModel.getLink();
            } else if ("airDate".equals(jrField.getName())) {
                valor = serieModel.getAirDate();
            } else if ("episodes".equals(jrField.getName())) {
                valor = formatEpisode(serieModel.getSeasons());
            } else if ("cast".equals(jrField.getName())) {
                valor = formatCast(serieModel.getActors());
            }

            return valor;
        }
    }


    /**
     * Esta funcion coge una array de Actores y me regresa un string con el nombre del actor
     * @param casts
     * @return
     */
    private String formatCast(ObservableList<ActorModel> casts) {
        StringBuilder cast = new StringBuilder();
        ActorModel a;
        for(int i = 0; i < casts.size(); i++){
            a = casts.get(i);
            cast.append(String.format("%s   like   %s,  Born   %s.\n", a.getName(),a.getCaracter(),a.getBornDate()));
        }
        return cast.toString();
    }

    /**
     * Esta funcion coge una array de episodio y me regresa un string  con el titulo de los
     * episodios ordenados por temporadas
     * @param seasons
     * @return
     */
    private String formatEpisode(ObservableList<SeasonModel> seasons) {
        StringBuilder episodes = new StringBuilder();
        SeasonModel s;
        EpisodeModel e;
        for(int i = 0; i < seasons.size(); i++){
            s = seasons.get(i);
            episodes.append(String.format("Season  %d\n", i+1));
            for (int j = 0; j < seasons.get(i).getListEpisodes().size(); j++){
                e = s.getListEpisodes().get(j);
                episodes.append(String.format(" Cap  %s:  %s\n", e.getNumber(), e.getName()));
            }
        }
        return episodes.toString();
    }


}
