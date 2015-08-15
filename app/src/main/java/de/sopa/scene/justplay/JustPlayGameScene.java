package de.sopa.scene.justplay;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.Level;
import de.sopa.model.game.LevelDestroyer;
import de.sopa.scene.game.GameScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlayGameScene extends GameScene {

    private final LevelCreator levelCreator;
    private final LevelDestroyer levelDestroyer;

    public JustPlayGameScene(Level level) {
        super(level);
        this.levelCreator = new LevelCreator();
        this.levelDestroyer = new LevelDestroyer();
    }

    @Override
    protected void addCustomLabels() {

    }

    @Override
    protected void addButtons() {

    }

    @Override
    public void onBackKeyPressed() {
        storyService.loadMenuSceneFromJustPlayGameScene();
    }

    @Override
    public void onSolvedGame() {
        engine.registerUpdateHandler(new TimerHandler(1.5f,new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                storyService.loadJustPlayScoreSceneSceneFromJustPlaySceneScene(levelDestroyer.destroyField(levelCreator.generateSolvedField(6, 6), 2, 4));
            }
        }));
    }
}
