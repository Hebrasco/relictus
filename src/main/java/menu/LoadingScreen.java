package menu;

import com.almasb.fxgl.scene.LoadingScene;
import utils.CustomCursor;

/**
 * @author Daniel Bedrich
 */
public class LoadingScreen extends LoadingScene {
    public LoadingScreen() {
        setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
    }
}
