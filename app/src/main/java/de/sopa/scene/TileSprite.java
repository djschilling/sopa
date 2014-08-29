package de.sopa.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * David Schilling - davejs92@gmail.com
 */
public class TileSprite extends Sprite {

    private float startX;
    private float startY;

    public TileSprite(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion, final VertexBufferObjectManager vbo){
        super(pX, pY, pWidth, pHeight, pTextureRegion, vbo);
        this.startX = pX;
        this.startY = pY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }
}
