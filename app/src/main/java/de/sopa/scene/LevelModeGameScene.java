package de.sopa.scene;

import de.sopa.model.Level;

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
        if (level.getLevelInfo().getFewestMoves() == -1 || level.getMovesCount() < level.getMinimumMovesToSolve()){
           level.getLevelInfo().setFewestMoves(level.getMovesCount());
        }
        levelService.updateLevelInfo(level.getLevelInfo());
        int nextLevelId = level.getId() + 1;
        if(nextLevelId <= levelService.getLevelCount()) {
            Level levelById = levelService.getLevelById(nextLevelId);
            levelById.getLevelInfo().setLocked(false);
            levelService.updateLevelInfo(levelById.getLevelInfo());
        }
        sceneService.loadScoreScreen(level);

    }
}
