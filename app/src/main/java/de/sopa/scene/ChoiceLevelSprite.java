package de.sopa.scene;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.UUID;

/**
 * @author Raphael Schilling
 */
public class ChoiceLevelSprite extends ButtonSprite {

    public ChoiceLevelSprite(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager vbo, OnClickListener onClickListener){
        super(pX, pY,pTextureRegion, vbo, onClickListener);
    }

}
