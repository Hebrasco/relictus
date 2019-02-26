package menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.CustomCursor;
import utils.Particles;

/**
 * @author Daniel Bedrich
 */
public class RelictusMenu extends FXGLMenu {
    //TODO: Fix game startup loop (Verursacht durch Partikelsystem)
    //private ParticleSystem particleSystem = new ParticleSystem();

    public RelictusMenu(GameApplication app, MenuType type) {
        super(app, type);
        //createMenu(type);
        setCustomCursor();

        MenuRoot menu;
        if (type == MenuType.MAIN_MENU) {
                menu = createMenuBodyMainMenu();
        } else {
                menu = createMenuBodyGameMenu();
        }

        double menuX = 50;
        double menuY = app.getHeight() / 2.0 - menu.getLayoutBounds().getHeight() / 2.0;

        menuRoot.setTranslateX(menuX);
        menuRoot.setTranslateY(menuY);

        contentRoot.setTranslateX(app.getWidth() - 500);
        contentRoot.setTranslateY(menuY);

        //ParticleEmitter dustParticleEmitter = Particles.getDustEmitter();
        //particleSystem.addParticleEmitter(dustParticleEmitter, 0, app.getHeight());
        //contentRoot.getChildren().add(3, particleSystem.getPane());

        menuRoot.getChildren().addAll(menu);
        contentRoot.getChildren().add(EMPTY);

        activeProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                switchMenuTo(menu);
                switchMenuContentTo(EMPTY);
            }
        });
    }

    @Override
    protected Button createActionButton(StringBinding name, Runnable action) {
        return createActionMenuButton(name.getValue(), action).button;
    }

    @Override
    protected Button createActionButton(String name, Runnable action) {
        return createActionMenuButton(name, action).button;
    }

    @Override
    protected Node createBackground(double width, double height) {
        return createBackgroundTexture(width, height);
    }

    @Override
    protected Node createTitleView(String title) {
        final SimpleObjectProperty<Color> titleColor = new SimpleObjectProperty<>(Color.WHITE);
        final Text titleText = createTitle(title, titleColor);
        final HBox titleLayout = createTitleLayout(titleText);

        return getFormattedTitle(titleText, titleLayout);
    }

    @Override
    protected Node createVersionView(String version) {
        return createVersionTextView(version);
    }

    @Override
    protected Node createProfileView(String profileName) {
        return createProfileTextView(profileName);
    }

    @Override
    protected void switchMenuTo(Node menuRoot) {
        playTransition(menuRoot);
    }

    @Override
    protected void switchMenuContentTo(Node content) {
        contentRoot.getChildren().set(0, content);
    }

    @Override
    public void onUpdate(double tpf) {
        //particleSystem.onUpdate(tpf);
    }

    private Texture createBackgroundTexture(double width, double height) {
        final String imagePath = "menu/";
        final String imageName = "menu_background.gif";
        final Texture backgroundImage = FXGL.getAssetLoader().loadTexture(imagePath + imageName);
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        return backgroundImage;
    }

    private StackPane getFormattedTitle(Text titleText, HBox box) {
        final double textWidth = titleText.getLayoutBounds().getWidth();

        final StackPane titleRoot = new StackPane();
        titleRoot.getChildren().addAll(createTitleBorder(textWidth), box);

        titleRoot.setTranslateX(FXGL.getAppWidth() / 2.0 - (textWidth + 30) / 2);
        titleRoot.setTranslateY(50);
        return titleRoot;
    }

    private HBox createTitleLayout(Text titleText) {
        final HBox box = new HBox(titleText);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Text createTitle(String title, SimpleObjectProperty<Color> color) {
        final Text titleText = FXGL.getUIFactory().newText(title, 55.0);
        titleText.setFill(null);
        titleText.strokeProperty().bind(color);
        titleText.setStrokeWidth(1.5);
        return titleText;
    }

    private Text createVersionTextView(String version) {
        final Text versionText = FXGL.getUIFactory().newText(version);
        versionText.setTranslateY(FXGL.getAppHeight() - 2.0);
        return versionText;
    }

    private Text createProfileTextView(String profileName) {
        final Text profileText = FXGL.getUIFactory().newText(profileName);
        profileText.setTranslateY(FXGL.getAppHeight() - 2);
        profileText.setTranslateX(FXGL.getAppWidth() - profileText.getLayoutBounds().getWidth());
        return profileText;
    }

    private Rectangle createTitleBorder(double textWidth) {
        final Rectangle border = new Rectangle(textWidth + 30, 65, null);
        border.setStroke(Color.WHITE);
        border.setStrokeWidth(4);
        border.setArcWidth(25);
        border.setArcHeight(25);
        return border;
    }

    private MenuRoot createMenuBodyMainMenu() {
        final MenuRoot box = new MenuRoot();

        final MenuButton itemNewGame = new MenuButton("menu.newGame");
        itemNewGame.setOnAction(e -> fireNewGame());
        box.add(itemNewGame);

        final MenuButton itemMultiplayer = new MenuButton("menu.online");
        itemMultiplayer.setOnAction(e -> fireMultiplayer());
        box.add(itemMultiplayer);

        final MenuButton itemCredits = new MenuButton("menu.credits");
        itemCredits.setMenuContent(this::createCredits, this);
        box.add(itemCredits);

        final MenuButton itemLogout = new MenuButton("menu.logout");
        itemLogout.setOnAction(e -> fireLogout());
        box.add(itemLogout);

        final MenuButton itemExit = new MenuButton("menu.exit");
        itemExit.setOnAction(e -> fireExit());
        box.add(itemExit);

        return box;
    }

    private MenuRoot createMenuBodyGameMenu() {
        final MenuRoot box = new MenuRoot();

        final MenuButton itemResume = new MenuButton("menu.resume");
        itemResume.setOnAction(e -> fireResume());
        box.add(itemResume);

        final MenuButton itemExit = new MenuButton("menu.mainMenu");
        itemExit.setOnAction(e -> fireExitToMainMenu());
        box.add(itemExit);

        return box;
    }

    private MenuButton createActionMenuButton(String key, Runnable runnable) {
        final MenuButton button = new MenuButton(key);
        button.addEventHandler(ActionEvent.ACTION, event -> runnable.run());
        return button;
    }

    private MenuItem createCredits() {
        return new MenuItem(new Pane());
    }

    private void playTransition(Node menu) {
        Node oldMenu = menuRoot.getChildren().get(0);

        FadeTransition fadeTransitionOldMenu = new FadeTransition(Duration.seconds(0.33), oldMenu);
        fadeTransitionOldMenu.setToValue(0);
        fadeTransitionOldMenu.setOnFinished(e -> {
            menu.setOpacity(0);
            menuRoot.getChildren().set(0, menu);
            oldMenu.setOpacity(1);

            FadeTransition fadeTransitionMenuBox = new FadeTransition(Duration.seconds(0.33), menu);
            fadeTransitionMenuBox.setToValue(1.0);
            fadeTransitionMenuBox.play();
        });
        fadeTransitionOldMenu.play();
    }

    private void setCustomCursor() {
        setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
    }
}
