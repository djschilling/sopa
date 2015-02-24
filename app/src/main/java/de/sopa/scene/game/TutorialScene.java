package de.sopa.scene.game;

import de.sopa.observer.Observer;
import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by raphael on 24.02.15.
 */
public class TutorialScene extends BaseScene implements Observer{
    private Sprite grid;
    private Sprite conceptText;
    private Sprite swipeText;
    private Sprite arrow;
    private int alreadySwitched = 0;

    @Override
    public void createScene(Object o) {
        final ButtonSprite background = new ButtonSprite(0,43, resourcesManager.tutorialScreenshotRegion, vbom);
        background.setWidth(1080);
        background.setHeight(1834);
        final ButtonSprite letsGo = new ButtonSprite(146, 843, resourcesManager.tutorialLetsGoRegion, vbom);
        letsGo.setVisible(false);
        grid = new Sprite(4, 412, 1064, 1064,resourcesManager.tutorialGridRegion, vbom);
        conceptText = new Sprite(18, 1521, 765, 258, resourcesManager.tutorialTextConceptRegion, vbom);

        swipeText = new Sprite(52, 1501, 649, 346, resourcesManager.tutorialTextSwipeRegion, vbom);
        swipeText.setVisible(false);
        arrow = new Sprite(539, 406, 288, 1084, resourcesManager.tutorialArrowRegion, vbom);
        arrow.setVisible(false);
        final ButtonSprite.OnClickListener listener = new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(alreadySwitched == 0) {
                    grid.setVisible(false);
                    conceptText.setVisible(false);
                    arrow.setVisible(true);
                    swipeText.setVisible(true);
                } else if(alreadySwitched == 1){
                    background.setVisible(false);
                    arrow.setVisible(false);
                    swipeText.setVisible(false);
                    letsGo.setVisible(true);
                    letsGo.setOnClickListener(this);
                    unregisterTouchArea(background);
                    registerTouchArea(letsGo);
                } else {
                    sceneService.loadFirstLevelFromTutorial();
                }
                alreadySwitched++;
            }
        };
        background.setOnClickListener(listener);
        attachChild(background);
        attachChild(grid);
        attachChild(conceptText);
        attachChild(arrow);
        attachChild(swipeText);
        attachChild(letsGo);
        registerTouchArea(background);
    }

    @Override
    public void onBackKeyPressed() {

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
    public void update() {

    }
}
