package de.sopa.drawing;

import org.andengine.entity.particle.emitter.BaseParticleEmitter;
import org.andengine.entity.particle.emitter.IParticleEmitter;
import org.andengine.util.math.MathConstants;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public class HorizontalLineParticleEmitter extends BaseParticleEmitter {

    private final float x;
    private final float y;
    private final float cameraWidth;

    public HorizontalLineParticleEmitter(float x, float y, float cameraWidth) {
        super(x + cameraWidth, y);
        this.x = x;
        this.y = y;
        this.cameraWidth = cameraWidth;
    }

    @Override
    public void getPositionOffset(float[] pOffset) {
        pOffset[0] = (float) (x + (cameraWidth * Math.random()));
        pOffset[1] = y;
    }
}
