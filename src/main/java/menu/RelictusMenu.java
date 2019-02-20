package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.local.Local;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.MenuType;
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
import java.util.EnumSet;

/**
 * @author Daniel Bedrich
 */
public class RelictusMenu extends FXGLMenu {
    private VBox emptyVBox = new MenuContent();

    public RelictusMenu(@NotNull MenuType type) {
        super(type);
        inflateMenu(type);
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
        return new Rectangle(width, height, Color.BLACK);
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
        Text titleText = FXGL.getUIFactory().newText(title, 50.0);
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

    private void inflateMenu(MenuType menuType) {
        final MenuBox menu;

        if (menuType == MenuType.MAIN_MENU)
            menu = createMainMenu();
        else {
            menu = createInGameMenu();
        }

        final double menuX = 50.0;
        final double menuY = FXGL.getAppHeight() / 2.0 - menu.getLayoutBounds().getHeight() / 2.0;

        getMenuRoot().setTranslateX(menuX);
        getMenuRoot().setTranslateY(menuY);

        getMenuContentRoot().setTranslateX((FXGL.getAppWidth() - 500));
        getMenuContentRoot().setTranslateY(menuY);

        getMenuRoot().getChildren().addAll(menu);
        getMenuContentRoot().getChildren().add(emptyVBox);

        activeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                switchMenuTo(menu);
                switchMenuContentTo(emptyVBox);
            }
        });
    }

    private MenuBox createMainMenu() {
        final MenuBox box = new MenuBox();
        final EnumSet<MenuItem> enabledItems = FXGL.getSettings().getEnabledMenuItems();

        box.add(createMenuItemNewGame());
        box.add(createMenuItemOptions());

        if (enabledItems.contains(MenuItem.EXTRA)) {
            box.add(createGameMenuItemExtra());
        }

        box.add(createMenuItemExit());

        return box;
    }

    private MenuBox createInGameMenu() {
        final MenuBox box = new MenuBox();
        final EnumSet<MenuItem> enabledItems = FXGL.getSettings().getEnabledMenuItems();

        box.add(createGameMenuItemResume());

        if (enabledItems.contains(MenuItem.SAVE_LOAD)) {
            box.add(createGameMenuItemSave());
            box.add(createGameMenuItemLoad());
        }

        box.add(createGameMenuItemOptions());

        if (enabledItems.contains(MenuItem.EXTRA)) {
            box.add(createGameMenuItemExtra());
        }

        box.add(createGameMenuItemExit());

        return box;
    }

    private MenuBox createOptionsMenu() {
        return new MenuBox(
                createMenuItemOptionsMenuGameplay(),
                createMenuItemOptionsMenuControls(),
                createMenuItemOptionsMenuVideo(),
                createMenuItemOptionsMenuAudio(),
                createMenuItemOptionsMenuRestore()
        );
    }

    private MenuBox createExtraMenu() {
        return new MenuBox(
                createMenuItemExtraMenuTrophies(),
                createMenuItemExtraMenuCredits(),
                createMenuItemExtraMenuFeedback()
        );
    }

    private MenuButton createMenuItemNewGame() {
        final MenuButton newGameMenuButton = new MenuButton("menu.newGame");
        newGameMenuButton.setOnAction(event -> fireNewGame());
        return newGameMenuButton;
    }

    private MenuButton createMenuItemOptions() {
        final MenuButton optionsMenuButton = new MenuButton("menu.options");
        optionsMenuButton.setOnAction(event -> optionsMenuButton.setChild(createOptionsMenu(), this));
        return optionsMenuButton;
    }

    private MenuButton createMenuItemExit() {
        final MenuButton exitMenuButton = new MenuButton("menu.exit");
        exitMenuButton.setOnAction(event -> fireExit());
        return exitMenuButton;
    }

    private MenuButton createGameMenuItemResume() {
        final MenuButton resumeMenuButton = new MenuButton("menu.resume");
        resumeMenuButton.setOnAction(event -> fireResume());
        return resumeMenuButton;
    }

    private MenuButton createGameMenuItemSave() {
        final MenuButton saveMenuButton = new MenuButton("menu.save");
        saveMenuButton.setOnAction(event -> fireSave());
        return saveMenuButton;
    }

    private MenuButton createGameMenuItemLoad() {
        final MenuButton loadMenuButton = new MenuButton("menu.load");
        loadMenuButton.setMenuContent(this::createContentLoad, this);
        return loadMenuButton;
    }

    private MenuButton createGameMenuItemOptions() {
        final MenuButton optionsMenuButton = new MenuButton("menu.options");
        optionsMenuButton.setChild(createOptionsMenu(), this);
        return optionsMenuButton;
    }

    private MenuButton createGameMenuItemExtra() {
        final MenuButton extraMenuButton = new MenuButton("menu.extra");
        extraMenuButton.setChild(createExtraMenu(), this);
        return extraMenuButton;
    }

    private MenuButton createGameMenuItemExit() {
        final MenuButton exitMainMenuButton = new MenuButton("menu.mainMenu");
        exitMainMenuButton.setOnAction(event -> fireExitToMainMenu());
        return exitMainMenuButton;
    }

    private MenuButton createMenuItemOptionsMenuGameplay() {
        final MenuButton gameplayMenuButton = new MenuButton("menu.gameplay");
        gameplayMenuButton.setMenuContent(this::createContentGameplay, this);
        return gameplayMenuButton;
    }

    private MenuButton createMenuItemOptionsMenuControls() {
        final MenuButton controlsMenuButton = new MenuButton("menu.controls");
        controlsMenuButton.setMenuContent(this::createContentControls, this);
        return controlsMenuButton;
    }

    private MenuButton createMenuItemOptionsMenuVideo() {
        final MenuButton videoMenuButton = new MenuButton("menu.video");
        videoMenuButton.setMenuContent(this::createContentVideo, this);
        return videoMenuButton;
    }

    private MenuButton createMenuItemOptionsMenuAudio() {
        final MenuButton audioMenuButton = new MenuButton("menu.audio");
        audioMenuButton.setMenuContent(this::createContentAudio, this);
        return audioMenuButton;
    }

    private MenuButton createMenuItemOptionsMenuRestore() {
        final MenuButton restoreMenuButton = new MenuButton("menu.restore");
        restoreMenuButton.setOnAction(event -> FXGL.getDisplay().showConfirmationBox(Local.getLocalizedString("menu.settingsRestore"), arg -> {
            if (arg) {
                switchMenuContentTo(emptyVBox);
            }
        }));
        return restoreMenuButton;
    }

    private MenuButton createMenuItemExtraMenuTrophies() {
        final MenuButton trophiesMenuButton = new MenuButton("menu.trophies");
        trophiesMenuButton.setMenuContent(this::createContentAchievements, this);
        return trophiesMenuButton;
    }

    private MenuButton createMenuItemExtraMenuCredits() {
        final MenuButton creditsMenuButton = new MenuButton("menu.credits");
        creditsMenuButton.setMenuContent(this::createContentCredits, this);
        return creditsMenuButton;
    }

    private MenuButton createMenuItemExtraMenuFeedback() {
        final MenuButton feedbackMenuButton = new MenuButton("menu.feedback");
        feedbackMenuButton.setMenuContent(this::createContentFeedback, this);
        return feedbackMenuButton;
    }
}



