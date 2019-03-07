package menu;

import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 * @version 1.0
 */
class MenuRoot extends VBox {
    MenuRoot(MenuButton... items) {
        for (MenuButton item : items) {
            add(item);
        }
    }

    /**
     * Adds the {@link MenuButton} to its Children
     * @param item
     */
    void add(MenuButton item) {
        item.setParent(this);
        getChildren().addAll(item);
    }
}
