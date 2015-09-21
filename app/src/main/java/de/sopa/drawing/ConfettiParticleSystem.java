package de.sopa.drawing;

import org.andengine.entity.IEntityFactory;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.initializer.GravityParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 * @author David Schilling - davejs92@gmail.com
 */
public class ConfettiParticleSystem extends ParticleSystem<Rectangle> {

    private static final Color[] COLOR_MAP = {  new Color(1f, 0f, 0f),
                                                new Color(0.07f, 0.84f, 0.12f),
                                                new Color(0.24f, 0f, 0.87f),
                                                new Color(1f, 1f, 0f)
    };

    public ConfettiParticleSystem(final VertexBufferObjectManager vbom, float cameraWidth) {

        super(createEntityFactory(vbom), new HorizontalLineParticleEmitter(0, 0, cameraWidth), 50f, 100f, 200);
        addParticleInitializer(new VelocityParticleInitializer<Rectangle>(-20, 20, 250, 300));
        addParticleInitializer(new GravityParticleInitializer<Rectangle>());
    }

    private static IEntityFactory<Rectangle> createEntityFactory(final VertexBufferObjectManager vbom) {

        return new IEntityFactory<Rectangle>() {
            @Override
            public Rectangle create(final float pX, final float pY) {

                Rectangle rectangle = new Rectangle(pX, pY, 30, 15, vbom);
                rectangle.setColor(COLOR_MAP[getRandomBetweenZeroAndThree()]);
                rectangle.registerEntityModifier(new RotationModifier(7f, (float) Math.random() * 360,
                        (7 - (float) Math.random() * 15) * 360));
                rectangle.registerEntityModifier(new AlphaModifier(4f, 0.7f, 0f));
                return rectangle;
            }};
    }

    private static int getRandomBetweenZeroAndThree() {
        return (int) (Math.random() * 4);
    }
}
