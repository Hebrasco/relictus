package preferences;

import com.almasb.fxgl.app.ApplicationMode;
import factories.RelictusSceneFactory;

/**
 * @author Daniel Bedrich
 */
public class GamePreferences {
    // GENERAL
    public static final String gameTitle = "R E L I C T U S";
    public static final String version = "0.1";
    public static final int windowWidth = 1280;
    public static final int windowHeight = 720;
    public static final boolean isMenuEnabled = true;
    public static final boolean isIntroEnabled = false;
    public static final boolean isFullScreenAllowed = false;
    public static final boolean isManualResizeAllowed = false;
    public static final RelictusSceneFactory sceneFactory = new RelictusSceneFactory();
    public static final ApplicationMode applicationMode = ApplicationMode.DEVELOPER; // bei release version auf "Release" ändern

    // FILE PATHS
    public static final String iconPath = "icon/";
    public static final String menuPath = "menu/";

    // FILE NAMES
    public static final String cssFileName = "ui_style.css";
    public static final String appIconFileName = "relictus.png";
    public static final String soundMenuPressFileName = "menu_click.wav";
    public static final String soundMenuSelectFileName = "menu_move.wav";
    public static final String menuFont = "Jura-Medium.ttf";
}
