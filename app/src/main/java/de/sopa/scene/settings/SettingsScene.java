package de.sopa.scene.settings;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

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
        final Text count = new Text(200,200,resourcesManager.settingsFont , "" + resourcesManager.justPlayMoves, vbom);
        attachChild(count);
        attachChild(mute);
        registerTouchArea(mute);
        Text plus = new Text(0,0,resourcesManager.settingsFont, "+",vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp()) {
                    resourcesManager.justPlayMoves += 1;
                    count.setText("" + resourcesManager.justPlayMoves);
                }
                return false;
            }
        };
        registerTouchArea(plus);
        attachChild(plus);
        Text minus = new Text(500,500,resourcesManager.settingsFont, "-",vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(pSceneTouchEvent.isActionUp()) {
                    resourcesManager.justPlayMoves -= 1;
                    count.setText("" + resourcesManager.justPlayMoves);
                }
                return false;
            }
        };
        registerTouchArea(minus);
        attachChild(minus);

    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromSettingsScene();
    }

    @Override
    public void disposeScene() {
        final SettingsScene settingsScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                settingsScene.detachChildren();
            }
        }));

    }
}
