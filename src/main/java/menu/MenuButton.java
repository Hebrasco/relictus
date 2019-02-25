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
import utils.AudioPlayer;
import utils.PropertiesLoader;

import java.util.function.Supplier;

/**
 * @author Daniel Bedrich
 */
class MenuButton extends Pane {
    final FXGLButton button = new FXGLButton();
    private MenuRoot parent = null;
    private MenuItem cachedContent = new MenuItem();

    MenuButton(String key) {

        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent");

        addText(key);
        addClickEvents();

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

    void setMenuContent(Supplier<MenuItem> contentSupplier, RelictusMenu relictusMenu) {
        button.addEventHandler(ActionEvent.ACTION, event -> {
            if (cachedContent == null) {
                cachedContent = contentSupplier.get();
            }

            relictusMenu.switchMenuContentTo(cachedContent);
        });
    }

    void setChild(MenuRoot menuRoot, RelictusMenu relictusMenu) {
        final MenuButton back = new MenuButton("menu.back");
        menuRoot.getChildren().add(0, back);

        back.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(parent));
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(menuRoot));
    }

    private void addClickEvents() {
        String soundMenuPressFilePath = FXGL.getSettings().getSoundMenuPress();
        String soundMenuMoveFilePath = FXGL.getSettings().getSoundMenuSelect();

        button.setOnMousePressed(e -> AudioPlayer.play(soundMenuPressFilePath));
        button.setOnMouseEntered(e -> AudioPlayer.play(soundMenuMoveFilePath));
        // TODO: menu_move.wav beim bewegen mit den Pfeiltasten abspielen (Zurseit wird der menu_klick.wav abgespielt)
        button.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                AudioPlayer.play(soundMenuPressFilePath);
                button.fire();
            } else {
                AudioPlayer.play(soundMenuMoveFilePath);
            }
        });
    }

    private void addText(String key) {
        StringBinding bindings = Bindings.createStringBinding(() -> PropertiesLoader.getResourceProperties(key));
        button.textProperty().bind(bindings);
    }
}
