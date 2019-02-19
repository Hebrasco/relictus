package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.core.local.Local;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Polygon;
import java.util.function.Supplier;

/**
 * @author Daniel Bedrich
 */
class MenuButton extends Pane {
    final FXGLButton button = new FXGLButton();
    private MenuBox parent = null;
    private FXGLMenu.MenuContent cachedContent = null;

    MenuButton(String key) {
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent");
        button.textProperty().bind(Local.localizedStringProperty(key));

        getChildren().addAll(button, createBackgroundPolygon());
    }

    void setOnAction(EventHandler<ActionEvent> e) {
        button.setOnAction(e);
    }

    void setParent(MenuBox menuBox) {
        parent = menuBox;
    }

    void setMenuContent(Supplier<FXGLMenu.MenuContent> contentSupplier, RelictusMenu relictusMenu) {
        button.addEventHandler(ActionEvent.ACTION, event -> {
            if (cachedContent == null) {
                cachedContent = contentSupplier.get();
            }
            relictusMenu.switchMenuContentTo(cachedContent);
        });
    }

    void setChild(MenuBox menuBox, RelictusMenu relictusMenu) {
        final MenuButton back = new MenuButton("menu.back");
        menuBox.getChildren().add(0, back);

        back.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(parent));
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(menuBox));
    }

    private Polygon createBackgroundPolygon() {
        final LinearGradient hoverGradient = createHoverGradient();
        final Polygon backgroundPolygon = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);

        backgroundPolygon.setMouseTransparent(true);
        backgroundPolygon.fillProperty().bind(
                Bindings.when(button.pressedProperty()).then(createPressedEffect()).otherwise(hoverGradient)
        );
        backgroundPolygon.setStroke(Color.color(0.1, 0.1, 0.1, 0.15));
        backgroundPolygon.setEffect(new GaussianBlur());
        backgroundPolygon.visibleProperty().bind(button.hoverProperty());

        return backgroundPolygon;
    }

    private LinearGradient createHoverGradient() {
        return new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)),
                new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
                new Stop(1.0, Color.WHITE));
    }

    private Paint createPressedEffect() {
        return Color.color(1.0, 0.8, 0.0, 0.75);
    }
}
