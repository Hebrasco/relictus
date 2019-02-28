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
    // TODO: Kollidierung an allen 4 Ecken messen
    // Kollidierung wird gerade nur am oberen rechten Punkt gemessen
    public boolean isCollided(Point2D vector) {
        final Point2D newPlayerPos = getGridVector(vector);
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            if (entity.isType(EntityTypes.PLATFORM)) {
                final List<Point2D> entityTiles = getEntityTiles(entity);
                for (Point2D tilePos : entityTiles) {
                    if (newPlayerPos.equals(tilePos)) {
                        return true;
                    }
                    // Entity ist nicht auf der neuen Spieler Position
                }
            }
            // Entity ist keiner der oben genannten Typen
        }
        return false;
    }

    private List<Point2D> getEntityTiles(Entity entity) {
        final int width = getEntityGridWidth(entity);
        final int height = getEntityGridHeight(entity);
        final double posX = entity.getPositionComponent().getX();
        final double posY = entity.getPositionComponent().getY();
        final Point2D entityPos = getGridVector(new Point2D(posX, posY));

        final List<Point2D> entityTiles = new ArrayList<>();
        for (int i = 0; i <= width; i++) {
            for (int k = 0; k <= height; k++) {
                Point2D tilePos = new Point2D(entityPos.getX() + i, entityPos.getY() + k);
                entityTiles.add(tilePos);
            }
        }
        return entityTiles;
    }

    private Point2D getGridVector(Point2D vector) {
        final int gridSize = GamePreferences.gridSize;
        return new Point2D((int) (vector.getX() / gridSize), (int) (vector.getY() / gridSize));
    }

    private int getEntityGridWidth(Entity entity) {
        final int gridSize = GamePreferences.gridSize;
        return (int) entity.getWidth() / gridSize;
    }

    private int getEntityGridHeight(Entity entity) {
        final int gridSize = GamePreferences.gridSize;
        return (int) entity.getHeight() / gridSize;
    }
}
