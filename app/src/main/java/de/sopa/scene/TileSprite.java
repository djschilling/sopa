package de.sopa.scene;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.UUID;

/**
 * David Schilling - davejs92@gmail.com
 */
public class TileSprite extends Sprite {

    private float startX;
    private float startY;
    private UUID uuid;

    public TileSprite(final float pX, final float pY, final float pWidth, final float pHeight, final ITextureRegion pTextureRegion, final VertexBufferObjectManager vbo){
        super(pX, pY, pWidth, pHeight, pTextureRegion, vbo);
        this.startX = pX;
        this.startY = pY;
        uuid = UUID.randomUUID();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TileSprite)){
            return false;
        }

        TileSprite that = (TileSprite) o;

        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }
}
