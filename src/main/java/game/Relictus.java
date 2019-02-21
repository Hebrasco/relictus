package game;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.util.Credits;
import com.almasb.fxgl.dsl.FXGL;
import factories.RelictusSceneFactory;
import javafx.beans.binding.StringBinding;

import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Bedrich
 */
public class Relictus extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		//PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		int windowWidth = 800;
		int windowHeight = 600;
		String cssFileName = "ui_style.css";
		String appIcon = "relictus.png";
		String soundPath = "menu/";
		String soundMenuPressFileName = soundPath + "menu_klick.wav";
		String soundMenuSelectFileName = soundPath + "menu_move.wav";
		//String title = propertiesLoader.getResourceProperties("app.title"); // TODO: fix lateinit exception
		//String version = propertiesLoader.getResourceProperties("app.version"); // TODO: fix lateinit exception
		String title = "R E L I C T U S"; // Replace with above
		String version = "0.1"; // Replace with above
		EnumSet<MenuItem> menuItems = EnumSet.of(
				MenuItem.ONLINE, // TODO: Online buttons und input felder implementieren
				MenuItem.EXTRA
		);
		Credits credits = new Credits(
				List.of(
						"Relictus created by Kamelrad",
						"Kevin Ortmeier",
						"Markus Kremer",
						"Lara-Marie Mann",
						"Roman Rubbashkin",
						"Daniel Bedrich"
				)
		);

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
		settings.setEnabledMenuItems(menuItems);
		settings.setCredits(credits);
		settings.setApplicationMode(ApplicationMode.DEVELOPER);
		settings.setSoundMenuPress(soundMenuPressFileName);
		settings.setSoundMenuSelect(soundMenuSelectFileName);
		settings.setAppIcon(appIcon);
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
		super.initGame();
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
}
