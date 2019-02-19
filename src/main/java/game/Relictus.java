package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import controllers.JsonController;
import factories.RelictusSceneFactory;

import java.util.Map;

public class Relictus extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setTitle(String.valueOf(JsonController.getInstance().json.getJSONObject("application").get("name")));
		settings.setVersion("0.1.0");
		settings.setMenuEnabled(true);
		settings.setIntroEnabled(false);
		settings.setSceneFactory(new RelictusSceneFactory());
		// settings.setCSS(); TODO: Default CSS einfügen und anpassen
		// settings.setEnabledMenuItems(...); TODO: Set enabled menu itemes (Online and extras)
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
