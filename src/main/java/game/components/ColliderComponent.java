package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import factories.EntityTypes;
import javafx.geometry.Point2D;
import preferences.GamePreferences;

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
                return areRectanglesOverlapping(vector, entity);
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
        final double entityPosXRange = entityPosX + entity.getWidth(); // Width returns 0
        final double entityPosYRange = entityPosY + entity.getHeight(); // Height returns 0

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Player position - X:" + playerPosX + " Y: " + playerPosY);
        System.out.println("Player range - X:" + playerPosXRange + " Y: " + playerPosYRange);
        System.out.println("---");
        System.out.println("Entity position - X:" + entityPosX + " Y: " + entityPosY);
        System.out.println("Entity range - X:" + entityPosXRange + " Y: " + entityPosYRange);

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
