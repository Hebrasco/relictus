package preferences;

import com.almasb.fxgl.app.ApplicationMode;
import factories.RelictusEntityFactory;
import factories.RelictusSceneFactory;

/**
 * @author Daniel Bedrich
 */
public class GamePreferences {
    // GENERAL
    public static final String GAME_TITLE = "R E L I C T U S";
    public static final String VERSION = "0.1";
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final boolean IS_MENU_ENABLED = true;
    public static final boolean IS_INTRO_ENABLED = false;
    public static final boolean IS_FULL_SCREEN_ALLOWED = false;
    public static final boolean IS_MANUAL_RESIZE_ALLOWED = false;
    public static final RelictusSceneFactory SCENE_FACTORY = new RelictusSceneFactory();
    public static final RelictusEntityFactory ENTITY_FACTORY = new RelictusEntityFactory();
    public static final ApplicationMode APPLICATION_MODE = ApplicationMode.DEVELOPER; // bei release version auf "Release" ändern

    // FILE PATHS
    public static final String ICON_PATH = "icon/";
    public static final String MENU_PATH = "menu/";
    public static final String LANGUAGE_PATH = "languages/";
    public static final String PARTICLES_PATH = "particles/";

    // FILE NAMES
    public static final String CSS_FILE_NAME = "ui_style.css";
    public static final String APP_ICON_FILE_NAME = "relictus.png";
    public static final String TILE_MAP_FILE_NAME = "relictusTileMap.json";
    public static final String POWER_UP_FILE_NAME = "diamond.png";
    public static final String PLAYER_FILE_NAME = "player.png";
    public static final String CURSOR_FILE_NAME = "cursor.png";
    public static final String LANGUAGE_PROPERTIES_FILE_NAME = "relictus.properties";
    public static final String SOUND_MENU_PRESS_FILE_NAME = "menu_click.wav";
    public static final String SOUND_MENU_SELECT_FILE_NAME = "menu_move.wav";
    public static final String SOUND_POWERUP = "powerup.mp3";
    public static final String MENU_FONT_FILE_NAME = "Jura-Medium.ttf";
    public static final String MENU_PARTICLE_FILE_NAME = "dust.png";
    public static final String MENU_BACKGROUND_FILE_NAME = "menu_background.gif";
    public static final String MENU_TITLE_IMAGE = "title.png";
}
