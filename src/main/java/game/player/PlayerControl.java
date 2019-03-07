package game.player;

import com.almasb.fxgl.entity.component.Component;
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
    private final double jumpVelocity = 50.0;
    private final UserAction userActionJump = createMovementAction("Jump", Direction.UP, jumpVelocity);
    private final UserAction userActionLeft = createMovementAction("Left", Direction.LEFT, speed);
    private final UserAction userActionRight = createMovementAction("Right", Direction.RIGHT, speed);
    private final UserAction userActionDown = createMovementAction("Down", Direction.DOWN, speed);
    private ColliderComponent colliderComponent;
    private PhysicsComponent physicComponent;
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
        input.addAction(userActionDown, KeyCode.S);
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

    /**
     * Moves the player in the direction the player presses, if he
     * won't collide with another entity.
     * @param direction the direction the player pressed.
     * @param speed the movement speed of the player.
     */
    private void move(Direction direction, double speed) {
        final Point2D targetVector = new Point2D(
                entity.getPosition().getX() + direction.vector.multiply(speed).getX(),
                entity.getPosition().getY() + direction.vector.multiply(speed).getY()
        );

        if (!colliderComponent.isCollided(targetVector)) {
            entity.getPositionComponent().translate(direction.vector.multiply(speed));
        }
    }

    /**
     * Enables the jump in the {@link PhysicsComponent}.
     */
    private void enableEntityJump() {
        entity.getComponent(PhysicsComponent.class).isJump = true;
    }

    /**
     * Checks in the {@link PhysicsComponent} if the player is jumping or not.
     * @return true, if the player is currently jumping.
     */
    private boolean isEntityJump() {
        return entity.getComponent(PhysicsComponent.class).isJump;
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
}
