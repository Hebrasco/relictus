package menu;

import com.almasb.fxgl.scene.LoadingScene;
import utils.CustomCursor;

/**
 * Defines the custom loading scene.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class LoadingScreen extends LoadingScene {
    public LoadingScreen() {
        setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
    }
}
