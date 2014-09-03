package de.sopa.scene;

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
        sceneService.loadScoreScreen(gameService.getLevel());
        gameService.getLevel().getLevelInfo().setLocked(false);

    }
}
