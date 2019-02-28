package game;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.settings.GameSettings;
import factories.EntityTypes;
import factories.RelictusEntityFactory;
import factories.RelictusSceneFactory;
import game.player.PlayerControl;
import javafx.stage.StageStyle;
import utils.CustomCursor;

/**
 * @author Daniel Bedrich, Kevin Ortmeier
 */
public class Relictus extends GameApplication {
    private Entity player;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        //PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        final int windowWidth = 1280;
        final int windowHeight = 720;
        final String cssFileName = "ui_style.css";
        final String appIcon = "icon/relictus.png";
        final String soundPath = "menu/";
        final String customFont = "Jura-Medium.ttf";
        final String soundMenuPressFileName = soundPath + "menu_click.wav";
        final String soundMenuSelectFileName = soundPath + "menu_move.wav";
        //final String title = propertiesLoader.getResourceProperties("app.title"); // TODO: fix lateinit exception
        //final String version = propertiesLoader.getResourceProperties("app.version"); // TODO: fix lateinit exception
        final String title = "R E L I C T U S"; // Replace with above
        final String version = "0.1"; // Replace with above

        settings.setWidth(windowWidth);
        settings.setHeight(windowHeight);
        settings.setTitle(title);
        settings.setVersion(version);
        settings.setMenuEnabled(true);
        settings.setIntroEnabled(false);
        settings.setFullScreenAllowed(false);
        settings.setManualResizeEnabled(false);
        settings.setSceneFactory(new RelictusSceneFactory());
        settings.setCSS(cssFileName);
        settings.setApplicationMode(ApplicationMode.DEVELOPER); // bei release version auf "Release" ändern
        settings.setSoundMenuPress(soundMenuPressFileName);
        settings.setSoundMenuSelect(soundMenuSelectFileName);
        settings.setAppIcon(appIcon);
        settings.setStageStyle(StageStyle.UNDECORATED);
        settings.setFontUI(customFont);
        settings.setFontGame(customFont);
        settings.setFontText(customFont);
        settings.setFontMono(customFont);
    }

    @Override
    protected void initGame() {
        setCustomCursor();

        getGameWorld().addEntityFactory(new RelictusEntityFactory());
        getGameWorld().setLevelFromMap("relictusTileMap.json");
        // TODO: Nachschauen ob man mit FXGL mehrere JSON laden und einfügen kann
        // Sollte es nicht funktionieren, dann muss die JSON manipuliert werden, damit wie Welt größer wird.
        // Das ganze muss dann Prozedural geschehen.

        player = getGameWorld().spawn("player", 50, 50);
        Physics.registerEntity(player);

        //getAudioPlayer().playSound("start.mp3");

        getGameScene().getViewport().setBounds(-1500, 0, 1500, 720);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

        initializeInput();
    }

    @Override
    protected void initPhysics() {
        Physics.addCollisionHandler(EntityTypes.PLAYER, EntityTypes.POWERUP);
    }

    @Override
    protected void onUpdate(double tpf) {
        Physics.onUpdate(tpf);
    }

    private void initializeInput() {
        player.getComponent(PlayerControl.class).createInput(getInput());
    }

    private void setCustomCursor() {
        FXGL.getGameScene().setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
    }
}
