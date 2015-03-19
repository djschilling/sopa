package de.sopa.scene.game;


import de.sopa.model.game.GameService;
import de.sopa.model.game.GameServiceImpl;
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
    protected GameFieldView gameFieldView;
    private Level level;
    protected Level levelBackup;

    public GameScene(Object o) {
        super(o);
    }

    @Override
    public void createScene(Object o) {
        initializeLogic(o);
        calculateSpacePerTile(gameService.getLevel().getField().length - 2);
        levelBackup = new Level(gameService.getLevel());
        addBackground();
        addTiles();
        addButtons();
        addScoreText();
        registerTouchHandler();
        gameService.attach(this);
        resourcesManager.musicService.stopMusic();
    }



    @Override
    public void update() {
        updateTiles();
        scoreText.setText(String.valueOf(gameService.getLevel().getMovesCount()));
        if (gameService.solvedPuzzle()) {
            gameService.detach(this);
            this.clearTouchAreas();
            this.clearUpdateHandlers();
            baseScene.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
                public void onTimePassed(final TimerHandler pTimerHandler) {
                    baseScene.unregisterUpdateHandler(pTimerHandler);
                    onSolvedGame();
                }
            }));

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
        detachChild(gameFieldView);
        gameFieldView.addTiles();
        attachChild(gameFieldView);
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

        Text levelText = new Text(0, camera.getHeight() * 0.96f, resourcesManager.levelFont, "Level", vbom);
        levelText.setScaleCenter(0, 0);
        levelText.setScale(0.3f);
        attachChild(levelText);
        Text level = new Text(0, camera.getHeight() * 0.85f, resourcesManager.levelFont, String.valueOf(gameService.getLevel().getId()), vbom);
        attachChild(level);
    }

    private void registerTouchHandler() {
        GameSceneSingleMoveDetector gameSceneSingleMoveDetector = new GameSceneSingleMoveDetector(0, getTileSceneStartY() + spacePerTile ,spacePerTile, gameFieldView);
        continuousHoldDetector = new ContinuousHoldDetector(0, 100, 0.01f, gameSceneSingleMoveDetector);
        registerUpdateHandler(continuousHoldDetector);
        setOnSceneTouchListener(continuousHoldDetector);
    }


    protected abstract void addButtons();


    private void addBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void initializeLogic(Object o) {
        if (o != null && o instanceof Level) {
            level = (Level) o;
        }
        gameService = new GameServiceImpl();
        gameService.startGame(level);

    }

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
}
