package de.sopa.scene.game;

import de.sopa.model.Level;
import de.sopa.scene.game.GameScene;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelModeGameScene extends GameScene {
    public LevelModeGameScene(Object o) {
        super(o);
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
