package utils;

import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.FXGLScene;
import com.almasb.fxgl.scene.GameScene;
import com.almasb.fxgl.scene.LoadingScene;

public class UIPreferences {
    // TODO: Custom Cursor für loadingScene setzen
    public static void setCustomCursor(FXGLScene scene) {
        if (isProvenScene(scene)) {
            scene.setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
        }
    }

    private static Boolean isProvenScene(FXGLScene scene) {
        final Class sceneClass = scene.getClass();
        final Class[] fxglScenes = new Class[] { GameScene.class, FXGLMenu.class, LoadingScene.class };

        for (Class fxglScene : fxglScenes) {
            if (fxglScene.equals(sceneClass)) {
                return true;
            }
        }
        return false;
    }
}
