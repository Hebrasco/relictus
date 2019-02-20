package utils;

import com.almasb.fxgl.dsl.FXGL;
import data.Language;

public class Localization {
    private final String langName = "relictus";
    private final String languagesPath = "languages/";
    private final String fileEndingProperties = ".properties";
    Language selectedLanguage = new Language(
            langName,
            FXGL.getAssetLoader().loadResourceBundle(languagesPath + langName + fileEndingProperties)
    );
}
