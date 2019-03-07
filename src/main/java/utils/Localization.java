package utils;

import com.almasb.fxgl.app.FXGL;
import data.Language;

import static preferences.GamePreferences.*;

/**
 * @author Daniel Bedrich
 */
class Localization {
    Language selectedLanguage = new Language(
            FXGL.getAssetLoader().loadResourceBundle(LANGUAGE_PATH + LANGUAGE_PROPERTIES_FILE_NAME)
    );
}
