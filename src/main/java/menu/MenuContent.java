package menu;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

class MenuContent extends VBox {
    MenuContent(Node... items) {

        if (items.length > 0) {
            int maxW = (int) items[0].getLayoutBounds().getWidth();

            for (Node n : items) {
                int w = (int) n.getLayoutBounds().getWidth();
                if (w > maxW)
                    maxW = w;
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

    /**
     * Set on open handler.
     *
     * @param onOpenAction method to be called when content opens
     */
    public void setOnOpen(Runnable onOpenAction) {
        this.onOpen = onOpenAction;
    }

    /**
     * Set on close handler.
     *
     * @param onCloseAction method to be called when content closes
     */
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
