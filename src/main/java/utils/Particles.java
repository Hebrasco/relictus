package utils;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * @author Daniel Bedrich
 */
public class Particles {

    public static ParticleEmitter getDustEmitter() {
        return createDustParticleEmitter();
    }

    private static ParticleEmitter createDustParticleEmitter() {
        final ParticleEmitter particleEmitter = new ParticleEmitter();
        setParticleEmitterSettings(particleEmitter);

        return particleEmitter;
    }

    private static void setParticleEmitterSettings(ParticleEmitter particleEmitter) {
        final Texture particleTexture = FXGL.texture("particles/dust.png", 8, 8).brighter().brighter();
        final Image particleImage = particleTexture.getImage();
        final BlendMode blendMode = BlendMode.ADD;
        final double emissionRate = 0.0025;
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

    private static Point2D particleVelocity() {
        final double minVelocity = 5.0;
        final double maxVelocity = 15.0;

        return new Point2D(
                FXGLMath.random(minVelocity, maxVelocity),
                FXGLMath.random(minVelocity, maxVelocity)
        );
    }

    private static Point2D particleScale() {
        final double maxScaleFactor = 0.0075;

        return new Point2D(
                maxScaleFactor,
                maxScaleFactor
        );
    }

    private static Duration particleExpiration() {
        final int minLifetime = 5;
        final int maxLifetime = 15;

        return Duration.seconds(FXGLMath.random(minLifetime, maxLifetime));
    }
}
