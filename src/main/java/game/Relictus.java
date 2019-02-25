package game;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.util.Credits;
import com.sun.tools.javac.util.List;
import factories.RelictusSceneFactory;
import javafx.geometry.Point2D;
import javafx.stage.StageStyle;
import utils.CustomCursor;
import utils.UIPreferences;

import java.util.Map;

/**
 * @author Daniel Bedrich
 */
public class Relictus extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		//PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		int windowWidth = 1280;
		int windowHeight = 720;
		String cssFileName = "ui_style.css";
		String appIcon = "icon/relictus.png";
		String soundPath = "menu/";
		String soundMenuPressFileName = soundPath + "menu_click.wav";
		String soundMenuSelectFileName = soundPath + "menu_move.wav";
		//String title = propertiesLoader.getResourceProperties("app.title"); // TODO: fix lateinit exception
		//String version = propertiesLoader.getResourceProperties("app.version"); // TODO: fix lateinit exception
		String title = "R E L I C T U S"; // Replace with above
		String version = "0.1"; // Replace with above



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
	}
	
	@Override
	protected void initInput() {
		super.initInput();
	}

	@Override
	protected void initGameVars(Map<String, Object> vars) {
		super.initGameVars(vars);
	}

	@Override
	protected void initGame() {
		setCustomCursor();
	}

	@Override
	protected void initUI() {
		super.initUI();
	}

	@Override
	protected void onUpdate(double tpf) {
		super.onUpdate(tpf);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void setCustomCursor() {
		FXGL.getGameScene().setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
	}
}
