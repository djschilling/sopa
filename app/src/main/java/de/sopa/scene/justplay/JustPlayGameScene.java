package de.sopa.scene.justplay;

import de.sopa.model.justplay.JustPlayLevel;
import de.sopa.model.justplay.JustPlayLevelResult;
import de.sopa.scene.game.GameScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlayGameScene extends GameScene {

    private final JustPlayLevel level;

    public JustPlayGameScene(JustPlayLevel level) {
        super(level.getLevel());
        this.level = level;
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
                storyService.loadJustPlayScoreSceneSceneFromJustPlaySceneScene(new JustPlayLevelResult(level.getLeftTime() - 12 , gameService.getLevel().getMovesCount()));
            }
        }));
    }
}
