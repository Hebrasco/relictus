package menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.scene.FXGLMenu;
import com.almasb.fxgl.scene.menu.MenuType;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.ui.FXGLScrollPane;
import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.CustomCursor;
import utils.Particles;
import utils.PropertiesLoader;

import static preferences.GamePreferences.*;
import static data.MenuKeys.*;

/**
 * @author Daniel Bedrich
 */
public class RelictusMenu extends FXGLMenu {
    private final ParticleSystem particleSystem = new ParticleSystem();

    public RelictusMenu(GameApplication app, MenuType type) {
        super(app, type);
        createMenu(type);
        setCustomCursor();
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
        return createTitleImage();
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
        particleSystem.onUpdate(tpf);
    }

    private Texture createBackgroundTexture(double width, double height) {
        final Texture backgroundImage = FXGL.getAssetLoader().loadTexture(MENU_PATH + MENU_BACKGROUND_FILE_NAME);
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        return backgroundImage;
    }

    private Texture createTitleImage() {
        final double scaleFactor = 3.0;
        final double width = FXGL.getAppWidth() / scaleFactor;
        final double height = FXGL.getAppHeight() / scaleFactor;
        final double posX = (FXGL.getAppWidth() / 2.0) - (width / 2.0);
        final Texture titleImage = FXGL.getAssetLoader().loadTexture(MENU_PATH + MENU_TITLE_IMAGE);
        titleImage.setFitWidth(width);
        titleImage.setFitHeight(height);
        titleImage.setTranslateX(posX);
        return titleImage;
    }

    private Text createVersionTextView(String version) {
        final Text versionText = FXGL.getUIFactory().newText(version);
        versionText.setTranslateY(FXGL.getAppHeight() - 2.0);
        return versionText;
    }

    private Text createProfileTextView(String profileName) {
        final Text profileText = FXGL.getUIFactory().newText(profileName);
        profileText.setTranslateY(FXGL.getAppHeight() - 2.0);
        profileText.setTranslateX(FXGL.getAppWidth() - profileText.getLayoutBounds().getWidth());
        return profileText;
    }

    private MenuRoot inflateMenu(MenuType menuType) {
        final MenuRoot menu = new MenuRoot();

        if (isMainMenu(menuType)) {
            menu.add(createMenuItemSingleplayer());
            menu.add(createMenuItemMultiplayer());
            menu.add(createMenuItemCredits());
            menu.add(createMenuItemExit());
        } else {
            menu.add(createMenuItemResume());
            menu.add(createMenuItemExitToMainMenu());
        }

        return menu;
    }

    private MenuItem createMenuItemSingleplayer() {
        final MenuItem singleplayerMenuButton = new MenuItem(SINGLEPLAYER);
        singleplayerMenuButton.setOnAction(event -> fireNewGame());
        return singleplayerMenuButton;
    }

    private MenuItem createMenuItemMultiplayer() {
        final MenuItem multiplayerMenuButton = new MenuItem(MULTIPLAYER);
        multiplayerMenuButton.setOnAction(e -> fireMultiplayer()); // TODO: eigene funktion für "fireMultiplayer()" implementieren
        return multiplayerMenuButton;
    }

    private MenuItem createMenuItemExit() {
        final MenuItem exitMenuButton = new MenuItem(QUIT);
        exitMenuButton.setOnAction(event -> fireExit());
        return exitMenuButton;
    }

    private MenuItem createMenuItemResume() {
        final MenuItem resumeMenuButton = new MenuItem(RESUME);
        resumeMenuButton.setOnAction(event -> fireResume());
        return resumeMenuButton;
    }

    private MenuItem createMenuItemCredits() {
        final MenuItem creditsMenuButton = new MenuItem(CREDITS);
        creditsMenuButton.setMenuContent(this::createCreditsContent, this);
        return creditsMenuButton;
    }

    private MenuItem createMenuItemExitToMainMenu() {
        final MenuItem exitMainMenuButton = new MenuItem(MAIN_MENU);
        exitMainMenuButton.setOnAction(event -> fireExitToMainMenu());
        return exitMainMenuButton;
    }

    private MenuItem createActionMenuButton(String key, Runnable runnable) {
        final MenuItem button = new MenuItem(key);
        button.addEventHandler(ActionEvent.ACTION, event -> runnable.run());
        return button;
    }

    private MenuContent createCreditsContent() {
        final ScrollPane pane = new FXGLScrollPane();
        pane.setPrefWidth(FXGL.getAppWidth() * 3.0 / 5.0);
        pane.setPrefHeight(FXGL.getAppWidth() / 2.0);
        pane.setStyle("-fx-background:black;");

        final VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(pane.getPrefWidth() - 15);

        addCreditEntries(vbox);
        pane.setContent(vbox);

        return new MenuContent(pane);
    }

    private String[] getCreditsEntries() {
        return new String[]{
                CREDITS_CREATED_BY,
                CREDITS_KEVIN_ORTMEIER,
                CREDITS_MARKUS_KREMER,
                CREDITS_LARA_MARIE_MANN,
                CREDITS_ROMAN_RUBASHKIN,
                CREDITS_DANIEL_BEDRICH,
                "", // Leerzeile
                CREDITS_POWERED_BY_FXGL,
                CREDITS_FXGL_AUTHOR,
                CREDITS_FXGL_REPO,

        };
    }

    private void addCreditEntries(VBox vbox) {
        final String[] creditEntries = getCreditsEntries();

        for (String credit : creditEntries) {
            String creditText;

            if (credit.isEmpty()) {
                creditText = "";
            } else {
                creditText = PropertiesLoader.getResourceProperties(credit);
            }

            if (credit.equals(creditEntries[creditEntries.length - 3])) {
                creditText += " " + FXGL.getVersion();
            }
            vbox.getChildren().add(FXGL.getUIFactory().newText(creditText));
        }
    }

    private void createMenu(MenuType menuType) {
        final MenuRoot menu = inflateMenu(menuType);

        final double menuX = 50;
        final double menuY = app.getHeight() / 2.0 - menu.getLayoutBounds().getHeight() / 2.0;
        final double menuContentX = 256.0;

        setTranslate(menuRoot, menuX, menuY);
        setTranslate(contentRoot, menuContentX, menuY);
        // TODO: setTranslateX nicht statisch, sondern dynamisch darstellen
        // FXGL.getAppWidth() / 2.0 - menu.getLayoutBounds().getWidth() - menuPosX
        // Problem: Menü breite ist immer 0.0
        // Credits breite ist FXGL.getAppWidth() * 3 / 5

        setParticles();

        menuRoot.getChildren().addAll(menu);
        contentRoot.getChildren().add(EMPTY);

        setListener(menu);
    }

    private void setTranslate(Pane pane, double PosX, double PosY) {
        pane.setTranslateX(PosX);
        pane.setTranslateY(PosY);
    }

    private void setParticles() {
        ParticleEmitter dustParticleEmitter = Particles.getDustEmitter();
        particleSystem.addParticleEmitter(dustParticleEmitter, 0, -FXGL.getAppHeight());
        getContentRoot().getChildren().add(3, particleSystem.getPane());
    }

    private void setListener(MenuRoot menu) {
        activeProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                switchMenuTo(menu);
                switchMenuContentTo(EMPTY);
            }
        });
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
        setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
    }

    private boolean isMainMenu(MenuType menuType) {
        return menuType == MenuType.MAIN_MENU;
    }
}
