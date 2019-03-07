package game.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

/**
 * Handles the physics of the entity.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class PhysicsComponent extends Component {
    public boolean isJump = false;
    private final double gravity = 9.81;
    private double jumpHeight;

    @Override
    public void onAdded() {
        jumpHeight = getJumpHeight();
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isJump) {
            final Point2D targetVector = getTargetVector();

            if (!isEntityCollided(targetVector)) {
                entity.translateY(gravity);
            }
        } else {
            jumpHeight -= tpf;
            if (jumpHeight <= 0) {
                isJump = false;
                jumpHeight = getJumpHeight();
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
     * @return 1
     */
    private double getJumpHeight() {
        // TODO: Fix PlayerComponent wurde entfernt und kann daher nicht gefunden werden
        //System.out.println(entity.hasComponent(PlayerComponent.class));
        //return entity.getComponent(PlayerComponent.class).playerHeight * 2;
        return 1; // Tile
    }

    /**
     * Checks on the {@link ColliderComponent} if the entity will collide with another entity.
     * @param targetVector the new position in the next frame.
     * @return true, if this entity will collide with another entity.
     */
    private boolean isEntityCollided(Point2D targetVector) {
        return entity.getComponent(ColliderComponent.class).isCollided(targetVector);
    }
}
