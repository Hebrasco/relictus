package game;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.audio.AudioPlayer;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import factories.EntityTypes;
import game.components.ColliderComponent;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Daniel Bedrich
 */
public class Physics {
    private static final PhysicsWorld physicsWorld = FXGL.getPhysicsWorld();
    private static final AudioPlayer audioPlayer = FXGL.getAudioPlayer();
    private static final double gravity = 9.81;
    private static List<Entity> entities = new ArrayList<>();

    static void addCollisionHandler(EntityTypes a, EntityTypes b) {
        physicsWorld.addCollisionHandler(createCollisionHandler(a, b));
    }

    private static CollisionHandler createCollisionHandler(Object a, Object b) {
        return new CollisionHandler(a, b) {
            @Override
            protected void onCollision(Entity player, Entity powerup) {
                powerup.removeFromWorld();
                audioPlayer.playSound("powerup.mp3");
            }
        };
    }

    static void onUpdate(double tpf) {
        /*for (Entity entity : entities) {
            final Point2D targetVector = new Point2D(
                    (entity.getPosition().getX()),
                    (entity.getPosition().getY() + gravity)
            );
            if (!entity.getComponent(ColliderComponent.class).isCollided(targetVector)) {
                entity.translateY(gravity);
            }
        }*/
    }

    static void registerEntity(Entity entity) {
        entities.add(entity);
        entity.addComponent(new PhysicsComponent());
    }

    static void unregisterEntity(Entity entity) {
        entities.remove(entity);
    }
}
