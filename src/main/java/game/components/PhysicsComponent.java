package game.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

/**
 * @author Daniel Bedrich
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
}
