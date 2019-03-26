package utils;

import com.almasb.fxgl.app.DSLKt;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static preferences.GamePreferences.*;

/**
 * Defines different particle emitters.
 *
 * @author Daniel Bedrich, Kevin Ortmeier
 * @version 1.0
 */
public class Particles {
    /**
     * @return the dust particle emitter.
     */
    public static ParticleEmitter getDustEmitter() {
        return createDustParticleEmitter();
    }

    /**
     * Creates the dust particle emitter.
     * @return the particle emitter.
     */
    private static ParticleEmitter createDustParticleEmitter() {
        final ParticleEmitter particleEmitter = new ParticleEmitter();
        setParticleEmitterSettings(particleEmitter);

        return particleEmitter;
    }

    /**
     * Sets all the dust particle emitter settings.
     * @param particleEmitter the particle emitter to apply the settings to.
     */
    private static void setParticleEmitterSettings(ParticleEmitter particleEmitter) {
        final int textureSize = 8;
        final Texture particleTexture = DSLKt.texture(PARTICLES_PATH + MENU_PARTICLE_FILE_NAME, textureSize, textureSize).brighter().brighter();
        final Image particleImage = particleTexture.getImage();
        final BlendMode blendMode = BlendMode.ADD;
        final double emissionRate = 0.01;
        final int numParticlePerEmission = 35;
        final double particleSize = 0.01;

        particleEmitter.setSpawnPointFunction(func -> particleSpawnPointArea());
        particleEmitter.setVelocityFunction(func -> particleVelocity());
        particleEmitter.setScaleFunction(func -> particleScale());
        particleEmitter.setExpireFunction(func -> particleExpiration());
        particleEmitter.setSourceImage(particleImage);
        particleEmitter.setBlendMode(blendMode);
        particleEmitter.setEmissionRate(emissionRate);
        particleEmitter.setNumParticles(numParticlePerEmission);
        particleEmitter.setSize(particleSize, particleSize);
        particleEmitter.setAllowParticleRotation(true);
    }

    /**
     * Defines the spawn point for the particle that gets emitted.
     * @return the spawn point.
     */
    private static Point2D particleSpawnPointArea() {
        final double vectorOrigin = 0.0;
        final double vectorXEnd = FXGL.getAppHeight();
        final double vectorYEnd = FXGL.getAppWidth();
        final double spawnPointMultiplier = 2.0;

        return new Point2D(
                FXGLMath.random(vectorOrigin, vectorXEnd),
                FXGLMath.random(vectorOrigin, vectorYEnd)
        ).multiply(spawnPointMultiplier);
    }

    /**
     * Defines the particles velocity and direction that gets emitted.
     * @return the direction.
     */
    private static Point2D particleVelocity() {
        final double minVelocity = 5.0;
        final double maxVelocity = 15.0;

        return new Point2D(
                FXGLMath.random(minVelocity, maxVelocity),
                FXGLMath.random(minVelocity, maxVelocity)
        ).add(
                FXGLMath.random(-minVelocity, maxVelocity),
                FXGLMath.random(-minVelocity, maxVelocity)
        ).add(
                FXGLMath.random(-maxVelocity, -minVelocity),
                FXGLMath.random(-maxVelocity, -minVelocity)
        ).add(
                FXGLMath.random(-minVelocity, -maxVelocity),
                FXGLMath.random(-minVelocity, -maxVelocity)
        );
    }

    /**
     * Defines the particle scale that gets emitted.
     * @return the scale of the particle.
     */
    private static Point2D particleScale() {
        final double maxScaleFactor = 0.0075;

        return new Point2D(
                maxScaleFactor,
                maxScaleFactor
        );
    }

    /**
     * Defines the particles lifetime.
     * @return the lifetime in seconds.
     */
    private static Duration particleExpiration() {
        final int minLifetime = 5;
        final int maxLifetime = 15;

        return Duration.seconds(FXGLMath.random(minLifetime, maxLifetime));
    }
}
