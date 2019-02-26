package game;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.util.Credits;
import com.sun.tools.javac.util.List;
import factories.RelictusEntityFactory;
import factories.RelictusSceneFactory;
import factories.RelictusType;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
import utils.CustomCursor;
import utils.UIPreferences;

import java.util.Map;

/**
 * @author Daniel Bedrich, Kevin Ortmeier
 */
public class Relictus extends GameApplication {
	private Entity player;
	PlayerControl playerComponent;
	private PhysicsComponent physics;

	private LocalTimer timer;

	@Override
	protected void initSettings(GameSettings settings) {
		//PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
		int windowWidth = 1280;
		int windowHeight = 720;
		String cssFileName = "ui_style.css";
		String appIcon = "icon/relictus.png";
		String soundPath = "menu/";
		String customFont = "Jura-Medium.ttf";
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
		settings.setFontUI(customFont);
		settings.setFontGame(customFont);
		settings.setFontText(customFont);
		settings.setFontMono(customFont);
	}

	@Override
	protected void initGame() {
	    setCustomCursor();
	    
		getGameWorld().addEntityFactory(new RelictusEntityFactory());
		getGameWorld().setLevelFromMap("relictusTileMap.json");

		//getAudioPlayer().playSound("start.mp3");
		player = getGameWorld().spawn("player", 50, 50);

			getGameScene().getViewport().setBounds(-1500, 0, 1500, 720);
			getGameScene().getViewport().bindToEntity(player, getWidth() / 2 , getHeight() / 2);
	}
	
	protected void initPhysics() {
		getPhysicsWorld().addCollisionHandler(new CollisionHandler(RelictusType.PLAYER, RelictusType.POWERUP) {
			@Override
			protected void onCollision(Entity player, Entity powerup) {
				powerup.removeFromWorld();
				getAudioPlayer().playSound("powerup.mp3");
			}
		});
	}

	@Override
	public void initInput() {
	getInput().addAction(new UserAction("Move Right") {
		@Override
		protected void onAction() {
			player.getComponent(PlayerControl.class).moveRight();
		}
	}, KeyCode.D);

		getInput().addAction(new UserAction("Move Left") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerControl.class).moveLeft();
			}
		}, KeyCode.A);

		getInput().addAction(new UserAction("jump") {
			@Override
			protected void onAction() {
				player.getComponent(PlayerControl.class).jump();
			}
		}, KeyCode.W);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void setCustomCursor() {
		FXGL.getGameScene().setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
	}
}
