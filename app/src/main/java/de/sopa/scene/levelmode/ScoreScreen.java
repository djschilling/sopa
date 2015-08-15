package de.sopa.scene.levelmode;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import de.sopa.scene.BaseScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class ScoreScreen extends BaseScene {

    public ScoreScreen(final LevelResult levelResult) {
        super();
        final Level level = levelService.getLevelById(levelResult.getLevelId());
        String levelCompleteString = levelResult.getLevelId() + ". Level\nComplete";
        Text levelCompleteTextShape = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.05), resourcesManager.movesScoreFont, levelCompleteString, vbom);
        levelCompleteTextShape.setScaleCenter(0, 0);
        levelCompleteTextShape.setScale(2);
        attachChild(levelCompleteTextShape);
        attachChild(new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.4), resourcesManager.movesScoreFont, "Your moves:          \t" + levelResult.getMoveCount() + "\nMoves for 3 Stars:\t" + level.getMinimumMovesToSolve(), vbom));
        attachChild(new Sprite(0, (float) (camera.getHeight() * 0.55), 400, 400, resourcesManager.starRegion, vbom));
        attachChild(new Sprite((float) (camera.getWidth() * 0.64), (float) (camera.getHeight() * 0.55), 400, 400, (levelResult.getStars() == 3) ? resourcesManager.starRegion : resourcesManager.starSWRegion, vbom));
        attachChild(new Sprite((camera.getWidth() / 2 - 200), (float) (camera.getHeight() * 0.6), 400, 400, (levelResult.getStars() >= 2) ? resourcesManager.starRegion : resourcesManager.starSWRegion, vbom));
        ButtonSprite choiceLevelButton = new ButtonSprite(0, (camera.getHeight() - 400), resourcesManager.backToChoiceRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                storyService.loadLevelChoiceSceneFromScoreScene();
            }
        });
        ButtonSprite nextLevelButton = new ButtonSprite((float) (camera.getWidth() * 0.64), (camera.getHeight() - 400), resourcesManager.nextLevelRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                int nextLevelId = levelResult.getLevelId() + 1;
                if (nextLevelId > levelService.getLevelCount()) {
                    storyService.loadLevelChoiceSceneFromScoreScene();
                } else {
                    storyService.loadGameSceneFromScoreScene(levelService.getLevelById(nextLevelId));
                }
            }
        });

        ButtonSprite levelAgainButton = new ButtonSprite((camera.getWidth() / 2 - 200), (camera.getHeight() - 400), resourcesManager.backToMenuRegionA, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                storyService.loadGameSceneFromScoreScene(level);
            }
        });
        registerTouchArea(choiceLevelButton);
        registerTouchArea(nextLevelButton);
        registerTouchArea(levelAgainButton);
        attachChild(choiceLevelButton);
        attachChild(levelAgainButton);
        attachChild(nextLevelButton);
    }

    @Override
    public void onBackKeyPressed() {
        storyService.loadMenuSceneFromScoreScene();
    }

    @Override
    public void disposeScene() {
        final ScoreScreen scoreScreen = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                scoreScreen.detachChildren();
            }
        }));
    }
}
