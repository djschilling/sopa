package com.sopaapp.scene.justplay;

import com.sopaapp.drawing.FlashEntityModifier;

import com.sopaapp.model.game.GameServiceImpl;
import com.sopaapp.model.game.TimeBasedGameService;
import com.sopaapp.model.game.TimeBasedGameServiceImpl;
import com.sopaapp.model.justplay.JustPlayLevel;
import com.sopaapp.model.justplay.JustPlayLevelResult;

import com.sopaapp.observer.JustPlaySceneObserver;

import com.sopaapp.scene.game.GameScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;


/**
 * David Schilling - davejs92@gmail.com.
 */
class JustPlayGameScene extends GameScene implements JustPlaySceneObserver {

    private TimeBasedGameService timeBasedGameService;
    private Text leftTime;
    private boolean leaveScene;
    private ButtonSprite restartButton;
    private Rectangle gameViewBackground;

    JustPlayGameScene(JustPlayLevel justPlayLevel) {

        super(justPlayLevel.getLevel());
        leaveScene = false;
        initializeWarningFlash();
        timeBasedGameService = new TimeBasedGameServiceImpl(justPlayLevel.getLeftTime());
        timeBasedGameService.start();
        timeBasedGameService.attach(this);
        leftTime.setText(String.valueOf(justPlayLevel.getLeftTime()));
    }


    JustPlayGameScene(TimeBasedGameService timeBasedGameService, JustPlayLevel justPlayLevel) {

        super(justPlayLevel.getLevel());
        leaveScene = false;
        initializeWarningFlash();
        this.timeBasedGameService = timeBasedGameService;

        timeBasedGameService.attach(this);
        leftTime.setText(String.valueOf(justPlayLevel.getLeftTime()));
        checkRestartedLevel(timeBasedGameService);
    }

    private void initializeWarningFlash() {

        gameViewBackground = new Rectangle(0f, getTileSceneStartY() + spacePerTile, camera.getWidth(),
                camera.getWidth(), vbom);
        gameViewBackground.setColor(1f, 1f, 1f, 0f);
        attachChild(gameViewBackground);
    }


    private void checkRestartedLevel(TimeBasedGameService timeBasedGameService) {

        if (timeBasedGameService.getRemainingTime() == 0) {
            if (gameService.solvedPuzzle()) {
                onSolvedGame();
            } else {
                onLostGame();
            }
        }
    }


    @Override
    protected void addCustomLabels() {

        leftTime = new Text(0, camera.getHeight() * 0.83f, resourcesManager.scoreFont, "", 6, vbom);
        attachChild(leftTime);

        Text leftTimeText = new Text(0, camera.getHeight() * 0.81f, resourcesManager.levelFont, "Left Time", vbom);
        leftTimeText.setScaleCenter(0, 0);
        leftTimeText.setScale(0.3f);
        attachChild(leftTimeText);
    }


    @Override
    protected void addButtons() {

        restartButton = new ButtonSprite(camera.getWidth() - 300, (camera.getHeight() - 300),
                resourcesManager.restartRegion, vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        storyService.loadJustPlaySceneFromJustPlayScene(timeBasedGameService,
                            new JustPlayLevel(timeBasedGameService.getRemainingTime(), levelBackup));
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

        if (!leaveScene) {
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
        restartButton.setVisible(false);

        if (!leaveScene) {
            restartButton.setEnabled(false);
            leaveScene = true;
            engine.registerUpdateHandler(new TimerHandler(1f, new ITimerCallback() {

                        @Override
                        public void onTimePassed(TimerHandler pTimerHandler) {

                            engine.unregisterUpdateHandler(pTimerHandler);
                            storyService.loadJustPlayScoreSceneFromJustPlayScene(
                                new JustPlayLevelResult(timeBasedGameService.getRemainingTime(),
                                    gameService.getLevel().getMovesCount(),
                                    gameService.getLevel().getMinimumMovesToSolve()));
                        }
                    }));
        }
    }


    @Override
    public void onLostGame() {

        if (!leaveScene) {
            restartButton.setEnabled(false);
            leaveScene = true;
            storyService.loadJustPlayLostSceneFromJustPlayScene(new JustPlayLevelResult(-1,
                    gameService.getLevel().getMovesCount(), gameService.getLevel().getMinimumMovesToSolve()));
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

                        if (timeBasedGameService.getRemainingTime() <= 5) {
                            backgroundFlash();
                        }
                    }
                }));

        if (timeBasedGameService.getRemainingTime() == 0 && !gameService.solvedPuzzle()) {
            onLostGame();
        }
    }


    private void backgroundFlash() {

        gameViewBackground.registerEntityModifier(new FlashEntityModifier());
    }
}
