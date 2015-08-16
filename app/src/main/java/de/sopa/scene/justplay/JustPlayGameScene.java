package de.sopa.scene.justplay;

import de.sopa.model.justplay.JustPlayLevel;
import de.sopa.model.justplay.JustPlayLevelResult;
import de.sopa.scene.game.GameScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlayGameScene extends GameScene {

    private final JustPlayLevel level;
    private Text leftTime;

    public JustPlayGameScene(JustPlayLevel level) {
        super(level.getLevel());
        this.level = level;
    }

    @Override
    protected void addCustomLabels() {
        leftTime = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.83f, resourcesManager.scoreFont, String.valueOf(12), 6, vbom);
        attachChild(leftTime);
        Text leftTimeText= new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.81f, resourcesManager.levelFont, "Left Time", vbom);
        leftTimeText.setScaleCenter(0, 0);
        leftTimeText.setScale(0.3f);
        attachChild(leftTimeText);

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
                storyService.loadJustPlayScoreSceneFromJustPlayScene(new JustPlayLevelResult(level.getLeftTime() - 12, gameService.getLevel().getMovesCount()));
            }
        }));
    }
}
