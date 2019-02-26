package menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.PropertiesLoader;

import java.util.function.Supplier;

/**
 * @author Daniel Bedrich
 */
class MenuButton extends Pane {
    final FXGLButton button = new FXGLButton();
    private MenuRoot parent = null;

    MenuButton(String key) {
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent");

        addText(key);
        overrideKeyPressedEvents();

        // TODO: Button größe an Textbreite anpassen
        button.setMinWidth(250);

        getChildren().add(button);
    }

    void setOnAction(EventHandler<ActionEvent> e) {
        button.setOnAction(e);
    }

    void setParent(MenuRoot root) {
        parent = root;
    }

    void setMenuContent(Supplier<VBox> contentSupplier, RelictusMenu relictusMenu) {
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuContentTo(contentSupplier.get()));
    }

    void setChild(MenuRoot menuRoot, RelictusMenu relictusMenu) {
        final MenuButton backButton = new MenuButton("menu.back");
        menuRoot.getChildren().add(menuRoot.getChildren().size(), backButton);

        backButton.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(parent));
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(menuRoot));
    }

    private void overrideKeyPressedEvents() {
        String soundMenuPressFilePath = FXGL.getSettings().getSoundMenuPress();
        String soundMenuMoveFilePath = FXGL.getSettings().getSoundMenuSelect();

        button.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                FXGL.getAudioPlayer().playSound(soundMenuPressFilePath);
                button.fire();
            } else {
                FXGL.getAudioPlayer().playSound(soundMenuMoveFilePath);
            }
        });
    }

    private void addText(String key) {
        StringBinding bindings = Bindings.createStringBinding(() -> PropertiesLoader.getResourceProperties(key));
        button.textProperty().bind(bindings);
    }
}
