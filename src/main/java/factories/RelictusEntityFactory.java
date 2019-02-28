package factories;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import game.components.ColliderComponent;
import game.player.PlayerControl;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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
                .viewFromNode(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.CYAN))
                .with(new ColliderComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.PLAYER)
                .from(data)
                .with(new ColliderComponent())
                .with(new PlayerControl())
                .viewFromNode(new Rectangle(32, 42, Color.RED))
                .build();
    }

    @Spawns("powerup")
    public Entity newPowerUp(SpawnData data) {
        return Entities.builder()
                .type(EntityTypes.POWERUP)
                .from(data)
                .viewFromTextureWithBBox("diamond.png")
                .with(new CollidableComponent(true))
                .build();
    }
}
