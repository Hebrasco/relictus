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
                        //moveToNonCollidingVector(entity);
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

    private void moveToNonCollidingVector(Entity entity) {

        entity.setPosition(nonCollidingVector(entity));
    }

    // ONLY FOR Direction.DOWN
    private Point2D nonCollidingVector(Entity entity) {
        double posX = getEntity().getPositionComponent().getX();
        double posY = getEntity().getPositionComponent().getY();
        Point2D vector = new Point2D(posX, posY);

        int i = 1;
        while (i < getEntity().getPositionComponent().getY() + 42) {
            if (areRectanglesOverlapping(getEntity().getPositionComponent().getValue(), entity)) {
                return vector.add(0, posY + i);
            }
            i++;
        }

        return vector;
    }
}