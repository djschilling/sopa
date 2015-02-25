package de.sopa.scene.tutorial;

import de.sopa.manager.ResourcesManager;
import de.sopa.scene.BaseScene;
import de.sopa.scene.game.LevelModeGameScene;
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
    private Sprite grid;
    private Sprite conceptText;
    private Sprite swipeText;
    private Sprite arrow;
    private int alreadySwitched = 0;
    private Sprite background;
    private Sprite letsGo;

    @Override
    public void createScene(Object o) {
        background = new Sprite(0, 43, resourcesManager.tutorialScreenshotRegion, vbom);
        background.setWidth(1080);
        background.setHeight(1834);
        letsGo = new Sprite(146, 843, resourcesManager.tutorialLetsGoRegion, vbom);
        letsGo.setVisible(false);
        grid = new Sprite(4, 412, 1064, 1064, resourcesManager.tutorialGridRegion, vbom);
        conceptText = new Sprite(18, 1521, 765, 258, resourcesManager.tutorialTextConceptRegion, vbom);

        swipeText = new Sprite(52, 1501, 649, 346, resourcesManager.tutorialTextSwipeRegion, vbom);
        swipeText.setVisible(false);
        arrow = new Sprite(539, 406, 288, 1084, resourcesManager.tutorialArrowRegion, vbom);
        arrow.setVisible(false);

        setOnSceneTouchListener(this);
        attachChild(background);
        attachChild(grid);
        attachChild(conceptText);
        attachChild(arrow);
        attachChild(swipeText);
        attachChild(letsGo);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadLevelChoiceFromTutorial();
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
                grid.setVisible(false);
                conceptText.setVisible(false);
                arrow.setVisible(true);
                swipeText.setVisible(true);
            } else if (alreadySwitched == 1) {
                background.setVisible(false);
                arrow.setVisible(false);
                swipeText.setVisible(false);
                letsGo.setVisible(true);
                engine.registerUpdateHandler(new TimerHandler(2.5f, new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        engine.unregisterUpdateHandler(pTimerHandler);
                        sceneService.loadFirstLevelFromTutorial();
                    }
                }));
            } else {
                sceneService.loadFirstLevelFromTutorial();
            }
            alreadySwitched++;
        }
        return false;
    }
}
