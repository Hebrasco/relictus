package preferences;

import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.FXGLScene;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.scene.LoadingScene;
import utils.CustomCursor;

/**
 * Defines custom UI preferences, such as the custom relictus cursor.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class UIPreferences {
    /**
     * Sets the custom relictus cursor.
     * @param scene the scene to set the cursor to.
     */
    public static void setCustomCursor(FXGLScene scene) {
        if (isProvenScene(scene)) {
            scene.setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
        }
    }

    /**
     * Checks if the scene is a {@link GameScene}, {@link FXGLMenu} or {@link LoadingScene}.
     * @param scene the scene to be checked.
     * @return true, if the scene is one of the specified scenes.
     */
    private static Boolean isProvenScene(FXGLScene scene) {
        final Class sceneClass = scene.getClass();
        final Class[] fxglScenes = new Class[]{GameScene.class, FXGLMenu.class, LoadingScene.class};

        for (Class fxglScene : fxglScenes) {
            if (fxglScene.equals(sceneClass)) {
                return true;
            }
        }
        return false;
    }
}
