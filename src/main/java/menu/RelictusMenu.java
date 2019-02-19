package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.local.Local;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.MenuType;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.util.EnumSet;
import java.util.function.Supplier;

public class RelictusMenu extends FXGLMenu {
    private VBox emptyVBox = new MenuContent();

    public RelictusMenu(@NotNull MenuType type) {
        super(type);
        createMainMenu();

        final MenuBox menu;

        if (type == MenuType.MAIN_MENU)
            menu = createMainMenu();
        else {
            menu = createMenuBodyGameMenu();
        }

        final double menuX = 50.0;
        final double menuY = FXGL.getAppHeight() / 2 - menu.getLayoutBounds().getHeight() / 2;

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

    @NotNull
    @Override
    protected Button createActionButton(@NotNull StringBinding stringBinding, @NotNull Runnable runnable) {
        final MenuButton btn = new MenuButton(stringBinding.getValue());
        btn.addEventHandler(ActionEvent.ACTION, event -> runnable.run());

        return btn.btn;
    }

    @NotNull
    @Override
    protected Button createActionButton(@NotNull String s, @NotNull Runnable runnable) {
        final MenuButton btn = new MenuButton(s);
        btn.addEventHandler(ActionEvent.ACTION, event -> runnable.run());

        return btn.btn;
    }

    @NotNull
    @Override
    protected Node createBackground(double width, double height) {
        Rectangle background = new Rectangle(width, height, Color.BLACK);
        return background;
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String profileName) {
        final Text view = FXGL.getUIFactory().newText(profileName);
        view.setTranslateY((FXGL.getAppHeight() - 2));
        view.setTranslateX(FXGL.getAppWidth() - view.getLayoutBounds().getWidth());
        return view;
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String title) {
        final SimpleObjectProperty<Color> titleColor = new SimpleObjectProperty<>(Color.WHITE);

        final Text text = FXGL.getUIFactory().newText(title.substring(0, 1), 50.0);
        text.setFill(null);
        text.strokeProperty().bind(titleColor);
        text.setStrokeWidth(1.5);

        final Text text2 = FXGL.getUIFactory().newText(title.substring(1), 50.0);
        text2.setFill(null);
        text2.setStroke(titleColor.getValue());
        text2.setStrokeWidth(1.5);

        final double textWidth = text.getLayoutBounds().getWidth() + text2.getLayoutBounds().getWidth();

        final Rectangle bg = new Rectangle(textWidth + 30, 65.0, null);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(4.0);
        bg.setArcWidth(25.0);
        bg.setArcHeight(25.0);

        final HBox box = new HBox(text, text2);
        box.setAlignment(Pos.CENTER);

        final StackPane titleRoot = new StackPane();
        titleRoot.getChildren().addAll(bg, box);

        titleRoot.setTranslateX(FXGL.getAppWidth() / 2 - (textWidth + 30) / 2);
        titleRoot.setTranslateY(50);

        return titleRoot;
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String version) {
        final Text view = FXGL.getUIFactory().newText(version);
        view.setTranslateY((FXGL.getAppHeight() - 2));
        return view;
    }

    private MenuBox createMainMenu() {
        final MenuBox box = new MenuBox();
        final EnumSet<MenuItem> enableditems = FXGL.getSettings().getEnabledMenuItems();

        final MenuButton itemNewGame = new MenuButton("menu.newGame");
        itemNewGame.setOnAction(event -> fireNewGame());
        box.add(itemNewGame);

        final MenuButton itemOptions = new MenuButton("menu.options");
        itemOptions.setOnAction(event -> itemOptions.setChild(createOptionsMenu()));
        box.add(itemOptions);

        final MenuButton itemExit = new MenuButton("menu.exit");
        itemExit.setOnAction(event -> fireExit());
        box.add(itemExit);

        final MenuButton itemTest = new MenuButton("menu.test");
        itemTest.setOnAction(event -> System.out.println("TEST!!"));
        box.add(itemTest);

        return box;
    }

    private MenuBox createMenuBodyGameMenu() {
        final MenuBox box = new MenuBox();

        final EnumSet<MenuItem> enabledItems = FXGL.getSettings().getEnabledMenuItems();

        final MenuButton itemResume = new MenuButton("menu.resume");
        itemResume.setOnAction(event -> fireResume());
        box.add(itemResume);

        if (enabledItems.contains(MenuItem.SAVE_LOAD)) {
            final MenuButton itemSave = new MenuButton("menu.save");
            itemSave.setOnAction(event -> fireSave());

            final MenuButton itemLoad = new MenuButton("menu.load");
            itemLoad.setMenuContent(this::createContentLoad);

            box.add(itemSave);
            box.add(itemLoad);
        }

        final MenuButton itemOptions = new MenuButton("menu.options");
        itemOptions.setChild(createOptionsMenu());
        box.add(itemOptions);

        if (enabledItems.contains(MenuItem.EXTRA)) {
            final MenuButton itemExtra = new MenuButton("menu.extra");
            itemExtra.setChild(createExtraMenu());
            box.add(itemExtra);
        }

        final MenuButton itemExit = new MenuButton("menu.mainMenu");
        itemExit.setOnAction(event -> fireExitToMainMenu());
        box.add(itemExit);

        return box;
    }

    private MenuBox createOptionsMenu() {

        final MenuButton itemGameplay = new MenuButton("menu.gameplay");
        itemGameplay.setMenuContent(this::createContentGameplay);

        final MenuButton itemControls = new MenuButton("menu.controls");
        itemControls.setMenuContent(this::createContentControls);

        final MenuButton itemVideo = new  MenuButton("menu.video");
        itemVideo.setMenuContent(this::createContentVideo);

        final MenuButton itemAudio = new MenuButton("menu.audio");
        itemAudio.setMenuContent(this::createContentAudio);

        final MenuButton btnRestore = new MenuButton("menu.restore");
        btnRestore.setOnAction(event -> FXGL.getDisplay().showConfirmationBox(Local.getLocalizedString("menu.settingsRestore"), arg -> {
            if (arg) {
                switchMenuContentTo(emptyVBox);
            }
        }));

        return new MenuBox(itemGameplay, itemControls, itemVideo, itemAudio, btnRestore);
    }

    private MenuBox createExtraMenu() {
        final MenuButton itemAchievements = new MenuButton("menu.trophies");
        itemAchievements.setMenuContent(this::createContentAchievements);

        final MenuButton itemCredits = new MenuButton("menu.credits");
        itemCredits.setMenuContent(this::createContentCredits);

        final MenuButton itemFeedback = new MenuButton("menu.feedback");
        itemFeedback.setMenuContent(this::createContentFeedback);

        return new MenuBox(itemAchievements, itemCredits, itemFeedback);
    }

    @Override
    protected void switchMenuTo(@NotNull Node menuBox) {
        final Node oldMenu = getMenuRoot().getChildren().get(0);

        final FadeTransition ft = new FadeTransition(Duration.seconds(0.33), oldMenu);
        ft.setToValue(0.0);
        ft.setOnFinished(event -> {
            menuBox.setOpacity(0.0);
            getMenuRoot().getChildren().set(0, menuBox);
            oldMenu.setOpacity(1.0);

            final FadeTransition ft2 = new FadeTransition(Duration.seconds(0.33), menuBox);
            ft2.setToValue(1.0);
            ft2.play();
        });
        ft.play();
    }

    @Override
    protected void switchMenuContentTo(@NotNull Node content) {
        getMenuContentRoot().getChildren().set(0, content);
    }

    class MenuButton extends Pane {
        private MenuBox parent = null;
        private MenuContent cachedContent = null;
        private final Polygon polygon = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);
        private final FXGLButton btn = new FXGLButton();

        MenuButton(String key) {
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-background-color: transparent");
            btn.textProperty().bind(Local.localizedStringProperty(key));

            polygon.setMouseTransparent(true);

            final LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                    new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)),
                    new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
                    new Stop(1.0, Color.WHITE));

            polygon.fillProperty().bind(
                    Bindings.when(btn.pressedProperty()).then((Paint)Color.color(1.0, 0.8, 0.0, 0.75)).otherwise(g)
            );

            polygon.setStroke(Color.color(0.1, 0.1, 0.1, 0.15));
            polygon.setEffect(new GaussianBlur());

            polygon.visibleProperty().bind(btn.hoverProperty());

            getChildren().addAll(btn, polygon);
        }



        void setOnAction(EventHandler<ActionEvent> e) {
            btn.setOnAction(e);
        }

        void setParent(MenuBox menuBox) {
            parent = menuBox;
        }

        void  setMenuContent(Supplier<MenuContent> contentSupplier) {
            btn.addEventHandler(ActionEvent.ACTION, event -> {
                if (cachedContent == null) {
                    cachedContent = contentSupplier.get();
                }
                switchMenuContentTo(cachedContent);
            });
        }

        void setChild(MenuBox menuBox) {
            final MenuButton back = new MenuButton("menu.back");
            menuBox.getChildren().add(0, back);

            back.addEventHandler(ActionEvent.ACTION, event -> switchMenuTo(parent)) ;
            btn.addEventHandler(ActionEvent.ACTION, event -> switchMenuTo(menuBox));
        }
    }

    class MenuBox extends VBox {
        MenuBox(MenuButton... items) {
            for (MenuButton item : items) {
                add(item);
            }
        }

        void add(MenuButton item) {
            item.setParent(this);
            getChildren().addAll(item);
        }
    }
}



