package factories;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.LoadingScene;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;
import menu.LoadingScreen;
import menu.RelictusMenu;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the games game menu, main menu and loading scene.
 *
 * @author Daniel Bedrich, Roman Rubashkin
 * @version 1.0
 */
public class RelictusSceneFactory extends SceneFactory {

    @NotNull
    @Override
    public FXGLMenu newGameMenu(@NotNull GameApplication app) {
        return new RelictusMenu(app, MenuType.GAME_MENU);
    }

    @NotNull
    @Override
    public FXGLMenu newMainMenu(@NotNull GameApplication app) {
        return new RelictusMenu(app, MenuType.MAIN_MENU);
    }

    @NotNull
    @Override
    public LoadingScene newLoadingScene() {
        return new LoadingScreen();
    }
}
