package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import data.EntityTypes;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * Handles the collisions with other entities.
 *
 * @author Daniel Bedrich
 * @version 1.0
 */
public class ColliderComponent extends Component {
    /*
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
                audioPlayer.playSound(GamePreferences.SOUND_POWERUP);
            }
        };
    }
    */

    /**
     * Checks if the new position of this entity in the next frame will move inside another entity.
     *
     * @param vector the point coordinates of the new position.
     * @return true, if the entity will move inside another entity.
     */
    public boolean willCollide(Point2D vector) {
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            if (entity.isType(EntityTypes.PLATFORM)) {
                if (entity.getWidth() != 0 && entity.getHeight() != 0) {
                    final boolean EntitiesAreOverlapping = areRectanglesOverlapping(vector, entity);

                    if (EntitiesAreOverlapping) {
                        getEntity().setPosition(nonCollidingVector(entity));
                        return true;
                    }
                }
            }
            // Entity ist keiner der oben genannten Typen
        }
        return false;
    }

    /**
     * Checks if two rectangles are overlapping each other.
     *
     * @param position the new position of the first entity.
     * @param entity   the entity to compare with.
     * @return true, if both entities are overlapping each other.
     * @implNote Only for static entities.
     */
    private boolean areRectanglesOverlapping(Point2D position, Entity entity) {
        final double playerPosX = position.getX();
        final double playerPosY = position.getY();
        final double playerPosXRange = playerPosX + 32; // TODO: Spieler breite von PlayerComponent laden
        final double playerPosYRange = playerPosY + 42; // TODO: Spieler höhe von PlayerComponent laden
        final double entityPosX = entity.getPositionComponent().getX();
        final double entityPosY = entity.getPositionComponent().getY();
        final double entityPosXRange = entityPosX + entity.getWidth();
        final double entityPosYRange = entityPosY + entity.getHeight();

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

    /**
     * Checks if two lines are overlapping each other.
     *
     * @param minA the first point of line A.
     * @param maxA the second point of line A.
     * @param minB the first point of line B.
     * @param maxB the second point of line B.
     * @return true, if both lines are overlapping each other.
     */
    private boolean isOverlapping(double minA, double maxA, double minB, double maxB) {
        return Math.max(minA, maxA) >= Math.min(minB, maxB) &&
                Math.min(minA, maxA) <= Math.max(minB, maxB);
    }

    /**
     * Moves the entity to the edge of the others entity collider.
     *
     * @implNote Only fort Direction.DOWN.
     * @param entity the entity to move it up to.
     */
    private Point2D nonCollidingVector(Entity entity) {
        final double posX = getEntity().getPositionComponent().getX();
        final double posY = getEntity().getPositionComponent().getY();
        final Point2D vector = new Point2D(posX, posY);

        int y = (int) posY;
        while (y < y + 42) {
            final Point2D targetVector = new Point2D(posX, y);
            final boolean overlapping = areRectanglesOverlapping(targetVector, entity);
            
            if (overlapping) {
                return targetVector;
            }
            y++;
        }

        return vector;
    }
}