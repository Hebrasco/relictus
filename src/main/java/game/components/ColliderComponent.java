package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import factories.EntityTypes;
import javafx.geometry.Point2D;
import preferences.GamePreferences;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Bedrich
 */
public class ColliderComponent extends Component {
    private boolean isCollidable;

    public ColliderComponent(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public boolean isCollided(Point2D vector) {
        final Point2D newPlayerPos = getGridVector(vector);
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            final double posX = entity.getPositionComponent().getX();
            final double posY = entity.getPositionComponent().getY();
            final Point2D entityPos = getGridVector(new Point2D(posX, posY));
            final int width = getGridWidth(entity);
            final int height = getGridHeight(entity);
            final List<Point2D> entityTiles = new ArrayList<>();

            if (entity.isType(EntityTypes.PLATFORM)) {
                for (int i = 0; i <= width; i++) {
                    for (int k = 0; k <= height; k++) {
                        Point2D tilePos = new Point2D(entityPos.getX() + i, entityPos.getY() + k);
                        entityTiles.add(tilePos);
                    }
                }

                for (Point2D tilePos : entityTiles) {
                    if (newPlayerPos.equals(tilePos)) {
                        logCollisionDetected(newPlayerPos, tilePos);

                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void logCollisionDetected(Point2D newPlayerPos, Point2D entityPos) {
        System.out.println("----------- COLLISION DETECTED -----------");
        System.out.println("Target position " + newPlayerPos);
        System.out.println("Entity position " + entityPos);
        System.out.println("------------------- END -------------------");
    }

    private Point2D getGridVector(Point2D vector) {
        final int gridSize = GamePreferences.gridSize;
        return new Point2D((int) (vector.getX() / gridSize), (int) (vector.getY() / gridSize));
    }

    private int getGridWidth(Entity entity) {
        final int gridSize = GamePreferences.gridSize;
        return (int) entity.getWidth() / gridSize;
    }

    private int getGridHeight(Entity entity) {
        final int gridSize = GamePreferences.gridSize;
        return (int) entity.getHeight() / gridSize;
    }
}
