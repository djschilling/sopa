package de.sopa.scene.game;

import de.sopa.model.game.GameService;
import de.sopa.model.game.Level;

import de.sopa.observer.GameSceneObserver;

import de.sopa.scene.BaseScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;

import org.andengine.input.touch.detector.ContinuousHoldDetector;

import org.andengine.util.color.Color;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public abstract class GameScene extends BaseScene implements GameSceneObserver {

    protected GameService gameService;
    protected float spacePerTile;
    private Text scoreText;
    private GameFieldView gameFieldView;
    protected final Level level;
    protected Level levelBackup;
    protected Background background;

    public GameScene(Level level) {

        super();
        this.level = level;
        initializeLogic();
        calculateSpacePerTile(gameService.getLevel().getField().length - 2);
        levelBackup = new Level(gameService.getLevel());
        addBackground();
        addTiles();
        addButtons();
        addScoreText();
        addCustomLabels();
        registerTouchHandler();
        resourcesManager.musicService.stopMusic();
    }

    protected abstract void addCustomLabels();


    @Override
    public void updateGameScene() {

        if (gameService.solvedPuzzle()) {
            setOnSceneTouchListener(null);
            gameService.detatch(this);
            baseScene.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                        @Override
                        public void onTimePassed(final TimerHandler pTimerHandler) {

                            baseScene.unregisterUpdateHandler(pTimerHandler);
                            onSolvedGame();
                        }
                    }));
        }

        scoreText.setText(String.valueOf(gameService.getLevel().getMovesCount()));

        updateTiles(gameService.solvedPuzzle());
    }


    private void addTiles() {

        float tilesSceneStartY = getTileSceneStartY();
        gameFieldView = new GameFieldView(0 - spacePerTile, tilesSceneStartY, spacePerTile, gameService,
                resourcesManager.regionTileMap, vbom, resourcesManager.tilesBorderRegion);
        gameFieldView.addTiles(false);
        attachChild(gameFieldView);
    }


    private void updateTiles(final boolean finished) {

        baseScene.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        detachChild(gameFieldView);
                        gameFieldView.addTiles(finished);
                        attachChild(gameFieldView);
                    }
                }));
    }


    private void calculateSpacePerTile(int width) {

        spacePerTile = camera.getWidth() / width;
    }


    protected float getTileSceneStartY() {

        return (camera.getHeight() - (spacePerTile * gameService.getLevel().getField().length)) / 2;
    }


    private void addScoreText() {

        scoreText = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.03f, resourcesManager.scoreFont,
                String.valueOf(gameService.getLevel().getMovesCount()), 4, vbom);
        attachChild(scoreText);

        Text score = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.01f, resourcesManager.levelFont,
                "Current Moves", vbom);
        score.setScaleCenter(0, 0);
        score.setScale(0.3f);
        attachChild(score);

        Text minimumMovesScoreText = new Text(0, camera.getHeight() * 0.01f, resourcesManager.minMovesFont,
                "Min. Moves", vbom);
        minimumMovesScoreText.setScaleCenter(0, 0);
        minimumMovesScoreText.setScale(0.3f);
        attachChild(minimumMovesScoreText);

        Text minimumMovesScore = new Text(0, camera.getHeight() * 0.03f, resourcesManager.minMovesFont,
                String.valueOf(gameService.getLevel().getMinimumMovesToSolve()), vbom);
        attachChild(minimumMovesScore);
    }


    private void registerTouchHandler() {

        GameSceneSingleMoveDetector gameSceneSingleMoveDetector = new GameSceneSingleMoveDetector(0,
                getTileSceneStartY() + spacePerTile, spacePerTile, gameFieldView, gameService);
        ContinuousHoldDetector continuousHoldDetector = new ContinuousHoldDetector(0, 100, 0.01f,
                gameSceneSingleMoveDetector);
        setOnSceneTouchListener(continuousHoldDetector);
    }


    protected abstract void addButtons();


    private void addBackground() {

        background = new Background(Color.BLACK);
        setBackground(background);
    }


    protected abstract void initializeLogic();


    @Override
    public abstract void onBackKeyPressed();


    @Override
    public void disposeScene() {

        final GameScene gameScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        gameScene.detachChildren();
                    }
                }));
    }


    /**
     * is called when the game is solved.
     */
    public abstract void onSolvedGame();


    /**
     * is called when the game is lost.
     */
    public abstract void onLostGame();
}
