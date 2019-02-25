package menu;

import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 */
class MenuRoot extends VBox {
    MenuRoot(MenuButton... items) {
        for (MenuButton item : items) {
            add(item);
        }
    }

    void add(MenuButton item) {
        item.setParent(this);
        getChildren().addAll(item);
    }
}
