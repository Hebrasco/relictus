package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.util.Credits;
import com.almasb.fxgl.dsl.FXGL;
import controllers.JsonController;
import factories.RelictusSceneFactory;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Bedrich
 */
public class Relictus extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		int windowWidth = 800;
		int windowHeight = 600;
		String cssName = "ui_style.css";
		String title = String.valueOf(JsonController.getInstance().json.getJSONObject("application").get("name"));
		String version = "0.1";
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
		settings.setSceneFactory(new RelictusSceneFactory());
		settings.setCSS(cssName);
		settings.setEnabledMenuItems(menuItems);
		settings.setCredits(credits);
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
		if (JsonController.getInstance().json == null) {
			JsonController.getInstance().loadTextsJson();
		}
		launch(args);
	}
}
