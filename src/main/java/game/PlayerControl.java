package game;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Pos;
import javafx.geometry.Point2D;
import javafx.util.Duration;

/**
 * @author Kevin Ortmeier
 */
@Required(PositionComponent.class)
public class PlayerControl extends Component {
    private PhysicsComponent physics;
    private PositionComponent position;
    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk;

    private LocalTimer timer;
    private double speed = 150;

    public PlayerControl() {
        animIdle = new AnimationChannel("player.png", 4, 32, 42, Duration.seconds(1), 1, 1);
        animWalk = new AnimationChannel("player.png", 4, 32, 42, Duration.seconds(1), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        timer = FXGL.newLocalTimer();
        timer.capture();
        entity.setView(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        texture.playAnimationChannel(isMoving() ? animWalk : animIdle);
        speed = tpf * 200;
    }

    private boolean isMoving() {
        return FXGLMath.abs(physics.getVelocityX()) > 0;
    }

    public void moveRight() {
        getEntity().setScaleX(1);
        position.translateX(speed);
        physics.setVelocityX(200);
    }

    public void moveLeft() {
        getEntity().setScaleX(-1);
        physics.setVelocityX(-200);
    }

    public void jump() {
        if (physics.isOnGround()) {
            physics.setVelocityY(-200);
        }
    }
}
