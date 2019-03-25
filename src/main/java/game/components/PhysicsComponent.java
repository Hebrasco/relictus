package game.components;

import com.almasb.fxgl.entity.component.Component;
import game.player.Direction;
import javafx.geometry.Point2D;

/**
 * Handles the physics of the entity.
 *
 * @author Daniel Bedrich, Lara-Marie Mann
 * @version 1.0
 */
public class PhysicsComponent extends Component {
    public int velocity = 1;
    private double gravity = 1;

    @Override
    public void onAdded() {
    }

    @Override
    public void onUpdate(double tpf) {
        final Point2D targetVector = getTargetVector();

        if (!isEntityCollided(targetVector)) {
            velocity += gravity;
            entity.translateY(velocity);
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
        int jumpVelocity = -12;
        velocity = jumpVelocity;
    }

    // TODO: isMoving()
    // TODO: isGrounded()
    // cast raycast zum boden

    /**
     * Checks on the {@link ColliderComponent} if the entity will collide with another entity.
     * @param targetVector the new position in the next frame.
     * @return true, if this entity will collide with another entity.
     */
    private boolean isEntityCollided(Point2D targetVector) {
        return entity.getComponent(ColliderComponent.class).willCollide(targetVector, Direction.DOWN);
    }
}
