package factories;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.SceneFactory;
import com.almasb.fxgl.scene.MenuType;
import menu.RelictusMenu;
import org.jetbrains.annotations.NotNull;

public class RelictusSceneFactory extends SceneFactory {

    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new RelictusMenu(MenuType.MAIN_MENU);
    }

    @NotNull
    @Override
    public FXGLMenu newGameMenu() {
        return new RelictusMenu(MenuType.GAME_MENU);
    }
}
