package src.main.java.dad;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import src.main.java.dad.api.Api;
import src.main.java.dad.api.Cast;
import src.main.java.dad.api.Episode;
import src.main.java.dad.api.Serie;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import src.main.java.dad.jasper.SeriesDatasource;
import src.main.java.dad.model.*;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

public class SerieAppViewController  implements Initializable {


    @FXML
    private MenuBar menuTab;

    @FXML
    private MenuItem txtClose;

    @FXML
    private MenuItem txtReport;

    @FXML
    private MenuItem txtNetflix;

    @FXML
    private MenuItem txtPrimeColor;

    @FXML
    private MenuItem txtHelp;

    @FXML
    private VBox busquedaVBox;

    @FXML
    private TextField txtFindSerie;

    @FXML
    private ListView<String> listFindSerie;

    @FXML
    private VBox leftPane;

    @FXML
    private HBox leftHbox;

    @FXML
    private Button idActor;

    @FXML
    private Button idEpis;

    @FXML
    private StackPane stackPane;

    @FXML
    private ProgressIndicator progresIndicator;

    @FXML
    private TabPane seasonTab;

    @FXML
    private Accordion acordionActor;

    @FXML
    private HBox centerHor;

    @FXML
    private ImageView imgSerie;

    @FXML
    private Label txtRating;

    @FXML
    private Label txtYear;

    @FXML
    private Label txtDuration;

    @FXML
    private Label txtStatus;

    @FXML
    private Label txtGenres;

    @FXML
    private Label txtProducer;

    @FXML
    private Label txtContry;

    @FXML
    private Label txtName;

    @FXML
    private Label txtDescription;

    @FXML
    private Separator separator1;

    @FXML
    private Separator separator2;

    @FXML
    private Label txtLink;


    private SerieAppModelo modelo = new SerieAppModelo();
    private SerieModel serieModel = new SerieModel();

    private Api api = new Api();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listFindSerie.itemsProperty().bind(modelo.listFindSerieProperty());
        txtName.textProperty().bind(serieModel.nameProperty());
        txtDescription.textProperty().bind(serieModel.descriptionProperty());
        txtLink.textProperty().bind(serieModel.linkProperty());
        txtRating.textProperty().bind(serieModel.ratingProperty().asString());
        txtDuration.textProperty().bind(serieModel.durationProperty().asString().concat(" min"));
        txtGenres.textProperty().bind(serieModel.categoryProperty());
        txtStatus.textProperty().bind(serieModel.statusProperty());
        imgSerie.imageProperty().bind(serieModel.imageProperty());
        txtProducer.textProperty().bind(serieModel.producerProperty());
        txtContry.textProperty().bind(serieModel.contryProperty());
        txtYear.textProperty().bind((serieModel.airDateProperty()));

        // region tooltips
        idEpis.setTooltip(new Tooltip("Show the episodes"));
        idActor.setTooltip(new Tooltip("Show the casts"));
        txtFindSerie.setTooltip(new Tooltip("Put the serie name inside"));
        listFindSerie.setTooltip(new Tooltip("Choose one"));
        //endregion


        /**
         * Este listener se encarga de escuchar los cambios que se realicen en el textfield cuando el usuario escriba
         * el nombre de las series
         */
        txtFindSerie.textProperty().addListener((o, ov, nv) ->{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final ArrayList<Serie> episodes = api.searchList(nv);
                            Platform.runLater(new Runnable() {
                                @Override public void run() {
                                    if(!serieModel.actorsProperty().isEmpty()){
                                        serieModel.actorsProperty().clear();
                                    }
                                    if(!serieModel.seasonsProperty().isEmpty()){
                                        serieModel.seasonsProperty().clear();
                                    }
                                    if (!modelo.listFindSerieProperty().isEmpty()){
                                        modelo.listFindSerieProperty().clear();
                                    }
                                    if (!modelo.listSerieProperty().isEmpty()){
                                        modelo.listSerieProperty().clear();
                                    }
                                    ArrayList<String> nombres = new ArrayList<>();
                                    modelo.listFindSerieProperty().clear();
                                    for (Serie s : episodes) {
                                        modelo.listFindSerieProperty().add((s.show.name));
                                        modelo.listSerieProperty().add(s);
                                    }
                                }
                            });
                        } catch (UnirestException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            //} else {
              //  modelo.listFindSerieProperty().clear();
            //}
        } );

        listFindSerie.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->{
            setVisible();
            int position = listFindSerie.getSelectionModel().getSelectedIndex();
            if (position == -1) return;
            progresIndicator.setVisible(true);
            //Region se cargan los datos recogidos de la src.main.java.dad.api el las variables de la serie
            serieModel.setId(modelo.listSerieProperty().get(position).show.Id);
            serieModel.setName(modelo.listSerieProperty().get(position).show.name);
            serieModel.setCategory(formatGenres(modelo.getListSerie().get(position).show.genres));
            String[] date = (modelo.getListSerie().get(position).show.premiered).split("-");
            serieModel.setAirDate(date[0]);
            serieModel.setLink(modelo.getListSerie().get(position).show.officialSite);
            if (modelo.getListSerie().get(position).show.image != null) {
                serieModel.setImgSerie(modelo.getListSerie().get(position).show.image.original);
                // para imágenes apaisadas tenemos que recortar para que las proporciones sean las que queremos
                Image image = new Image(URI.create(modelo.getListSerie().get(position).show.image.original).toString());
                double scale = 2/3.0;
                if(image.getWidth()>image.getHeight()){
                    int alto = (int)image.getHeight();
                    int ancho = (int)(alto*scale);
                    PixelReader reader = image.getPixelReader();
                    WritableImage wImage = new WritableImage(reader, (int)(image.getWidth()-ancho)/2, 0, ancho, alto);
                    serieModel.setImage(wImage);
                } else {
                    serieModel.setImage(image);
                }

            } else {
                // mostrar imagen genérica
            }
            serieModel.setRating(modelo.getListSerie().get(position).show.rating.average);
            serieModel.setStatus(modelo.getListSerie().get(position).show.status);
            serieModel.setDuration(modelo.getListSerie().get(position).show.runtime);
            if (modelo.getListSerie().get(position).show.network != null) {
                serieModel.setContry(modelo.getListSerie().get(position).show.network.country.name);
                serieModel.setProducer(modelo.getListSerie().get(position).show.network.name);
            } else {
                serieModel.setContry("");
                serieModel.setProducer("");
            }
            if(modelo.getListSerie().get(position).show.summary != null) {
                serieModel.setDescription(modelo.getListSerie().get(position).show.summary.replaceAll("<[^>]*>", ""));
            } else {
                serieModel.setDescription("No description");
            }

            /**
             * Este hilo carga los episodios de la serie seleccionada
             */
            new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final ArrayList<Episode> episodes = api.findEpisodes(serieModel.getId());
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        seasonTab.getTabs().clear();
                                        serieModel.seasonsProperty().clear();
                                        for (Episode e : episodes) {
                                            if(serieModel.seasonsProperty().size()<e.season){
                                                serieModel.seasonsProperty().add(new SeasonModel());
                                            }
                                            if(serieModel.seasonsProperty().size() > e.season-1) {
                                                serieModel.seasonsProperty().get(e.season - 1).getListEpisodes().add(convertEpisode(e)); //fixme
                                            }
                                        }
                                        showEpisodes();
                                        progresIndicator.setVisible(false);
                                    }
                                });
                            } catch (UnirestException e) {
                                e.printStackTrace();
                            }
                        }
            }).start();

            /**
             * Carga los actores en la variable
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final ArrayList<Cast> casts = api.findCast(serieModel.getId());
                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                acordionActor.getPanes().clear();
                                serieModel.actorsProperty().clear();
                                for (Cast c : casts) {
                                    ActorModel actorModel = convertActor(c);
                                    serieModel.actorsProperty().add(actorModel);
                                }
                                showCasts();
                                progresIndicator.setVisible(false);
                            }
                        });
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }

    /**
     * Hace visible los componentes de la interfase de usuario
     */
    private void setVisible() {
        idEpis.setVisible(true);
        idActor.setVisible(true);
        centerHor.setVisible(true);
        separator1.setVisible(true);
        separator2.setVisible(true);
    }

    /**
     * Esta función coge los datos de los Actores y los carga las variables del model que estan relacionadas
     * con la interfase de usuario
     */
    private void showCasts() {
        for (int i= 0 ; i < serieModel.getActors().size() ; i++ ){
            acordionActor.setPrefWidth(310);
            VBox vBox = setActorData(serieModel.actorsProperty().get(i));
            TitledPane titledPane = new TitledPane(String.format("%s", serieModel.getActors().get(i).getName()),vBox);
            // cuando se despliegue el panel cargamos la imagen con la URL que había en el vBox.UserData
            titledPane.setOnMouseClicked(event -> {
                VBox vbox = (VBox)titledPane.getContent();
                ImageView imgv = (ImageView)vBox.getChildren().get(0);
                if (vBox.getUserData() != null) {
                    imgv.setImage(new Image(URI.create((String) vbox.getUserData()).toString()));
                    vbox.setUserData(null);
                }
            });
            acordionActor.getPanes().add(titledPane);
        }
    }

    /**
     * Esta función coge los datos de los episodio y los carga de la interface de usuario
     */
    private void showEpisodes() {
        for (int i= 0 ; i < serieModel.seasonsProperty().size() ; i++ ){
            Accordion accordion  = new Accordion();
            accordion.setPrefWidth(310);
            Tab tab = new Tab(String.format("Season %s", i+1),new ScrollPane(accordion));
            tab.setId("tab"+i);
            seasonTab.getTabs().add(tab);
            for(int j = 0; j < serieModel.seasonsProperty().get(i).getListEpisodes().size(); j++) {
                VBox vBox = setEpisodeData(serieModel.getSeasons().get(i).getListEpisodes().get(j));

                TitledPane titledPane = new TitledPane(serieModel.getSeasons().get(i).getListEpisodes().get(j).getName(),vBox);
                titledPane.setPrefWidth(300);
                // cuando se despliegue el panel cargamos la imagen con la URL que había en el vBox.UserData
                titledPane.setOnMouseClicked(event -> {
                    VBox vbox = (VBox)titledPane.getContent();
                    vbox.setPrefWidth(295);
                    ImageView imgv = (ImageView)vBox.getChildren().get(0);
                    if (vBox.getUserData() != null) {
                        imgv.setImage(new Image(URI.create((String) vbox.getUserData()).toString()));
                        vbox.setUserData(null);
                    }
                });
                accordion.getPanes().add(titledPane);
            }
        }
    }

    /**
     *  Esta funcion trasnforma un objeto Cast que que se extrae de la APi
     *  y devuelve un castModel el cual puede ser vindeado con la interface
     * @param c
     * @return
     */
    private ActorModel convertActor(Cast c) {
        ActorModel actorModel = new ActorModel();
        actorModel.setName(c.person.name);
        if (c.person.birthday != null) {
            actorModel.setBornDate(LocalDate.parse(c.person.birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        actorModel.setCaracter(c.character.name);
        actorModel.setGenre(c.person.gender);
        if(c.person.country != null && c.person.country.name != null) {
            actorModel.setContry(c.person.country.name);
        }
        if (c.person.deathday != null) {
            actorModel.setDeathday(LocalDate.parse(c.person.deathday, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        if (c.person.image != null && c.person.image.medium != null) {
            actorModel.setImg(c.person.image.medium);
        } else {
            actorModel.setImg(serieModel.getDefaultUser());
        }
        actorModel.setId(c.person.id);
        return actorModel;
    }

    /**
     * Esta funcion trasnforma un objeto Episode que que se extrae de la APi
     * y devuelve un EpisodeModel el cual puede ser vindeado con la interface
     * @return EpisodeModel
     */
    private EpisodeModel convertEpisode(Episode e) {
        EpisodeModel episodeModel = new EpisodeModel();
        episodeModel.setName(e.name);
        episodeModel.setId(e.id);
        episodeModel.setAirdate(e.airdate);
        episodeModel.setNumber(e.number);

        if (e.image != null && e.image.medium != null) {
            episodeModel.setImg(e.image.medium);
        } else {
            episodeModel.setImg(serieModel.getDefaultUser());
        }
        episodeModel.setSinopsis(e.summary);
        episodeModel.setRuntime(e.runtime);
        return episodeModel;
    }


    @FXML
    void onClickActor(ActionEvent event) {
        acordionActor.setVisible(true);
        seasonTab.setVisible(false);
    }

    @FXML
    void onActionTxtClose (ActionEvent event) {
        System.exit(1);
    }


    /**
     * Llama a la funion de JaspertReport y genera un informe con el formato determinado con aterioridad
     * @param event
     */
    @FXML
    void onActionTxtReport (ActionEvent event) {

       SeriesDatasource datasource = new SeriesDatasource(serieModel);
       JasperReport reporte = null;
        try {
            InputStream inputStrm = getClass().getClassLoader().getResourceAsStream("Series_report.jasper");
            reporte = (JasperReport) JRLoader.loadObject(inputStrm);
            inputStrm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte,null, datasource);
        } catch (JRException e) {
            e.printStackTrace();
        }
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,
                new java.io.File("reporte2PDF.pdf"));
        try {
            exporter.exportReport();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Se a creado el PDF");
            alert.setContentText("Se a creado correctamente el PDF");
            alert.show();
            Runtime.getRuntime().exec("cmd /C start \"\" \"reporte2PDF.pdf\"");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void onActionTxtCassic (ActionEvent event) {
        ModelSerieAppView.getStage().getScene().getStylesheets().clear();
        ModelSerieAppView.getStage().getScene().getStylesheets().add("styloCss.css");
    }

    @FXML
    void onActionTxtNetflix (ActionEvent event) {
        ModelSerieAppView.getStage().getScene().getStylesheets().clear();
        ModelSerieAppView.getStage().getScene().getStylesheets().add("styleNetflix.css");
    }
    @FXML
    void onActionTxtPrimeColor (ActionEvent event) {
        ModelSerieAppView.getStage().getScene().getStylesheets().clear();
        ModelSerieAppView.getStage().getScene().getStylesheets().add("stylePrimeColor.css");
    }

    @FXML
    void onActionAbout (ActionEvent event) {
        dialogAbout();
    }

    /**
     * muestra el Alert dialogo informativo con la informacion de la aplicacion
     */
    private void dialogAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"About",ButtonType.OK);
        alert.setTitle("About");
        alert.setHeaderText("¿Qué es SearchSeriesApp?");
        alert.setContentText("Esta aplicación se creó para buscar series\n" +
                "en todo el mundo a través de la información que nos\n" +
                "suministra el API “api.tvmaze.com”, en esta aplicación\n" +
                "podemos ver un resumen de la serie, la fecha de la\n" +
                "premier, país que se creó, si ha finalizado.\n" +
                "También podemos crear información sobre las temporadas\n" +
                "que tiene los episodios, y detalles sobre el mismo.\n" +
                "Y cuales actores han participado en la creación de los programas." +
                "\n\nCreado por Angel Guillorme");
        alert.show();
    }


    @FXML
    void onClickEpisodios(ActionEvent event) {
        acordionActor.setVisible(false);
        seasonTab.setVisible(true);
    }

    private String formatGenres(ArrayList<String> genres) {
        StringBuilder listGenres =new StringBuilder();
        for(int i = 0 ; i < genres.size(); i++){
            listGenres.append(genres.get(i));
            listGenres.append("\n");

        }
        return listGenres.toString();
    }

    /**
     * Carga el contenido de la serie en un VBox que servira de contenido para los los TitledPane que estan en el
     * contenido en el Accordion
     * @param episode
     * @return
     */
    private VBox setEpisodeData(EpisodeModel episode){
        VBox vBox;
        Label sumary = new Label();
        sumary.setWrapText(true);
        sumary.setTextAlignment(TextAlignment.JUSTIFY);
        sumary.setPadding(new Insets(10,0,10,0));
        Label airdate = new Label(String.format("Premiered %s", episode.getAirdate()));
        Label date = new Label(String.format("Duration %d minutes", episode.getRuntime()));

        airdate.setTextAlignment(TextAlignment.LEFT);
        date.setTextAlignment(TextAlignment.LEFT);

        if (episode.getSinopsis() != null && !episode.getSinopsis().isEmpty()) {
            sumary.setText(episode.getSinopsis().replaceAll("<[^>]*>", ""));
            sumary.setWrapText(true);
        } else {
            sumary.setVisible(false);
        }

        HBox hBox = new HBox(sumary);
        hBox.setPrefWidth(200);
        // creamos un imageview pero no le metemos nada. Dejaos la URL en el userdata del vbox para cargarla
        // cuando se muestre el contenido
        ImageView img = new ImageView();
        Separator separator = new Separator();
        vBox = new VBox();
        vBox.getChildren().add(img);
        if(episode.getSinopsis() != null){
            vBox.getChildren().add(hBox);
            vBox.getChildren().add(separator);
        }
        vBox.getChildren().addAll(airdate, date);

        if(episode.getImg()!= null) {
            vBox.setUserData(episode.getImg());
        }

        vBox.setPadding(new Insets(10,20,10,10));
        return vBox;
    }

    /**
     * Esta funcion coloca la informacion del actor dentro vbox que luego se cargarra
     * dentro de loo titledpane del acordeon
     * @param actor
     * @return
     */
    private VBox setActorData(ActorModel actor){
        VBox vBox;
        Label chacter = new Label(String.format("like  %s", actor.getCaracter()));
        chacter.setId("charactar");
        chacter.setPadding(new Insets(5,0,5,0));
        Label contry = new Label(actor.getContry());
        Label bornDate =  new Label(actor.getBornDate() == null ? "" : String.format("Born  %s", actor.getBornDate().toString()));
        Label deathDate = new Label(actor.getDeathday() == null ? "" : String.format("Death  %s", actor.getDeathday().toString()));
        Label genre = new Label(actor.getGenre());
        Label age = new Label(formatAge(actor.getBornDate()));
        ImageView img = new ImageView();
        Separator separator = new Separator();

        vBox = new VBox();
        vBox.getChildren().add(img);

        if(actor.getCaracter() != null){
            vBox.getChildren().add(chacter);
        }

        vBox.getChildren().add(separator);

        if(actor.getBornDate() != null){
            vBox.getChildren().add(bornDate);
        }
        if(actor.getDeathday() != null){
            vBox.getChildren().add(deathDate);
        }

        vBox.getChildren().add(age);

        if(actor.getImg()!=null) {
            vBox.setUserData(actor.getImg());
        }
        vBox.setPadding(new Insets(10,20,10,10));
        return vBox;
    }

    private String formatAge(LocalDate bornDate) {
        if (bornDate == null) return  " - ";
        LocalDate now = LocalDate.now();
        int years = (int)Math.floor(Duration.between(bornDate.atStartOfDay(), now.atStartOfDay()).toDays() / 365.25);
        return String.format("%d years old", years);
    }
}
