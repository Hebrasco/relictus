package controllers;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Daniel Bedrich
 */
public class JsonController {
    public JSONObject json;
    private String textsJsonRelativePath = "/assets/json/texts.json";
    private URL textsJsonURL = getClass().getResource(textsJsonRelativePath);
    private static JsonController jsonController;

    private JsonController() {

    }

    public static JsonController getInstance( ) {
        if (jsonController == null) {
            jsonController = new JsonController( );
        }
        return jsonController;
    }

    // Call before starting Launcher
    public void loadTextsJson() {
        String jsonData = "";
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(String.valueOf(textsJsonURL.toURI().getPath())));
            while ((line = br.readLine()) != null) {
                jsonData += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        json = new JSONObject(jsonData);
    }
}
