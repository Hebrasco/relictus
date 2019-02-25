package menu;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 */
class MenuItem extends VBox {
    MenuItem(Node... items) {

        if (items.length > 0) {
            int maxWidth = (int) items[0].getLayoutBounds().getWidth();

            for (Node n : items) {
                int width = (int) n.getLayoutBounds().getWidth();
                if (width > maxWidth)
                    maxWidth = width;
            }

            for (Node item : items) {
                getChildren().addAll(item);
            }
        }

        sceneProperty().addListener((o, oldScene, newScene) -> {
            if (newScene != null) {
                onOpen();
            } else {
                onClose();
            }
        });
    }

    private Runnable onOpen = null;
    private Runnable onClose = null;

    public void setOnOpen(Runnable onOpenAction) {
        this.onOpen = onOpenAction;
    }

    public void setOnClose(Runnable onCloseAction) {
        this.onClose = onCloseAction;
    }

    private void onOpen() {
        if (onOpen != null)
            onOpen.run();
    }

    private void onClose() {
        if (onClose != null)
            onClose.run();
    }
}
