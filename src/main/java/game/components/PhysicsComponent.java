package game.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

/**
 * Handles the physics of the entity.
 *
 * @author Daniel Bedrich, Lara-Marie Mann
 * @version 1.0
 */
public class PhysicsComponent extends Component {
    public boolean isJump = false;
    public double gravity = 7;
    public double jumpPosY;

    @Override
    public void onAdded() {
    }

    @Override
    public void onUpdate(double tpf) {
        final Point2D targetVector = getTargetVector();

        if (!isEntityCollided(targetVector)) {
            entity.translateY(gravity);
        }

        if (isJump) {
            final double pos = entity.getPositionComponent().getY();
            if (pos < (jumpPosY - 84)) {
                enableGravitiy();
            }
        }
    }

    /**
     * Creates the new position for the next frame.
     * @return the new position.
     */
    private Point2D getTargetVector() {
        return new Point2D(
                entity.getPosition().getX(),
                entity.getPosition().getY() + gravity
        );
    }

    /**
     * Turns the gravity to normal again.
     */
    private void enableGravitiy() {
        if (isJump) {
            isJump = false;
            gravity *= -1;
        }
    }

    /**
     * Lets the player jump, by reversing the gravity.
     */
    public void jump() {
        if (!isJump) {
            isJump = true;
            gravity *= -1;
        }
    }

    // TODO: isGrounded()
    // cast raycast zum boden

    /**
     * Checks on the {@link ColliderComponent} if the entity will collide with another entity.
     * @param targetVector the new position in the next frame.
     * @return true, if this entity will collide with another entity.
     */
    private boolean isEntityCollided(Point2D targetVector) {
        return entity.getComponent(ColliderComponent.class).isCollided(targetVector);
    }
}
