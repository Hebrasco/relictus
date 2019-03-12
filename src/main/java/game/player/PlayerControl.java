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
import preferences.GamePreferences;

/**
 * Defines the player controls.
 *
 * @author Kevin Ortmeier, Daniel Bedrich
 * @version 1.0
 */
public class PlayerControl extends Component {
    private final double speed = 7.5;
    private final UserAction userActionJump = createJumpAction();
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private ColliderComponent colliderComponent;
    private PhysicsComponent physicComponent;
    private PositionComponent positionComponent;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    @Override
    public void onAdded() {
        // TODO: Animationen und Textur in andere Klasse verschieben
        animIdle = new AnimationChannel(GamePreferences.PLAYER_FILE_NAME, 4, (int) getPlayerWidth(), (int) getPlayerHeight(), Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel(GamePreferences.PLAYER_FILE_NAME, 4, (int) getPlayerWidth(), (int) getPlayerHeight(), Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);

        entity.setView(texture);

        colliderComponent = entity.getComponent(ColliderComponent.class);
        physicComponent = entity.getComponent(PhysicsComponent.class);
        positionComponent = entity.getPositionComponent();
    }

    @Override
    public void onUpdate(double tpf) {
        //texture.playAnimationChannel(physics.isMoving() ? animWalk : animIdle);
    }

    // TODO: isMoving()
    // TODO: isGrounded()

    /**
     * Adds the controls to the player input.
     * @param input the input to add the actions to.
     */
    public void createInput(Input input) {
        input.addAction(userActionLeft, KeyCode.A);
        input.addAction(userActionRight, KeyCode.D);
        input.addAction(userActionJump, KeyCode.W);
    }

    /**
     * Creates a {@link UserAction} to move the player around the game world.
     * @param name the name of the action.
     * @param direction the direction vector to move to.
     * @param speed the speed the player should move.
     * @return the {@link UserAction} with defined actions to move the player.
     */
    private UserAction createMovementAction(String name, Direction direction, double speed) {
        return new UserAction(name) {
            @Override
            protected void onAction() {
                move(direction, speed);
            }
        };
    }

    // TODO: Wenn im Spingen, richtung merken und input sperren

    /**
     * Creates a {@link UserAction} to let the player jump.
     * @return the {@link UserAction} with defined actions to let the player jump.
     */
    private UserAction createJumpAction() {
        return new UserAction("Jump") {
            @Override
            protected void onActionBegin() {
                move(Direction.UP, physicComponent.velocity);
            }
        };
    }

    /**
     * Moves the player in the direction the player presses, if he
     * won't collide with another entity.
     * @param direction the direction the player pressed.
     * @param speed the movement speed of the player.
     */
    private void move(Direction direction, double speed) {
        // TODO: Add acceleration to left and right movement
        final Point2D vector = direction.vector.multiply(speed);
        final Point2D targetVector = getTargetVector(vector);

        if (!colliderComponent.willCollide(targetVector)) {
            if (direction.equals(Direction.UP)) {
                jump();
            } else {
                positionComponent.translate(vector);
            }
        }
    }

    /**
     * Lets the player jump
     */
    private void jump() {
        // TODO: Dix multiple jumps
        physicComponent.jump();
    }

    /**
     * @return the width of the player sprite.
     */
    private double getPlayerWidth() {
        // TODO: Fix Component PlayerComponent not found!
        //return entity.getComponent(PlayerComponent.class).playerWidth;
        return 32;
    }

    /**
     * @return the height of the player sprite.
     */
    private double getPlayerHeight() {
        // TODO: Fix Component PlayerComponent not found!
        //return entity.getComponent(PlayerComponent.class).playerHeight;
        return 42;
    }

    /**
     * Get the target vector, where the player will be in, in the next frame.
     * @param vector the vector to add.
     * @return the new vector.
     */
    private Point2D getTargetVector(Point2D vector) {
        return new Point2D(
                entity.getPosition().getX() + vector.getX(),
                entity.getPosition().getY() + vector.getY()
        );
    }
}
