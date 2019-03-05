package preferences;

import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.FXGLScene;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.scene.LoadingScene;
import utils.CustomCursor;

/**
 * @author Daniel Bedrich
 */
public class UIPreferences {
    public static void setCustomCursor(FXGLScene scene) {
        if (isProvenScene(scene)) {
            scene.setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
        }
    }

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
