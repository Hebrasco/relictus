package game;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.settings.GameSettings;
import factories.RelictusEntityFactory;
import factories.RelictusSceneFactory;
import game.player.PlayerControl;
import preferences.GamePreferences;
import utils.CustomCursor;

import static preferences.GamePreferences.*;

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
        settings.setWidth(windowWidth);
        settings.setHeight(windowHeight);
        settings.setTitle(gameTitle);
        settings.setVersion(version);
        settings.setMenuEnabled(isMenuEnabled);
        settings.setIntroEnabled(isIntroEnabled);
        settings.setFullScreenAllowed(isFullScreenAllowed);
        settings.setManualResizeEnabled(isManualResizeAllowed);
        settings.setSceneFactory(sceneFactory);
        settings.setCSS(cssFileName);
        settings.setApplicationMode(applicationMode);
        settings.setSoundMenuPress(menuPath + soundMenuPressFileName);
        settings.setSoundMenuSelect(menuPath + soundMenuSelectFileName);
        settings.setAppIcon(iconPath + appIconFileName);
        settings.setFontUI(menuFont);
        settings.setFontGame(menuFont);
        settings.setFontText(menuFont);
        settings.setFontMono(menuFont);
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

        //getAudioPlayer().playSound("start.mp3");

        getGameScene().getViewport().setBounds(-1500, 0, 1500, 720);
        getGameScene().getViewport().bindToEntity(player, getWidth() / 2, getHeight() / 2);

        initializeInput();
    }

    @Override
    protected void initPhysics() {
    }

    @Override
    protected void onUpdate(double tpf) {
    }

    private void initializeInput() {
        player.getComponent(PlayerControl.class).createInput(getInput());
    }

    private void setCustomCursor() {
        FXGL.getGameScene().setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
    }
}
