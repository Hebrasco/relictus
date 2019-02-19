package menu;

import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 */
class MenuBox extends VBox {
    MenuBox(MenuButton... items) {
        for (MenuButton item : items) {
            add(item);
        }
    }

    void add(MenuButton item) {
        item.setParent(this);
        getChildren().addAll(item);
    }
}
