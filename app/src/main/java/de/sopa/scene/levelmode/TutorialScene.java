package de.sopa.scene.levelmode;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 *
 */
public class TutorialScene extends BaseScene implements IOnSceneTouchListener {
    private Sprite secondScreenA;
    private int alreadySwitched = 0;
    private Sprite firstScreenB;
    private Sprite letsGo;
    private Sprite firstScreenA;
    private Sprite secondScreenB;
    private boolean leaveScene = false;

    public TutorialScene() {
        firstScreenA = new Sprite(0, 0, 1080, 960, resourcesManager.tutorialFirstRegionA, vbom);
        firstScreenB = new Sprite(0, 960, 1080, 960, resourcesManager.tutorialFirstRegionB, vbom);
        letsGo = new Sprite(146, 843, resourcesManager.tutorialLetsGoRegion, vbom);
        letsGo.setVisible(false);
        secondScreenA = new Sprite(0, 0, 1080, 960, resourcesManager.tutorialSecondRegionA, vbom);
        secondScreenB = new Sprite(0, 960, 1080, 960, resourcesManager.tutorialSecondRegionB, vbom);
        secondScreenA.setVisible(false);
        secondScreenB.setVisible(false);
        setOnSceneTouchListener(this);
        attachChild(firstScreenA);
        attachChild(firstScreenB);
        attachChild(secondScreenA);
        attachChild(secondScreenB);
        attachChild(letsGo);
    }

    @Override
    public void onBackKeyPressed() {
        leaveScene = true;
        storyService.loadLevelChoiceFromTutorial();
    }

    @Override
    public void disposeScene() {
        final TutorialScene tutorialScreen = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                tutorialScreen.detachChildren();
            }
        }));
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            if (alreadySwitched == 0) {
                firstScreenA.setVisible(false);
                firstScreenB.setVisible(false);
                secondScreenA.setVisible(true);
                secondScreenB.setVisible(true);
            } else if (alreadySwitched == 1) {
                secondScreenA.setVisible(false);
                secondScreenB.setVisible(false);
                letsGo.setVisible(true);
                engine.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        engine.unregisterUpdateHandler(pTimerHandler);
                        if(!leaveScene){
                            storyService.loadFirstLevelFromTutorial();
                        }
                    }
                }));
            }
            alreadySwitched++;
        }
        return false;
    }
}
