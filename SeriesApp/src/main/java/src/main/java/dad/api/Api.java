package src.main.java.dad.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class Api {

    public Api(){
        // utilizamos el mapper de Jackson dentro del Unirest
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

            // convierte un objeto en json
            public String writeValue(java.lang.Object value) {
                try {
                    return mapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            // convierte json en objeto
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return mapper.readValue(value, valueType);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        });
    }

    public static ArrayList<Serie> searchList(String name) throws UnirestException {
        HttpResponse<String> response =
                Unirest
                        .get("http://api.tvmaze.com/search/shows?q={name}")
                        .routeParam("name", name)
                        .asString();
        JSONArray arr = new JSONArray(response.getBody());

        ArrayList<Serie> series = new ArrayList<>();
        for (int i=0; i<arr.length(); i++){
            Serie s = new Serie(arr.getJSONObject(i));
            series.add(s);
        }
        return series;
    }


    public ArrayList<Episode> findEpisodes(int idSerie) throws UnirestException {
        Serie newSerie;
        HttpResponse<String> response =
                Unirest
                        .get("http://api.tvmaze.com/shows/{idSerie}/episodes")
                        .routeParam("idSerie", String.format("%d", idSerie))
                        .asString();
        JSONArray arr = new JSONArray(response.getBody());
        ArrayList<Episode> episodes = new ArrayList<>();
        for (int i=0; i<arr.length(); i++){
            episodes.add(new Episode(arr.getJSONObject(i)));
        }
        return episodes;
    }

    public ArrayList<Cast> findCast(int idSerie) throws UnirestException {
        Serie newSerie;
        HttpResponse<String> response =
                Unirest
                        .get("http://api.tvmaze.com/shows/{idSerie}/cast")
                        .routeParam("idSerie", String.format("%d", idSerie))
                        .asString();
        JSONArray arr = new JSONArray(response.getBody());
        ArrayList<Cast> casts = new ArrayList<>();
        for (int i=0; i<arr.length(); i++){
            casts.add(new Cast(arr.getJSONObject(i)));
        }
        return casts;
    }
}
