package de.sopa.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.UUID;

/**
 * @author Raphael Schilling
 */
public class ChoiceLevelSprite extends Sprite {
    private final String filename;

    public ChoiceLevelSprite(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion, final VertexBufferObjectManager vbo,String filename){
        super(pX, pY, pWidth, pHeight, pTextureRegion, vbo);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
