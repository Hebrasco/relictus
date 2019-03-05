package game.player;

import com.almasb.fxgl.entity.component.Component;
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
    private final double jumpVelocity = 50.0;
    private final UserAction userActionJump = createMovementAction("Jump", Direction.UP, jumpVelocity);
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private final UserAction userActionDown = createMovementAction("Down", Direction.DOWN, speed);
    private ColliderComponent colliderComponent;
    private PlayerPhysicsComponent physicComponent;
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

    private UserAction createMovementAction(String name, Direction direction, double speed) {
        return new UserAction(name) {
            @Override
            protected void onActionBegin() {
                if (direction.equals(Direction.UP) && !isEntityJump()) {
                    enableEntityJump();
                    move(direction, speed);
                }
            }

            @Override
            protected void onAction() {
                if (!direction.equals(Direction.UP)) {
                    move(direction, speed);
                }
            }
        };
    }

    private void move(Direction direction, double speed) {
        final Point2D targetVector = new Point2D(
                entity.getPosition().getX() + direction.vector.multiply(speed).getX(),
                entity.getPosition().getY() + direction.vector.multiply(speed).getY()
        );

        if (!colliderComponent.isCollided(targetVector)) {
            entity.getPositionComponent().translate(direction.vector.multiply(speed));
        }
    }

    private void enableEntityJump() {
        entity.getComponent(PlayerPhysicsComponent.class).isJump = true;
    }

    private boolean isEntityJump() {
        return entity.getComponent(PlayerPhysicsComponent.class).isJump;
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
}
