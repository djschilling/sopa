package de.sopa.scene;

import org.andengine.entity.sprite.ButtonSprite;

/**
 * Created by raphael on 03.09.14.
 */
public class SettingsScene extends BaseScene {

    @Override
    public void createScene(Object o) {
        ButtonSprite mute =  new ButtonSprite(camera.getWidth()/2+300/2,(camera.getHeight()/2+300/2), resourcesManager.muteRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                //TODO: Mute The Music
            }
        });
        attachChild(mute);
        registerTouchArea(mute);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromSettingsScene();
    }

    @Override
    public void disposeScene() {

    }
}
