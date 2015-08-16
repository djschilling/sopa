package de.sopa.scene.game;


import de.sopa.model.game.GameService;
import de.sopa.model.game.Level;
import de.sopa.observer.Observer;
import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.util.color.Color;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public abstract class GameScene extends BaseScene implements Observer {

    protected GameService gameService;
    private ContinuousHoldDetector continuousHoldDetector;
    private float spacePerTile;
    private Text scoreText;
    private GameFieldView gameFieldView;
    protected final Level level;
    protected Level levelBackup;

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
    public void update() {

        updateTiles();
        scoreText.setText(String.valueOf(gameService.getLevel().getMovesCount()));
        if (gameService.solvedPuzzle()) {
            setOnSceneTouchListener(null);
            gameFieldView.setTubesState(1);
            gameService.detatch(this);
            baseScene.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
                public void onTimePassed(final TimerHandler pTimerHandler) {
                    baseScene.unregisterUpdateHandler(pTimerHandler);
                    onSolvedGame();
                }
            }));

        }
        if (gameService.lostLevel()) {
            onLostGame();
        }
    }


    private void addTiles() {
        float tilesSceneStartY = getTileSceneStartY();
        gameFieldView = new GameFieldView(0 - spacePerTile, tilesSceneStartY, spacePerTile,
                gameService, resourcesManager.regionTileMap, vbom, resourcesManager.tilesBorderRegion);
        gameFieldView.addTiles();
        attachChild(gameFieldView);

    }

    private void updateTiles() {
        baseScene.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                detachChild(gameFieldView);
                gameFieldView.addTiles();
                attachChild(gameFieldView);
            }
        }));
    }


    private void calculateSpacePerTile(int width) {
        spacePerTile = camera.getWidth() / width;
    }

    private float getTileSceneStartY() {
        return (camera.getHeight() - (spacePerTile * gameService.getLevel().getField().length)) / 2;
    }

    private void addScoreText() {

        scoreText = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.03f, resourcesManager.scoreFont, String.valueOf(gameService.getLevel().getMovesCount()), 4, vbom);
        attachChild(scoreText);
        Text score = new Text(camera.getWidth() * 0.67f, camera.getHeight() * 0.01f, resourcesManager.levelFont, "Current Moves", vbom);
        score.setScaleCenter(0, 0);
        score.setScale(0.3f);
        attachChild(score);

        Text minimumMovesScoreText = new Text(0, camera.getHeight() * 0.01f, resourcesManager.minMovesFont, "Min. Moves", vbom);
        minimumMovesScoreText.setScaleCenter(0, 0);
        minimumMovesScoreText.setScale(0.3f);
        attachChild(minimumMovesScoreText);
        Text minimumMovesScore = new Text(0, camera.getHeight() * 0.03f, resourcesManager.minMovesFont, String.valueOf(gameService.getLevel().getMinimumMovesToSolve()), vbom);
        attachChild(minimumMovesScore);
    }

    private void registerTouchHandler() {
        GameSceneSingleMoveDetector gameSceneSingleMoveDetector = new GameSceneSingleMoveDetector(0, getTileSceneStartY() + spacePerTile, spacePerTile, gameFieldView);
        continuousHoldDetector = new ContinuousHoldDetector(0, 100, 0.01f, gameSceneSingleMoveDetector);
        setOnSceneTouchListener(continuousHoldDetector);
    }


    protected abstract void addButtons();


    private void addBackground() {
        setBackground(new Background(Color.BLACK));
    }

    protected abstract void initializeLogic();

    @Override
    public abstract void onBackKeyPressed();

    @Override
    public void disposeScene() {
        final GameScene gameScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                gameScene.detachChildren();
            }
        }));
    }

    /**
     * is called when the game is solved
     */
    public abstract void onSolvedGame();

    /**
     * is called when the game is lost
     */
    public abstract void onLostGame();
}
