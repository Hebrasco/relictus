package utils;

import com.almasb.fxgl.app.FXGL;
import data.Language;

import static preferences.GamePreferences.*;

/**
 * Stores the selected language.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
class Localization {
    Language selectedLanguage = new Language(
            FXGL.getAssetLoader().loadResourceBundle(LANGUAGE_PATH + LANGUAGE_PROPERTIES_FILE_NAME)
    );
}
