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
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import utils.CustomCursor;
import utils.Particles;
import utils.PropertiesLoader;

import java.io.File;

/**
 * @author Daniel Bedrich
 */
public class RelictusMenu extends FXGLMenu {
    private ParticleSystem particleSystem = new ParticleSystem();

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
        particleSystem.onUpdate(tpf);
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
        titleRoot.getChildren().add(box);

        titleRoot.setTranslateX(FXGL.getAppWidth() / 2.0 - (textWidth + 30.0) / 2.0);
        titleRoot.setTranslateY(50.0);
        return titleRoot;
    }

    private HBox createTitleLayout(Text titleText) {
        final HBox box = new HBox(titleText);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Text createTitle(String title, SimpleObjectProperty<Color> color) {
        final double fontSize = 55.0;
        final String fontName = "CinzelDecorative-Bold.ttf";
        final Text titleText = FXGL.getUIFactory().newText(title);
        final Font font = FXGL.getAssetLoader().loadFont(fontName).newFont(fontSize);
        titleText.setFill(null);
        titleText.strokeProperty().bind(color);
        titleText.setStrokeWidth(1.5);
        titleText.setFont(font);
        return titleText;
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

    private MenuRoot createMultiplayerMenu() {
        return new MenuRoot(
                createMenuItemMultiplayerConnect(),
                createMenuItemMultiplayerHost()
        );
    }

    private MenuButton createMenuItemSingleplayer() {
        final MenuButton singleplayerMenuButton = new MenuButton("menu.singleplayer");
        singleplayerMenuButton.setOnAction(event -> fireNewGame());
        return singleplayerMenuButton;
    }

    private MenuButton createMenuItemMultiplayer() {
        final MenuButton multiplayerMenuButton = new MenuButton("menu.multiplayer");
        multiplayerMenuButton.setOnAction(event -> multiplayerMenuButton.setChild(createMultiplayerMenu(), this));
        return multiplayerMenuButton;
    }

    private MenuButton createMenuItemExit() {
        final MenuButton exitMenuButton = new MenuButton("menu.quit");
        exitMenuButton.setOnAction(event -> fireExit());
        return exitMenuButton;
    }

    private MenuButton createMenuItemResume() {
        final MenuButton resumeMenuButton = new MenuButton("menu.resume");
        resumeMenuButton.setOnAction(event -> fireResume());
        return resumeMenuButton;
    }

    private MenuButton createMenuItemCredits() {
        final MenuButton creditsMenuButton = new MenuButton("menu.credits");
        creditsMenuButton.setMenuContent(this::createCreditsContent, this);
        return creditsMenuButton;
    }

    private MenuButton createMenuItemExitToMainMenu() {
        final MenuButton exitMainMenuButton = new MenuButton("menu.mainMenu");
        exitMainMenuButton.setOnAction(event -> fireExitToMainMenu());
        return exitMainMenuButton;
    }

    private MenuButton createMenuItemMultiplayerConnect() {
        final MenuButton feedbackMenuButton = new MenuButton("multiplayer.connect");
        //feedbackMenuButton.setMenuContent(this::createMultiplayerConnect, this);
        return feedbackMenuButton;
    }

    private MenuButton createMenuItemMultiplayerHost() {
        final MenuButton feedbackMenuButton = new MenuButton("multiplayer.host");
        //feedbackMenuButton.setMenuContent(this::createMultiplayerHost, this);
        return feedbackMenuButton;
    }

    private MenuButton createActionMenuButton(String key, Runnable runnable) {
        final MenuButton button = new MenuButton(key);
        button.addEventHandler(ActionEvent.ACTION, event -> runnable.run());
        return button;
    }

    private MenuContent createMultiplayerConnect() {
        // TODO: IP input feld einfügen
        final MenuContent connectContent = new MenuContent();

        /*
        connectContent.setOnOpen();
        connectContent.setOnClose();
        */
        System.out.println("created multiplayer connect");
        return connectContent;
    }

    private MenuContent createMultiplayerHost() {
        return new MenuContent();
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
        return new String[] {
                "credits.relictusCreatedBy",
                "credits.kevinOrtmeier",
                "credits.markusKremer",
                "credits.laraMarieMann",
                "credits.romanRubashkin",
                "credits.danielBedrich",
                "",
                "credits.poweredByFXGL",
                "credits.FXGLAuthor",
                "credits.FXGLRepo"
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

        setTranslate(menuRoot, menuX, menuY);
        setTranslate(contentRoot, 256.0, menuY);
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
        setCursor(CustomCursor.defaultCurser, CustomCursor.defaultHotSpot);
    }

    private boolean isMainMenu(MenuType menuType) {
        return menuType == MenuType.MAIN_MENU;
    }
}
