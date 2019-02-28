package game.components;

import com.almasb.fxgl.entity.component.Component;

import java.awt.*;

/**
 * @author Daniel Bedrich
 */
public class ColliderComponent extends Component {
    public boolean isCollidable;

    public ColliderComponent(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }
}
