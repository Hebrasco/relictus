package data;

import java.util.ResourceBundle;

/**
 * Contains the {@link ResourceBundle} with all texts for the whole game.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class Language {
    public String name;
    public ResourceBundle resourceBundle;

    public Language(String name, ResourceBundle resourceBundle) {
        this.name = name;
        this.resourceBundle = resourceBundle;
    }
}