package game.components;

import com.almasb.fxgl.entity.component.Component;
import game.player.PlayerControl;

/**
 * @author Daniel Bedrich
 */
public class PlayerComponent extends Component {
    public double playerWidth = 32;
    public double playerHeight = 42;

    @Override
    public void onAdded() {
        entity.addComponent(new ColliderComponent());
        entity.addComponent(new PhysicsComponent());
        entity.addComponent(new PlayerControl());
    }
}
