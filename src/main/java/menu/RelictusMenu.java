package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.scene.MenuType;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import utils.Particles;

/**
 * @author Daniel Bedrich
 */
public class RelictusMenu extends FXGLMenu {
    //private final ArrayList<Animation> animations = new ArrayList<>();
    private final ParticleSystem particleSystem = new ParticleSystem();
    private VBox emptyVBox = new MenuContent();
    //private double t = 0.0;

    public RelictusMenu(@NotNull MenuType type) {
        super(type);
        createMenu(type);
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding stringBinding, @NotNull Runnable runnable) {
        return createActionMenuButton(stringBinding.getValue(), runnable).button;
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull String key, @NotNull Runnable runnable) {
        return createActionMenuButton(key, runnable).button;
    }

    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        return createBackgroundTexture(width, height);
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        return createProfileTextView(profileName);
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        final SimpleObjectProperty<Color> titleColor = new SimpleObjectProperty<>(Color.WHITE);
        final Text titleText = createTitle(title, titleColor);
        final HBox titleLayout = createTitleLayout(titleText);

        return getFormattedTitle(titleText, titleLayout);
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        return createVersionTextView(version);
    }

    @Override
    protected void switchMenuTo(@NotNull Node menuBox) {
        playTransition(menuBox);
    }

    @Override
    protected void switchMenuContentTo(@NotNull Node content) {
        getMenuContentRoot().getChildren().set(0, content);
    }

    @Override
    protected void onUpdate(double tpf) {
        particleSystem.onUpdate(tpf);
    }

    private MenuButton createActionMenuButton(String key, Runnable runnable) {
        final MenuButton button = new MenuButton(key);
        button.addEventHandler(ActionEvent.ACTION, event -> runnable.run());
        return button;
    }

    private StackPane getFormattedTitle(Text titleText, HBox box) {
        final double textWidth = titleText.getLayoutBounds().getWidth();

        final StackPane titleRoot = new StackPane();
        titleRoot.getChildren().addAll(createTitleBorder(textWidth), box);

        titleRoot.setTranslateX(FXGL.getAppWidth() / 2.0 - (textWidth + 30) / 2.0);
        titleRoot.setTranslateY(50);
        return titleRoot;
    }

    private HBox createTitleLayout(Text titleText) {
        HBox box = new HBox(titleText);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private Text createTitle(String title, SimpleObjectProperty<Color> titleColor) {
        Text titleText = FXGL.getUIFactory().newText(title, 55.0);
        titleText.setFill(null);
        titleText.strokeProperty().bind(titleColor);
        titleText.setStrokeWidth(1.5);
        return titleText;
    }

    private Rectangle createTitleBorder(double textWidth) {
        Rectangle titleBorder = new Rectangle(textWidth + 30, 65.0, null);
        titleBorder.setStroke(Color.WHITE);
        titleBorder.setStrokeWidth(4.0);
        titleBorder.setArcWidth(25.0);
        titleBorder.setArcHeight(25.0);
        return titleBorder;
    }

    // TODO: Profile anlegen können (Profil Name == Online Spieler Name)
    private Text createProfileTextView(String profileName) {
        final Text view = FXGL.getUIFactory().newText(profileName);
        view.setTranslateY((FXGL.getAppHeight() - 2.0));
        view.setTranslateX(FXGL.getAppWidth() - view.getLayoutBounds().getWidth());
        return view;
    }

    private Text createVersionTextView(String version) {
        final Text view = FXGL.getUIFactory().newText(version);
        view.setTranslateY((FXGL.getAppHeight() - 2.0));
        return view;
    }

    private Texture createBackgroundTexture(double width, double height) {
        Texture backgroundImage = FXGL.getAssetLoader().loadTexture("menu/launcher_background.gif");
        backgroundImage.setFitWidth(width);
        backgroundImage.setFitHeight(height);
        return backgroundImage;
    }

    private void playTransition(Node menuBox) {
        final Node oldMenu = getMenuRoot().getChildren().get(0);
        final FadeTransition fadeTransitionOldMenu = new FadeTransition(Duration.seconds(0.33), oldMenu);
        fadeTransitionOldMenu.setToValue(0.0);
        fadeTransitionOldMenu.setOnFinished(event -> {
            menuBox.setOpacity(0.0);
            getMenuRoot().getChildren().set(0, menuBox);
            oldMenu.setOpacity(1.0);

            final FadeTransition fadeTransitionMenuBox = new FadeTransition(Duration.seconds(0.33), menuBox);
            fadeTransitionMenuBox.setToValue(1.0);
            fadeTransitionMenuBox.play();
        });
        fadeTransitionOldMenu.play();
    }

    private void createMenu(MenuType menuType) {
        final MenuBox menu = inflateMenu(menuType);

        final double menuPosX = 50.0;
        final double menuPosY = FXGL.getAppHeight() / 2.0 - menu.getLayoutBounds().getHeight() / 2.0;

        getMenuRoot().setTranslateX(menuPosX);
        getMenuRoot().setTranslateY(menuPosY);

        // TODO: setTranslateX nicht statisch, sondern dynamisch darstellen
        // FXGL.getAppWidth() / 2.0 - menu.getLayoutBounds().getWidth() - menuPosX
        // Problem: Menü breite ist immer 0.0
        // Credits breite ist FXGL.getAppWidth() * 3 / 5
        getMenuContentRoot().setTranslateX(256);
        getMenuContentRoot().setTranslateY(menuPosY);

        ParticleEmitter dustParticleEmitter = Particles.getDustEmitter();
        particleSystem.addParticleEmitter(dustParticleEmitter, 0.0, -FXGL.getAppHeight());
        getContentRoot().getChildren().add(3, particleSystem.getPane());

        getMenuRoot().getChildren().addAll(menu);
        getMenuContentRoot().getChildren().add(emptyVBox);

        activeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                switchMenuTo(menu);
                switchMenuContentTo(emptyVBox);
            }
        });
    }

    private MenuBox inflateMenu(MenuType menuType) {
        final MenuBox box = new MenuBox();

        if (isMainMenu(menuType)) {
            box.add(createMenuItemSingleplayer());
            box.add(createMenuItemMultiplayer());
            // TODO: Profile menü hinzufügen (im Profile menü wird der Spielername festgelegt)
            box.add(createMenuItemCredits());
            box.add(createMenuItemExit());
        } else {
            box.add(createMenuItemResume());
            box.add(createMenuItemExitMainMenu());
        }

        return box;
    }

    private boolean isMainMenu(MenuType menuType) {
        return menuType == MenuType.MAIN_MENU;
    }

    private MenuBox createMultiplayerMenu() {
        return new MenuBox(
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
        final MenuButton exitMenuButton = new MenuButton("menu.exit");
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
        creditsMenuButton.setMenuContent(this::createContentCredits, this);
        return creditsMenuButton;
    }

    private MenuButton createMenuItemExitMainMenu() {
        final MenuButton exitMainMenuButton = new MenuButton("menu.mainMenu");
        exitMainMenuButton.setOnAction(event -> fireExitToMainMenu());
        return exitMainMenuButton;
    }

    private MenuButton createMenuItemMultiplayerConnect() {
        final MenuButton feedbackMenuButton = new MenuButton("multiplayer.connect");
        feedbackMenuButton.setMenuContent(this::createMultiplayerConnect, this);
        return feedbackMenuButton;
    }

    private MenuButton createMenuItemMultiplayerHost() {
        final MenuButton feedbackMenuButton = new MenuButton("multiplayer.host");
        feedbackMenuButton.setMenuContent(this::createMultiplayerHost, this);
        return feedbackMenuButton;
    }

    private MenuContent createMultiplayerConnect() {
        // TODO: IP input feld einfügen
        final MenuContent connectMenuContent = new MenuContent();

        /* TODO: needed?
        connectMenuContent.setOnOpen();
        connectMenuContent.setOnClose();
        */
        System.out.println("created multiplayer connect");
        return connectMenuContent;
    }

    private MenuContent createMultiplayerHost() {
        return new MenuContent();
    }

    private MenuContent createMultiplayerOptions() {
        return new MenuContent();
    }
}



