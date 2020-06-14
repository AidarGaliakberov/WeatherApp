package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;;
import javafx.scene.image.ImageView;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    private ImageView icon;

    @FXML
    void initialize() {
        assert city != null : "fx:id=\"city\" was not injected: check your FXML file 'sample.fxml'.";
        assert getData != null : "fx:id=\"getData\" was not injected: check your FXML file 'sample.fxml'.";
        assert temp != null : "fx:id=\"temp\" was not injected: check your FXML file 'sample.fxml'.";
        assert temp_feels != null : "fx:id=\"temp_feels\" was not injected: check your FXML file 'sample.fxml'.";
        assert temp_max != null : "fx:id=\"temp_max\" was not injected: check your FXML file 'sample.fxml'.";
        assert temp_min != null : "fx:id=\"temp_min\" was not injected: check your FXML file 'sample.fxml'.";
        assert pressure != null : "fx:id=\"pressure\" was not injected: check your FXML file 'sample.fxml'.";
        assert icon != null : "fx:id=\"icon\" was not injected: check your FXML file 'sample.fxml'.";

        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&APPID=cf414058c89f4b418b0824e3e6e0cfae&units=metric");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp") + " \u00b0" + "C");
                    temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like") + " \u00b0" + "C");
                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max") + " \u00b0" + "C");
                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min") + " \u00b0" + "C");
                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure") + " Гпа");

                    JSONArray a = obj.getJSONArray("weather"); //read weather array at json
                    JSONObject aIcon = (JSONObject) a.get(0); //get icon of weather

                    String weatherIcon = "http://openweathermap.org/img/wn/" + aIcon.get("icon") + "@2x.png";
                    boolean backgroundLoading = true;
                    Image image = new Image(weatherIcon, backgroundLoading);
                    icon.setImage(image);
                }
            }
        });
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try
        {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }

        catch (Exception e) {
            System.out.println("Такой город не был найден!");
        }
        return  content.toString();
    }
}
