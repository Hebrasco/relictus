package utils;

import java.util.ResourceBundle;

/**
 * @author Daniel Bedrich
 */
public class PropertiesLoader {
    private static Localization localization = new Localization();

    public static String getResourceProperties(String key) {
        ResourceBundle bundle = localization.selectedLanguage.resourceBundle;

        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "MISSING KEY!";
        }
    }
}
