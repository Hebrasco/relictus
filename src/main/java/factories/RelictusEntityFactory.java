package factories;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import game.player.PlayerControl;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * @author Kevin Ortmeier
 */
public class RelictusEntityFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                //.with(new PhysicsComponent())
                .viewFromNode(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.CYAN))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        //PhysicsComponent physics = new PhysicsComponent();
        //physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(EntityTypes.PLAYER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(32, 42)))
                .with(new CollidableComponent(true))
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
