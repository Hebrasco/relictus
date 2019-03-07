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
 * @author Daniel Bedrich
 */
class MenuItem extends Pane {
    final FXGLButton button = new FXGLButton();
    private MenuRoot parent = null;

    MenuItem(String key) {
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent");
        button.setPrefWidth(getTextWidth(key));

        addText(key);
        overrideKeyPressedEvents();

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
        final MenuItem backButton = new MenuItem(MenuKeys.BACK);
        menuRoot.getChildren().add(menuRoot.getChildren().size(), backButton);

        backButton.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(parent));
        button.addEventHandler(ActionEvent.ACTION, event -> relictusMenu.switchMenuTo(menuRoot));
    }

    private void overrideKeyPressedEvents() {
        button.setOnKeyPressed(e -> {/*DO NOTHING*/});
        button.setOnMouseClicked(e -> FXGL.getAudioPlayer().playSound(FXGL.getSettings().getSoundMenuPress()));
        button.setOnMouseEntered(e -> FXGL.getAudioPlayer().playSound(FXGL.getSettings().getSoundMenuSelect()));
    }

    private void addText(String key) {
        final StringBinding bindings = Bindings.createStringBinding(() -> PropertiesLoader.getResourceProperties(key));
        button.textProperty().bind(bindings);
    }

    private double getTextWidth(String text) {
        final Text textObject = new Text(text);
        return textObject.getLayoutBounds().getWidth() * 1.75;
    }
}
