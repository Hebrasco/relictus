package menu;

import javafx.scene.layout.VBox;

/**
 * @author Daniel Bedrich
 */
class MenuRoot extends VBox {
    MenuRoot(MenuItem... items) {
        for (MenuItem item : items) {
            add(item);
        }
    }

    void add(MenuItem item) {
        item.setParent(this);
        getChildren().addAll(item);
    }
}
