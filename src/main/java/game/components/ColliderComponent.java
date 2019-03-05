package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import factories.EntityTypes;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * @author Daniel Bedrich
 */
public class ColliderComponent extends Component {
    private final PhysicsWorld physicsWorld = FXGL.getPhysicsWorld();
    private final AudioPlayer audioPlayer = FXGL.getAudioPlayer();

    void addCollisionHandler(EntityTypes a, EntityTypes b) {
        physicsWorld.addCollisionHandler(createCollisionHandler(a, b));
    }

    private CollisionHandler createCollisionHandler(Object a, Object b) {
        return new CollisionHandler(a, b) {
            @Override
            protected void onCollision(Entity player, Entity powerup) {
                powerup.removeFromWorld();
                audioPlayer.playSound("powerup.mp3");
            }
        };
    }

    public boolean isCollided(Point2D vector) {
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            if (entity.isType(EntityTypes.PLATFORM)) {
                if (entity.getWidth() != 0 && entity.getHeight() != 0) {
                    final boolean EntitiesAreOverlapping = areRectanglesOverlapping(vector, entity);

                    if (EntitiesAreOverlapping) {
                        return true;
                    }
                }
            }
            // Entity ist keiner der oben genannten Typen
        }
        return false;
    }

    // Only for static entities
    private boolean areRectanglesOverlapping(Point2D newPlayerPos, Entity entity) {
        final double playerPosX = newPlayerPos.getX();
        final double playerPosY = newPlayerPos.getY();
        final double playerPosXRange = playerPosX + 32;
        final double playerPosYRange = playerPosY + 42;
        final double entityPosX = entity.getPositionComponent().getX();
        final double entityPosY = entity.getPositionComponent().getY();
        final double entityPosXRange = entityPosX + entity.getWidth();
        final double entityPosYRange = entityPosY + entity.getHeight();

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Player position: TopLeft:" + playerPosX + ", " + playerPosY);
        System.out.println("Player range: " + playerPosXRange + ", " + playerPosYRange);
        System.out.println("Entity position: " + entityPosX + ", " + entityPosY);
        System.out.println("Entity range: " + entityPosXRange + ", " + entityPosYRange);
        System.out.println("Colliding: ");
        System.out.println(isOverlapping(
                playerPosX,
                playerPosXRange,
                entityPosX,
                entityPosXRange
                ) && isOverlapping(
                playerPosY,
                playerPosYRange,
                entityPosY,
                entityPosYRange
                )
        );

        return isOverlapping(
                playerPosX,
                playerPosXRange,
                entityPosX,
                entityPosXRange
        ) && isOverlapping(
                playerPosY,
                playerPosYRange,
                entityPosY,
                entityPosYRange
        );
    }

    private boolean isOverlapping(double minA, double maxA, double minB, double maxB) {
        return Math.max(minA, maxA) >= Math.min(minB, maxB) &&
                Math.min(minA, maxA) <= Math.max(minB, maxB);
    }
}
