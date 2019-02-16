package game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import controllers.JsonController;

import java.util.Map;

public class Relictus extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setWidth(800);
		settings.setHeight(600);
		settings.setTitle(String.valueOf(JsonController.getInstance().json.getJSONObject("application").get("name")));
		settings.setVersion("");
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
}
