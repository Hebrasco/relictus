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
 * @author Kevin Ortmeier, Daniel Bedrich
 */
public class RelictusEntityFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.PLATFORM)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new ColliderComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.PLAYER)
                .from(data)
                .with(new PlayerComponent())
                .build();
    }

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
