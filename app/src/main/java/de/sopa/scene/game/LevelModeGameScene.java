package de.sopa.scene.game;


import android.view.animation.Animation;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class LevelModeGameScene extends GameScene {
    private boolean leaveScene;

    public LevelModeGameScene(Object o) {
        super(o);
        leaveScene = false;
    }

    @Override
    protected void addButtons() {
        ButtonSprite restartButton = new ButtonSprite(camera.getWidth() - 300, (camera.getHeight() - 300), resourcesManager.restartRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadGameSceneFromGameScene(levelBackup);
            }
        });
        restartButton.setWidth(300);
        restartButton.setHeight(300);
        registerTouchArea(restartButton);
        attachChild(restartButton);
    }

    @Override
    public void onBackKeyPressed() {
        leaveScene = true;
        sceneService.loadLevelChoiceSceneFromGameScene();
    }

    public void onSolvedGame() {
        Level level = gameService.getLevel();
        final LevelResult levelResult = levelService.calculateLevelResult(level);
        levelService.persistLevelResult(levelResult);
        int nextLevelId = level.getId() + 1;
        levelService.unlockLevel(nextLevelId);
        engine.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!leaveScene) {
                    engine.unregisterUpdateHandler(pTimerHandler);
                    sceneService.loadScoreScreen(levelResult);
                }
            }
        }));

    }
}
