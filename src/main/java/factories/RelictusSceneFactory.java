package factories;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.LoadingScene;
import com.almasb.fxgl.scene.SceneFactory;
import com.almasb.fxgl.scene.menu.MenuType;
import javafx.scene.paint.Color;
import menu.LoadingScreen;
import menu.RelictusMenu;
import org.jetbrains.annotations.NotNull;

/**
 * @author Daniel Bedrich
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
