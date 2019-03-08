package game;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.settings.GameSettings;
import game.player.PlayerControl;
import utils.CustomCursor;

import static preferences.GamePreferences.*;

/**
 * Handles the complete game activity.
 *
 * @author Daniel Bedrich, Kevin Ortmeier
 * @version 1.0
 */
public class Relictus extends GameApplication {
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WINDOW_WIDTH);
        settings.setHeight(WINDOW_HEIGHT);
        settings.setTitle(GAME_TITLE);
        settings.setVersion( VERSION);
        settings.setMenuEnabled(IS_MENU_ENABLED);
        settings.setIntroEnabled(IS_INTRO_ENABLED);
        settings.setFullScreenAllowed(IS_FULL_SCREEN_ALLOWED);
        settings.setManualResizeEnabled(IS_MANUAL_RESIZE_ALLOWED);
        settings.setSceneFactory(SCENE_FACTORY);
        settings.setCSS(CSS_FILE_NAME);
        settings.setApplicationMode(APPLICATION_MODE);
        settings.setSoundMenuPress(MENU_PATH + SOUND_MENU_PRESS_FILE_NAME);
        settings.setSoundMenuSelect(MENU_PATH + SOUND_MENU_SELECT_FILE_NAME);
        settings.setAppIcon(ICON_PATH + APP_ICON_FILE_NAME);
        settings.setFontUI(MENU_FONT_FILE_NAME);
        settings.setFontGame(MENU_FONT_FILE_NAME);
        settings.setFontText(MENU_FONT_FILE_NAME);
        settings.setFontMono(MENU_FONT_FILE_NAME);
    }

    @Override
    protected void initGame() {
        setCustomCursor();

        getGameWorld().addEntityFactory(ENTITY_FACTORY);
        getGameWorld().setLevelFromMap(TILE_MAP_FILE_NAME);
        // TODO: Nachschauen ob man mit FXGL mehrere JSON laden und einfügen kann
        // Sollte es nicht funktionieren, dann muss die JSON manipuliert werden, damit wie Welt größer wird.
        // Das ganze muss dann Prozedural geschehen.
        getGameScene().addGameView(new ParallaxBackgroundView(Arrays.asList(
            new ParallaxTexture(getAssetLoader().loadTexture("background.png", getWidth(), getHeight()), 0.5)
        ), Orientation.HORIZONTAL), RenderLayer.BACKGROUND);


        // TODO: getGameWorld().setLevelFromMap("map_spawn.json");
        //getGameWorld().setLevelFromMap("map_1.json");

        //getAudioPlayer().playSound("start.mp3");
        player = getGameWorld().spawn("player", 100, 125);

        //getAudioPlayer().playSound("start.mp3");

        getGameScene().getViewport().setBounds(-1500, 0, 1500, 720);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2.0, getHeight() / 2.0);

        initializeInput();
    }

    @Override
    protected void initPhysics() {
    }

    @Override
    protected void onUpdate(double tpf) {
    }

    /**
     * Initializes input for the player entity.
     */
    private void initializeInput() {
        player.getComponent(PlayerControl.class).createInput(getInput());
    }

    /**
     * Sets the custom relictus cursor in the game scene.
     */
    private void setCustomCursor() {
        FXGL.getGameScene().setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
    }
}
