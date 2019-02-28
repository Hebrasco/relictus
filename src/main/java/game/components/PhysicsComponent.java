package game.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import factories.EntityTypes;
import javafx.geometry.Point2D;
import preferences.GamePreferences;

import java.util.List;

/**
 * @author Daniel Bedrich
 */
public class PhysicsComponent extends Component {
    
    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    public boolean isCollided(Point2D vector) {
        final int gridSize = GamePreferences.gridSize;
        final Point2D newPlayerPos = getGridVector(vector);
        //final List<Entity> entities = FXGL.getGameWorld().getEntitiesAt(newPlayerPos);
        final List<Entity> entities = FXGL.getGameWorld().getEntities();

        for (Entity entity : entities) {
            if (isTypePlatform(entity)) {
                final int posX = entity.getComponent(PositionComponent.class).getGridX(gridSize);
                final int posY = entity.getComponent(PositionComponent.class).getGridY(gridSize);
                final Point2D entityPos = new Point2D(posX, posY);

                System.out.println("Target position " + newPlayerPos + " - " + "Other entity position" + entityPos);

                return newPlayerPos.equals(entityPos);
            }
            //return entity.getComponent(ColliderComponent.class).isCollidable;
        }
        return false;
    }

    private Point2D getGridVector(Point2D vector) {
        final int gridSize = GamePreferences.gridSize;
        return new Point2D((int)(vector.getX() / gridSize), (int)(vector.getY() / gridSize));
    }

    private boolean isTypePlatform(Entity entity) {
        if (entity.hasComponent(TypeComponent.class)) {
            return entity.getComponent(TypeComponent.class).type.equals(EntityTypes.PLATFORM);
        } else {
            return false;
        }
    }
}
