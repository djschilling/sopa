package de.sopa.scene.game;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.LevelDestroyer;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlayGameScene extends GameScene {

    private final LevelCreator levelCreator;
    private final LevelDestroyer levelDestroyer;

    public JustPlayGameScene(Object o) {
        super(o);
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
        sceneService.loadMenuSceneFromGameScene();
    }

    @Override
    public void onSolvedGame() {
        engine.registerUpdateHandler(new TimerHandler(1.5f,new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                sceneService.loadJustPlaySceneSceneFromJustPlaySceneScene(levelDestroyer.destroyField(levelCreator.generateSolvedField(6, 6), 2, 4));
            }
        }));
    }
}
