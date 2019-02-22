package menu;

import com.almasb.fxgl.app.FXGLMenu;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FXGLButton;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import utils.AudioPlayer;
import utils.PropertiesLoader;
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

        addText(key);
        addClickSoundEffects();

        // TODO: Butten größe an Textbreite anpassen
        button.setMinWidth(300);

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

    private void addClickSoundEffects() {
        AudioPlayer audioPlayer = AudioPlayer.getInstance();
        String soundMenuPressFilePath = FXGL.getSettings().getSoundMenuPress();
        String soundMenuMoveFilePath = FXGL.getSettings().getSoundMenuSelect();

        button.setOnMousePressed(e -> audioPlayer.play(soundMenuPressFilePath));
        button.setOnMouseEntered(e -> audioPlayer.play(soundMenuMoveFilePath));
        // TODO: menu_move.wav beim bewegen mit den Pfeiltasten abspielen (Zurseit wird der menu_klick.wav abgespielt)
        button.setOnKeyPressed(e -> audioPlayer.play(soundMenuPressFilePath));
    }

    private void addText(String key) {
        StringBinding bindings = Bindings.createStringBinding(() -> PropertiesLoader.getInstance().getResourceProperties(key));
        button.textProperty().bind(bindings);
    }
}
