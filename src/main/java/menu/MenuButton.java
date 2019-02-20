package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.core.local.Local;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
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

        getChildren().add(button);
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
}
