package de.sopa.drawing;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;


public class FlashEntityModifier extends AlphaModifier {

    private IEntity pEntity;

    public FlashEntityModifier() {

        super(0.1f, 0f, 1f);
    }

    @Override
    protected void onSetInitialValue(IEntity pEntity, float pAlpha) {

        super.onSetInitialValue(pEntity, pAlpha);
        this.pEntity = pEntity;
    }


    @Override
    protected void onModifierFinished(IEntity pItem) {

        pEntity.registerEntityModifier(new AlphaModifier(0.1f, 1f, 0f));
    }
}
