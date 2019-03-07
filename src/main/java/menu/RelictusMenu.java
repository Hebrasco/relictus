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
 * Creates the main menu and game menu.
 * @author Daniel Bedrich
 * @version 1.0
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

    /**
     * Creates the background texture with the windows width and height.
     * @param width the width of the game window.
     * @param height the height of the game window.
     * @return the background texture.
     */
    private Texture createBackgroundTexture(double width, double height) {
        final Texture backgroundImage = FXGL.getAssetLoader().loadTexture(MENU_PATH + MENU_BACKGROUND_FILE_NAME);
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        return backgroundImage;
    }

    /**
     * Creates the title image and scales it to the given factor.
     * @return the title image.
     */
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

    /**
     * Creates the version text view.
     * @param version the version of the game.
     * @return the {@link Text} object to display.
     */
    private Text createVersionTextView(String version) {
        final Text versionText = FXGL.getUIFactory().newText(version);
        versionText.setTranslateY(FXGL.getAppHeight() - 2.0);
        return versionText;
    }

    /**
     * Creates the profile text view.
     * @param profileName the profile name of the selected profile.
     * @return the {@link Text} object to display.
     */
    private Text createProfileTextView(String profileName) {
        final Text profileText = FXGL.getUIFactory().newText(profileName);
        profileText.setTranslateY(FXGL.getAppHeight() - 2.0);
        profileText.setTranslateX(FXGL.getAppWidth() - profileText.getLayoutBounds().getWidth());
        return profileText;
    }

    /**
     * Inflates the main menu or game menu, bases on the {@link MenuType}.
     * @param menuType the type of the menu, that should be inflated.
     * @return the inflated menu.
     */
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

    /**
     * Creates the menu item for the singleplayer with click events.
     * @return the singleplayer menu item.
     */
    private MenuButton createMenuItemSingleplayer() {
        final MenuButton singleplayerMenuButton = new MenuButton(SINGLEPLAYER);
        singleplayerMenuButton.setOnAction(event -> fireNewGame());
        return singleplayerMenuButton;
    }

    /**
     * Creates the menu item for the multiplayer with click events.
     * @return the multiplayer menu item.
     */
    private MenuButton createMenuItemMultiplayer() {
        final MenuButton multiplayerMenuButton = new MenuButton(MULTIPLAYER);
        multiplayerMenuButton.setOnAction(e -> fireMultiplayer()); // TODO: eigene funktion für "fireMultiplayer()" implementieren
        return multiplayerMenuButton;
    }

    /**
     * Creates the menu item for quitting the game with click events.
     * @return the quit game menu item.
     */
    private MenuButton createMenuItemExit() {
        final MenuButton exitMenuButton = new MenuButton(QUIT);
        exitMenuButton.setOnAction(event -> fireExit());
        return exitMenuButton;
    }

    /**
     * Creates the menu item for resuming the game with click events.
     * @return the resume game menu item.
     */
    private MenuButton createMenuItemResume() {
        final MenuButton resumeMenuButton = new MenuButton(RESUME);
        resumeMenuButton.setOnAction(event -> fireResume());
        return resumeMenuButton;
    }

    /**
     * Creates the menu item for the credits with click events.
     * @return the credits menu item.
     */
    private MenuButton createMenuItemCredits() {
        final MenuButton creditsMenuButton = new MenuButton(CREDITS);
        creditsMenuButton.setMenuContent(this::createCreditsContent, this);
        return creditsMenuButton;
    }

    /**
     * Creates the menu item for exiting to main menu with click events.
     * @return the exit to main menu menu item.
     */
    private MenuButton createMenuItemExitToMainMenu() {
        final MenuButton exitMainMenuButton = new MenuButton(MAIN_MENU);
        exitMainMenuButton.setOnAction(event -> fireExitToMainMenu());
        return exitMainMenuButton;
    }

    /**
     * Creates the action menu item.
     * @return the action button.
     */
    private MenuButton createActionMenuButton(String key, Runnable runnable) {
        final MenuButton button = new MenuButton(key);
        button.addEventHandler(ActionEvent.ACTION, event -> runnable.run());
        return button;
    }

    /**
     * Creates the menu content for the credits.
     * @return the menu content.
     */
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

    /**
     * Creates a string array with all credits entries.
     * @return the string array.
     */
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

    /**
     * Loads and adds all credits entries to the {@link com.almasb.fxgl.scene.FXGLMenu.MenuContent}.
     * @param vbox the {@link com.almasb.fxgl.scene.FXGLMenu.MenuContent} to add the credits entries to.
     */
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

    /**
     * Creates and sets up the menu based on the passed {@link MenuType}.
     * @param menuType the {@link MenuType} of the menu to inflate.
     */
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

    /**
     * Sets the position of {@link Pane}.
     * @param pane the pane to set the position on.
     * @param PosX the X position in pixels.
     * @param PosY the Y position in pixels.
     */
    private void setTranslate(Pane pane, double PosX, double PosY) {
        pane.setTranslateX(PosX);
        pane.setTranslateY(PosY);
    }

    /**
     * Adds the particle effect to the menu.
     */
    private void setParticles() {
        ParticleEmitter dustParticleEmitter = Particles.getDustEmitter();
        particleSystem.addParticleEmitter(dustParticleEmitter, 0, -FXGL.getAppHeight());
        getContentRoot().getChildren().add(3, particleSystem.getPane());
    }

    /**
     * Adds the listener for the menu.
     * @param menu the menu to switch to.
     */
    private void setListener(MenuRoot menu) {
        activeProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                switchMenuTo(menu);
                switchMenuContentTo(EMPTY);
            }
        });
    }

    /**
     * Plays a transition to the passed menu.
     * @param menu the menu to play the transition to.
     */
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

    /**
     * Sets the custom relictus cursor in the menu scene.
     */
    private void setCustomCursor() {
        setCursor(CustomCursor.DEFAULT_CURSOR, CustomCursor.DEFAULT_HOTSPOT);
    }

    /**
     * Checks if the {@link MenuType} equals main menu.
     * @param menuType the {@link MenuType} to check.
     * @return true, if the {@link MenuType} is the main menu.
     */
    private boolean isMainMenu(MenuType menuType) {
        return menuType == MenuType.MAIN_MENU;
    }
}
