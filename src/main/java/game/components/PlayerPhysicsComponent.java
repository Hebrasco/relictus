package game.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

/**
 * @author Daniel Bedrich
 */
public class PlayerPhysicsComponent extends Component {
    public boolean isJump = false;
    private double gravity = 7;
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

    private Point2D getTargetVector() {
        return new Point2D(
                entity.getPosition().getX(),
                entity.getPosition().getY() + gravity
        );
    }

    private double getJumpHeight() {
        // TODO: Fix PlayerComponent wurde entfernt und kann daher nicht gefunden werden
        //System.out.println(entity.hasComponent(PlayerComponent.class));
        //return entity.getComponent(PlayerComponent.class).playerHeight * 2;
        return 1; // Tile
    }

    private boolean isEntityCollided(Point2D targetVector) {
        return entity.getComponent(ColliderComponent.class).isCollided(targetVector);
    }

    private void enableGravitiy() {
        if (isJump) {
            gravity *= -1;
        }
    }

    public void jump() {
        if (!isJump) {
            isJump = true;
            gravity *= -1;
        }
    }

    // TODO: isGrounded()
    // cast raycast zum boden
}
