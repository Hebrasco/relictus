package utils;

import java.util.ResourceBundle;

/**
 * Loads the resource language text properties.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class PropertiesLoader {
    private static final Localization localization = new Localization();

    /**
     * Loads the resource language property.
     * @param key the key for the specific text property.
     * @return the text of the given key.
     */
    public static String getResourceProperties(String key) {
        final ResourceBundle bundle = localization.selectedLanguage.resourceBundle;

        try {
            return bundle.getString(key);
        } catch (Exception e) {
            return "MISSING KEY!";
        }
    }
}
