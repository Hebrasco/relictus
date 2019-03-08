package game.components;

import com.almasb.fxgl.entity.component.Component;
import game.player.PlayerControl;

/**
 * Adds all needed components to the player component and
 * contains general information about the player.
 *
 * @author Daniel Bedrich, Lara-Marie Mann
 * @version 1.0
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
