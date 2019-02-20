package utils;

import java.util.ResourceBundle;

public class PropertiesLoader {
    private static PropertiesLoader ourInstance = new PropertiesLoader();
    private static Localization localization = new Localization();

    public static PropertiesLoader getInstance() {
        return ourInstance;
    }

    private PropertiesLoader() {
    }

    public String getResourceProperties(String key) {
        ResourceBundle bundle = localization.selectedLanguage.resourceBundle;

        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "MISSING KEY!";
        }
    }
}
