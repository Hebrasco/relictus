package factories;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import game.PlayerControl;

/**
 * @author Kevin Ortmeier
 */
public class RelictusEntityFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        return Entities.builder()
                .type(RelictusType.PLAYER)
                .from(data)
                //.viewFromAnimatedTexture("character.png", 3, Duration.seconds(0.5))
                .bbox(new HitBox(BoundingShape.box(32, 42)))
                //.viewFromNodeWithBBox(new Rectangle(30, 30, Color.BLUE))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }

    @Spawns("powerup")
    public Entity newPowerUp(SpawnData data) {
        return Entities.builder()
                .type(RelictusType.POWERUP)
                .from(data)
                .viewFromTextureWithBBox("diamond.png")
                //.viewFromNodeWithBBox(new Circle(data.<Integer>get("width") / 2, Color.RED))
                .with(new CollidableComponent(true))
                .build();
    }
}
