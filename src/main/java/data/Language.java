package data;

import java.util.ResourceBundle;

/**
 * Contains the {@link ResourceBundle} with all texts for the whole game.
 *
 * @author Daniel Bedrich, Roman Rubashkin
 * @version 1.0
 */
public class Language {
    public ResourceBundle resourceBundle;

    public Language(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}