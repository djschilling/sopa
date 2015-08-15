package de.sopa.scene.game;


import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class LevelModeGameScene extends GameScene {
    private boolean leaveScene;
    private ButtonSprite restartButton;

    public LevelModeGameScene(Level level) {
        super(level);
        leaveScene = false;
    }

    @Override
    protected void addCustomLabels() {
        Text levelText = new Text(0, camera.getHeight() * 0.96f, resourcesManager.levelFont, "Level", vbom);
        levelText.setScaleCenter(0, 0);
        levelText.setScale(0.3f);
        attachChild(levelText);
        Text level = new Text(0, camera.getHeight() * 0.85f, resourcesManager.levelFont, String.valueOf(gameService.getLevel().getId()), vbom);
        attachChild(level);
    }

    @Override
    protected void addButtons() {
        restartButton = new ButtonSprite(camera.getWidth() - 300, (camera.getHeight() - 300), resourcesManager.restartRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                storyService.loadGameSceneFromGameScene(levelBackup);
            }
        });
        restartButton.setWidth(300);
        restartButton.setHeight(300);
        registerTouchArea(restartButton);
        attachChild(restartButton);
    }

    @Override
    public void onBackKeyPressed() {
        if(!leaveScene) {
            storyService.loadLevelChoiceSceneFromGameScene();
        }
    }

    public void onSolvedGame() {
        restartButton.setVisible(false);
        Level level = gameService.getLevel();
        final LevelResult levelResult = levelService.calculateLevelResult(level);
        levelService.persistLevelResult(levelResult);
        int nextLevelId = level.getId() + 1;
        levelService.unlockLevel(nextLevelId);
        this.leaveScene = true;
        engine.registerUpdateHandler(new TimerHandler(1.5f,new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                storyService.loadScoreScreen(levelResult);
            }
        }));

    }
}
