package de.sopa.scene.game;

import de.sopa.model.Level;
import de.sopa.scene.game.GameScene;
import org.andengine.entity.sprite.ButtonSprite;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelModeGameScene extends GameScene {
    public LevelModeGameScene(Object o) {
        super(o);
    }

    @Override
    protected void addButtons() {
        if(this instanceof LevelModeGameScene) {
            ButtonSprite restartButton = new ButtonSprite(camera.getWidth() - 300, (float) (camera.getHeight() - 300), resourcesManager.restartRegion, vbom, new ButtonSprite.OnClickListener() {
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
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadLevelChoiceSceneFromGameScene();
    }

    public void onSolvedGame() {
        Level level = gameService.getLevel();
        levelService.updateFewestMoves(level);
        int nextLevelId = level.getId() + 1;
        levelService.unlockLevel(nextLevelId);
        sceneService.loadScoreScreen(level);
    }
}
