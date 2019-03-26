package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import data.EntityTypes;
import game.player.Direction;
import javafx.geometry.Point2D;

import java.util.List;

import static game.components.PlayerComponent.*;

/**
 * Handles the collisions with other entities.
 *
 * @author Daniel Bedrich
 * @version 2.0
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
     * @param direction the direction, this entity is heading.
     * @return true, if the entity will move inside another entity.
     */
    public boolean willCollide(Point2D vector, Direction direction) {
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            if (entity.isType(EntityTypes.PLATFORM)) {
                final boolean EntitiesAreOverlapping = areRectanglesOverlapping(vector, entity);

                if (EntitiesAreOverlapping) {
                    getEntity().setPosition(nonCollidingVector(entity, direction));
                    return true;
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
        final double playerPosXRange = playerPosX + PLAYER_WIDTH;
        final double playerPosYRange = playerPosY + PLAYER_HEIGHT;
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
     * @param entity the entity it will be moved to.
     * @param direction the direction, this entity is heading.
     */
    private Point2D nonCollidingVector(Entity entity, Direction direction) {
        final int x = (int) getEntity().getPositionComponent().getX();
        final int y = (int) getEntity().getPositionComponent().getY();

        Point2D targetVector = new Point2D(x, y);
        final boolean isXAxis = direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT);
        int axisToModify;
        final int nonModifiedAxis;

        if (isXAxis) {
            axisToModify = x;
            nonModifiedAxis = y;
        } else {
            axisToModify = y;
            nonModifiedAxis = x;
        }

        boolean doOverlap = false;
        while (!doOverlap) {
            if (direction.equals(Direction.RIGHT) || direction.equals(Direction.LEFT)) {
                targetVector = new Point2D(axisToModify, nonModifiedAxis);
            } else {
                targetVector = new Point2D(nonModifiedAxis, axisToModify);
            }

            if (direction.equals(Direction.RIGHT) || direction.equals(Direction.DOWN)) {
                axisToModify++;
            } else {
                axisToModify--;
            }

            doOverlap = areRectanglesOverlapping(targetVector, entity);
        }
        return targetVectorWithSafetyZone(targetVector, direction);
    }

    /**
     * Builds the safety zone around the given vector.
     *
     * @param targetVector the vector, where this entity will be set to.
     * @param direction the direction, this entity is heading to.
     * @return the vector with added safety zone.
     */
    private Point2D targetVectorWithSafetyZone(Point2D targetVector, Direction direction) {
        final int safetyZone = 1;
        Point2D safetyZoneX = new Point2D(safetyZone, 0);
        Point2D safetyZoneY = new Point2D(0, safetyZone);
        if (direction.equals(Direction.UP)) {
            targetVector = targetVector.add(safetyZoneY);
        } else if (direction.equals(Direction.DOWN)) {
            targetVector = targetVector.subtract(safetyZoneY);
        } else if (direction.equals(Direction.LEFT)) {
            targetVector = targetVector.add(safetyZoneX);
        } else if (direction.equals(Direction.RIGHT)) {
            targetVector = targetVector.subtract(safetyZoneX);
        }
        return targetVector;
    }
}