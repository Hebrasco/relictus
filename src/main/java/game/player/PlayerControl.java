package game.player;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import game.components.ColliderComponent;
import game.components.PhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * @author Kevin Ortmeier, Daniel Bedrich
 */
public class PlayerControl extends Component {
    private final double speed = 15;
    private final double jumpVelocity = 18;
    private final UserAction userActionJump = createMovementAction("Jump", Direction.UP, jumpVelocity);
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private final UserAction userActionDown = createMovementAction("Down", Direction.DOWN, speed);
    private PhysicsComponent physicsComponent;
    private PositionComponent positionComponent;
    private ColliderComponent colliderComponent;
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

        physicsComponent = entity.getComponent(PhysicsComponent.class);
        if (physicsComponent == null) {
            entity.addComponent(new PhysicsComponent());
        }
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
        input.addAction(userActionDown, KeyCode.S);
    }

    private void move(Direction direction, double speed) {
        final Point2D targetVector = new Point2D(
                (entity.getPosition().getX() + direction.vector.multiply(speed).getX()),
                (entity.getPosition().getY() + direction.vector.multiply(speed).getY())
        );
        if (!physicsComponent.isCollided(targetVector)) {
            positionComponent.translate(direction.vector.multiply(speed));
        } else {
            System.out.println("Entity collided");
        }
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
