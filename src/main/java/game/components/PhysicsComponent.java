package game.components;

import com.almasb.fxgl.entity.component.Component;
import game.player.Direction;
import javafx.geometry.Point2D;

import javax.jnlp.DownloadService;

/**
 * Handles the physics of the entity.
 *
 * @author Daniel Bedrich
 * @version 1.1
 */
public class PhysicsComponent extends Component {
    private final double gravity = 1.0;
    public int velocity = 1;
    private boolean isGrounded = false;

    @Override
    public void onUpdate(double tpf) {
        final Point2D targetVector = getTargetVector();
        final Direction direction = getMovingDirection();

        if (!isEntityCollided(targetVector, direction)) {
            velocity += gravity;
            entity.translateY(velocity);

        } else {
            if (direction.equals(Direction.UP)) {
                velocity = 0;
            } else if (direction.equals(Direction.DOWN)) {
                isGrounded = true;
            }
        }
    }

    /**
     * The direction, the entity is moving.
     *
     * @return {@link Direction}.UP, if entity is moving up on y axis.
     */
    private Direction getMovingDirection() {
        if (velocity < 0) {
            return Direction.UP;
        } else {
            return Direction.DOWN;
        }
    }

    /**
     * Creates the new position for the next frame.
     * @return the new position.
     */
    private Point2D getTargetVector() {
        return new Point2D(
                entity.getPosition().getX(),
                entity.getPosition().getY() + velocity + gravity
        );
    }

    /**
     * Lets the player jump, by reversing the gravity.
     */
    public void jump() {
        if (isGrounded) {
            int jumpVelocity = -12;
            velocity = jumpVelocity;
            isGrounded = false;
        }
    }

    // TODO: isMoving()

    /**
     * Checks on the {@link ColliderComponent} if the entity will collide with another entity.
     * @param targetVector the new position in the next frame.
     * @return true, if this entity will collide with another entity.
     */
    private boolean isEntityCollided(Point2D targetVector, Direction direction) {
        return entity.getComponent(ColliderComponent.class).willCollide(targetVector, direction);
    }
}
