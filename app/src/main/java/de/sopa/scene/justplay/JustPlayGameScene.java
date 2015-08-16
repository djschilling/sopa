package de.sopa.scene.justplay;

import de.sopa.model.game.TimeBasedGameServiceImpl;
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

    private final JustPlayLevel justPlayLevel;
    private TimeBasedGameServiceImpl timeBasedGameService;
    private Text leftTime;


    public JustPlayGameScene(JustPlayLevel justPlayLevel) {
        super(justPlayLevel.getLevel());
        this.justPlayLevel = justPlayLevel;
    }

    @Override
    protected void addCustomLabels() {
        leftTime = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.83f, resourcesManager.scoreFont, String.valueOf(timeBasedGameService.getRemainingTime()), 6, vbom);
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
    protected void initializeLogic() {
        timeBasedGameService = new TimeBasedGameServiceImpl(level, 10);
        gameService = timeBasedGameService;
        timeBasedGameService.start();
    }

    @Override
    public void onBackKeyPressed() {
        engine.registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                storyService.loadMenuSceneFromJustPlayGameScene();
            }
        }));


    }

    @Override
    public void onSolvedGame() {

        engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                storyService.loadJustPlayScoreSceneFromJustPlayScene(new JustPlayLevelResult(justPlayLevel.getLeftTime() - 12, gameService.getLevel().getMovesCount()));
            }
        }));

    }
    @Override
    public void onLostGame() {
        onBackKeyPressed();
    }

    @Override
    public void update() {
        super.update();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                leftTime.setText(String.valueOf(timeBasedGameService.getRemainingTime()));
            }
        }));
    }
}
