package de.sopa.scene.justplay;

import de.sopa.model.game.GameServiceImpl;
import de.sopa.model.game.Level;
import de.sopa.model.game.TimeBasedGameService;
import de.sopa.model.game.TimeBasedGameServiceImpl;
import de.sopa.model.justplay.JustPlayLevel;
import de.sopa.model.justplay.JustPlayLevelResult;
import de.sopa.observer.JustPlaySceneObserver;
import de.sopa.scene.game.GameScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlayGameScene extends GameScene implements JustPlaySceneObserver {

    private final JustPlayLevel justPlayLevel;
    private TimeBasedGameService timeBasedGameService;
    private Text leftTime;
    private boolean leaveScene;


    public JustPlayGameScene(JustPlayLevel justPlayLevel) {
        super(justPlayLevel.getLevel());
        this.justPlayLevel = justPlayLevel;
        leaveScene = false;
        timeBasedGameService = new TimeBasedGameServiceImpl(justPlayLevel.getLeftTime());
        timeBasedGameService.start();
        timeBasedGameService.attach(this);
        leftTime.setText(String.valueOf(justPlayLevel.getLeftTime()));
    }

    public JustPlayGameScene(TimeBasedGameService timeBasedGameService, JustPlayLevel justPlayLevel) {
        super(justPlayLevel.getLevel());
        this.justPlayLevel = justPlayLevel;
        leaveScene = false;
        this.timeBasedGameService = timeBasedGameService;
        timeBasedGameService.attach(this);
        leftTime.setText(String.valueOf(justPlayLevel.getLeftTime()));
    }

    @Override
    protected void addCustomLabels() {
        leftTime = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.83f, resourcesManager.scoreFont, "", 6, vbom);
        attachChild(leftTime);
        Text leftTimeText= new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.81f, resourcesManager.levelFont, "Left Time", vbom);
        leftTimeText.setScaleCenter(0, 0);
        leftTimeText.setScale(0.3f);
        attachChild(leftTimeText);

    }

    @Override
    protected void addButtons() {
        ButtonSprite restartButton = new ButtonSprite(camera.getWidth() - 300, (camera.getHeight() - 300), resourcesManager.restartRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                storyService.loadJustPlaySceneFromJustPlayScene(timeBasedGameService, new JustPlayLevel(timeBasedGameService.getRemainingTime(), levelBackup));
            }
        });
        restartButton.setWidth(300);
        restartButton.setHeight(300);
        registerTouchArea(restartButton);
        attachChild(restartButton);

    }

    @Override
    protected void initializeLogic() {
        gameService = new GameServiceImpl(this.level);
        gameService.attach(this);
    }

    @Override
    public void onBackKeyPressed() {
        if(!leaveScene) {
            leaveScene = true;
            engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    engine.unregisterUpdateHandler(pTimerHandler);
                    storyService.loadMenuSceneFromJustPlayGameScene();
                }
            }));
        }


    }

    @Override
    public void onSolvedGame() {
        timeBasedGameService.stop();
        if(!leaveScene) {
            leaveScene = true;
            engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    engine.unregisterUpdateHandler(pTimerHandler);
                    storyService.loadJustPlayScoreSceneFromJustPlayScene(new JustPlayLevelResult(timeBasedGameService.getRemainingTime(), gameService.getLevel().getMovesCount()));
                }
            }));
        }

    }
    @Override
    public void onLostGame() {
        if(!leaveScene) {
            leaveScene = true;
            engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {
                @Override
                public void onTimePassed(TimerHandler pTimerHandler) {
                    engine.unregisterUpdateHandler(pTimerHandler);
                    storyService.loadJustPlayScoreSceneFromJustPlayScene(new JustPlayLevelResult(-1, gameService.getLevel().getMovesCount()));
                }
            }));
        }
    }

    @Override
    public void disposeScene() {
        super.disposeScene();
        timeBasedGameService.detatch(this);
    }

    @Override
    public void updateJustPlayScene() {
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                leftTime.setText(String.valueOf(timeBasedGameService.getRemainingTime()));
            }
        }));
        if(timeBasedGameService.getRemainingTime() == 0 && !gameService.solvedPuzzle()){
            onLostGame();
        }
    }
}
