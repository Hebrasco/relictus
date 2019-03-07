package game.player;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import game.components.ColliderComponent;
import game.components.PlayerPhysicsComponent;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import preferences.GamePreferences;

/**
 * @author Kevin Ortmeier, Daniel Bedrich
 */
public class PlayerControl extends Component {
    private final double speed = 7.5;
    private final UserAction userActionJump = createJumpAction();
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private ColliderComponent colliderComponent;
    private PlayerPhysicsComponent physicComponent;
    private PositionComponent positionComponent;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    @Override
    public void onAdded() {
        animIdle = new AnimationChannel(GamePreferences.PLAYER_FILE_NAME, 4, (int) getPlayerWidth(), (int) getPlayerHeight(), Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(GamePreferences.PLAYER_FILE_NAME, 4, (int) getPlayerWidth(), (int) getPlayerHeight(), Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);

        entity.setView(texture);

        colliderComponent = entity.getComponent(ColliderComponent.class);
        physicComponent = entity.getComponent(PlayerPhysicsComponent.class);
        positionComponent = entity.getPositionComponent();
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

    private UserAction createMovementAction(String name, Direction direction, double speed) {
        return new UserAction(name) {
            @Override
            protected void onAction() {
                move(direction, speed);
            }
        };
    }

    // TODO: Wenn im Spingen, richtung merken und input sperren
    private UserAction createJumpAction() {
        return new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                if (!isEntityJump()) {
                    move( Direction.UP, speed);
                }
            }
        };
    }

    private void move(Direction direction, double speed) {
        final Point2D vector = direction.vector.multiply(speed);
        final Point2D targetVector = getTargetVector(vector);

        if (!colliderComponent.isCollided(targetVector)) {
            if (direction.equals(Direction.UP)) {
                jump();
            } else {
                positionComponent.translate(vector);
            }
        }
    }

    private void jump() {
        if (!physicComponent.isJump) {
            physicComponent.jumpPosY = positionComponent.getY();
            physicComponent.jump();
        }
    }

    private boolean isEntityJump() {
        return physicComponent.isJump;
    }

    private double getPlayerWidth() {
        // TODO: Fix Component PlayerComponent not found!
        //return entity.getComponent(PlayerComponent.class).playerWidth;
        return 32;
    }

    private double getPlayerHeight() {
        // TODO: Fix Component PlayerComponent not found!
        //return entity.getComponent(PlayerComponent.class).playerHeight;
        return 42;
    }

    private Point2D getTargetVector(Point2D vector) {
        return new Point2D(
                entity.getPosition().getX() + vector.getX(),
                entity.getPosition().getY() + vector.getY()
        );
    }
}
