package menu;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.ui.FXGLButton;
import data.MenuKeys;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.PropertiesLoader;

import java.util.function.Supplier;

/**
 * Custom Button to handle the menu input.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
class MenuButton extends Pane {
    final FXGLButton button = new FXGLButton();
    private MenuRoot parent = null;

    MenuButton(String key) {
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent");
        button.setPrefWidth(getTextWidth(key));

        addText(key);
        overrideKeyPressedEvents();

        getChildren().add(button);
    }

    /**
     * Sets the action to the {@link FXGLButton}.
     * @param e the action.
     */
    void setOnAction(EventHandler<ActionEvent> e) {
        button.setOnAction(e);
    }

    /**
     * Sets the parent to the passed {@link MenuRoot}.
     * @param root the parent {@link MenuRoot}.
     */
    void setParent(MenuRoot root) {
        parent = root;
    }

    /**
     * Adds a event, when button was clicked.
     * @param contentSupplier the {@link VBox} to switch content to.
     * @param relictusMenu the {@link RelictusMenu} to call switchMenuContentTo from.
     */
    void setMenuContent(Supplier<VBox> contentSupplier, RelictusMenu relictusMenu) {
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuContentTo(contentSupplier.get()));
    }

    /**
     * Sets sub menu, button click events and adds back button.
     * @param menuRoot {@link MenuRoot} object of the menu.
     * @param relictusMenu the {@link RelictusMenu}.
     */
    void setChild(MenuRoot menuRoot, RelictusMenu relictusMenu) {
        final MenuButton backButton = new MenuButton(MenuKeys.BACK);
        menuRoot.getChildren().add(menuRoot.getChildren().size(), backButton);

        backButton.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(parent));
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(menuRoot));
    }

    /**
     * Overrides the key pressed events and adds the menu sounds.
     */
    private void overrideKeyPressedEvents() {
        button.setOnKeyPressed(e -> {/*DO NOTHING*/});
        button.setOnMouseClicked(e -> FXGL.getAudioPlayer().playSound(FXGL.getSettings().getSoundMenuPress()));
        button.setOnMouseEntered(e -> FXGL.getAudioPlayer().playSound(FXGL.getSettings().getSoundMenuSelect()));
    }

    /**
     * Adds the loaded properties text to this button.
     * @param key the key where to find the text.
     */
    private void addText(String key) {
        final StringBinding bindings = Bindings.createStringBinding(() -> PropertiesLoader.getResourceProperties(key));
        button.textProperty().bind(bindings);
    }

    /**
     * @param text the text to get the length from.
     * @return the width of the text.
     */
    private double getTextWidth(String text) {
        final Text textObject = new Text(text);
        return textObject.getLayoutBounds().getWidth() * 1.75;
    }
}
