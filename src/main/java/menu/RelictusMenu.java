package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.local.Local;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.MenuType;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.animation.FadeTransition;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.util.EnumSet;
import java.util.function.Supplier;

public class RelictusMenu extends FXGLMenu {

    public RelictusMenu(@NotNull MenuType type) {
        super(type);
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
    protected Node createBackground(double v, double v1) {
        return null;
    }

    @NotNull
    @Override
    protected Node createProfileView(@NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    protected Node createTitleView(@NotNull String s) {
        return null;
    }

    @NotNull
    @Override
    protected Node createVersionView(@NotNull String s) {
        return null;
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

    private MenuBox createOptionsMenu() {

        final MenuButton itemGameplay = new MenuButton("menu.gameplay");
        itemGameplay.setMenuContent((Supplier<MenuContent>) () -> createContentGameplay());

        final MenuButton itemControls = new MenuButton("menu.controls");
        itemControls.setMenuContent((Supplier<MenuContent>) () -> createContentControls());

        final MenuButton itemVideo = new  MenuButton("menu.video");
        itemVideo.setMenuContent((Supplier<MenuContent>) () -> createContentVideo());

        final MenuButton itemAudio = new MenuButton("menu.audio");
        itemAudio.setMenuContent((Supplier<MenuContent>) () -> createContentAudio());

        final MenuButton btnRestore = new MenuButton("menu.restore");
        btnRestore.setOnAction(event -> FXGL.getDisplay().showConfirmationBox(Local.getLocalizedString("menu.settingsRestore"), arg -> {
            if (arg) {
                switchMenuContentTo(getMenuContentRoot());
            }
        }));

        return new MenuBox(itemGameplay, itemControls, itemVideo, itemAudio, btnRestore);
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
        private final Shape p = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);
        final FXGLButton btn = new FXGLButton();

        MenuButton(String key) {
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-background-color: transparent");
            btn.textProperty().bind(Local.localizedStringProperty(key));

            p.setMouseTransparent(true);

            final LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                    new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)),
                    new Stop(0.85, Color.color(1.0, 0.8, 0.0, 074)),
                    new Stop(1.0, Color.WHITE));
        }

        /* TODO: Zu java übersetzen
         p.fillProperty().bind(
                    Bindings.`when`(btn.pressedProperty()).then(Color.color(1.0, 0.8, 0.0, 0.75) as Paint).otherwise(g)
            )

            p.stroke = Color.color(0.1, 0.1, 0.1, 0.15)
            p.effect = GaussianBlur()

            p.visibleProperty().bind(btn.hoverProperty())

            children.addAll(btn, p)
        */
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



