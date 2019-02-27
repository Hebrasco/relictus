package game.player;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * @author Kevin Ortmeier, Daniel Bedrich
 */
public class PlayerControl extends Component {
    private final double speed = 25;
    private final double jumpVelocity = 20;
    private final UserAction userActionJump = createMovementAction("Jump", Direction.UP, jumpVelocity);
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private PhysicsComponent physics;
    private PositionComponent position;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    public PlayerControl() {
        animIdle = new AnimationChannel("player.png", 4, 32, 42, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel("player.png", 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        //texture.playAnimationChannel(physics.isMoving() ? animWalk : animIdle);
    }

    // TODO: isMoving()
    // TODO: isGrounded()

    public void createInput(Input input) {
        input.addAction(userActionLeft, KeyCode.A);
        input.addAction(userActionRight, KeyCode.D);
        input.addAction(userActionJump, KeyCode.W);
    }

    private void move(Direction direction, double speed) {
        position.translate(direction.vector.multiply(speed));
    }

    private UserAction createMovementAction(String name, Direction direction, double speed) {
        return new  UserAction(name) {
            @Override
            protected void onAction() {
                move(direction, speed);
            }
        };
    }
}
