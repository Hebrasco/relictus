package menu;

import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 * @version 1.0
 */
class MenuRoot extends VBox {
    MenuRoot(MenuItem... items) {
        for (MenuItem item : items) {
            add(item);
        }
    }

    /**
     * Adds the {@link MenuItem} to its Children
     * @param item
     */
    void add(MenuItem item) {
        item.setParent(this);
        getChildren().addAll(item);
    }
}
