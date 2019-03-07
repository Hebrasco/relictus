package factories;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import data.EntityTypes;
import game.components.ColliderComponent;
import game.components.PlayerComponent;
import preferences.GamePreferences;


/**
 * This class defines all entities and builds them when they will get spawned.
 *
 * @author Kevin Ortmeier, Daniel Bedrich
 * @version 1.0
 */
public class RelictusEntityFactory implements EntityFactory {

    /**
     * Builds the platform entity with the given data.
     * @param data the information about the entity, such as width and height.
     * @return the platform entity with a {@link com.almasb.fxgl.entity.components.BoundingBoxComponent}
     * and a {@link ColliderComponent} component.
     */
    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.PLATFORM)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new ColliderComponent())
                .build();
    }

    /**
     * Builds the player entity with the given data.
     * @param data the information about the entity, such as width and height.
     * @return the platform entity with a {@link PlayerComponent} component.
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.PLAYER)
                .from(data)
                .with(new PlayerComponent())
                .build();
    }

    /**
     * Builds the power-up entity with the given data.
     * @param data the information about the entity, such as width and height.
     * @return the platform entity with a {@link com.almasb.fxgl.entity.components.BoundingBoxComponent}
     * and a {@link CollidableComponent}.
     */
    @Spawns("powerup")
    public Entity newPowerUp(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.POWERUP)
                .from(data)
                .viewFromTextureWithBBox(GamePreferences.POWER_UP_FILE_NAME)
                .with(new CollidableComponent(true))
                .build();
    }
}
