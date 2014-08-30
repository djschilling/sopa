package de.sopa.scene;


import de.sopa.model.GameField;
import de.sopa.model.GameFieldHandler;
import de.sopa.model.GameService;
import de.sopa.model.GameServiceImpl;
import de.sopa.model.Tile;
import de.sopa.observer.Observer;
import java.io.IOException;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameScene extends BaseScene implements Observer {

    private GameService gameService;
    private Sprite solvedSprite;
    private Sprite unsolvedSprite;
    private GameFieldHandler gameFieldHandler;
    private ContinuousHoldDetector continuousHoldDetector;
    private float spacePerTile;
    private Text scoreText;
    private GameFieldView gameFieldView;
    private GameField gameField;

    public GameScene(Object o) {
        super(o);
    }

    @Override
    public void createScene(Object o) {
        initializeLogic(o);
        addBackground();
        addTiles();
        addSolvedIcon();
        setSolved(gameService.solvedPuzzle());
        addButtons();
        addScoreText();
        registerTouchHandler();
        gameService.attach(this);
        gameFieldHandler = new GameFieldHandler();
    }

    @Override
    public void update() {
        addTiles();
        setSolved(gameService.solvedPuzzle());
        scoreText.setText(String.valueOf(gameService.getGameField().getMovesCount()));
    }


    private void addTiles() {
        detachChild(gameFieldView);
        Tile[][] field = gameService.getGameField().getField();
        int width = field.length;
        spacePerTile = camera.getWidth() / width;
        float tilesSceneStartY = getTileSceneStartY(spacePerTile);
        gameFieldView = new GameFieldView(0, tilesSceneStartY, spacePerTile,
                gameService.getGameField().getField(), resourcesManager.regionTileMap, vbom, resourcesManager.tilesBorderRegion);
        attachChild(gameFieldView);

    }

    private float getTileSceneStartY(float spacePerTile) {
        return (camera.getHeight() - (spacePerTile * gameService.getGameField().getField().length)) / 2;
    }

    public void moveTiles(boolean horizontal, int row, float moveSize, boolean moveOver) {
        gameFieldView.moveTiles(horizontal, row, moveSize, moveOver);
    }


    private void setSolved(boolean solved) {
        if (solved) {
            solvedSprite.setVisible(true);
            unsolvedSprite.setVisible(false);
        } else {
            unsolvedSprite.setVisible(true);
            solvedSprite.setVisible(false);
        }
    }

    private void addScoreText() {
        scoreText = new Text(camera.getWidth() * 0.7f, camera.getHeight() * 0.01f, resourcesManager.scoreFont,String.valueOf(gameService.getGameField().getMovesCount()),4,vbom);
        attachChild(scoreText);
    }

    private void registerTouchHandler() {
        final float widthPerTile = camera.getWidth() / gameService.getGameField().getField().length;
        MyHoldDetector myHoldDetector = new MyHoldDetector(widthPerTile, getTileSceneStartY(widthPerTile) + widthPerTile, widthPerTile, this, gameService, camera.getWidth());
        continuousHoldDetector = new ContinuousHoldDetector(0, 100, 0.01f, myHoldDetector);
        registerUpdateHandler(continuousHoldDetector);
        setOnSceneTouchListener(continuousHoldDetector);
    }


    private void addSolvedIcon() {
        solvedSprite = new Sprite(0, 0, 50, 50, resourcesManager.regionTileMap.get('s'), vbom);
        unsolvedSprite = new Sprite(0, 0, 50, 50, resourcesManager.regionTileMap.get('i'), vbom);
        attachChild(solvedSprite);
        attachChild(unsolvedSprite);
    }

    private void addButtons() {
        Sprite sprite = new Sprite(0, camera.getHeight() * 0.8f, resourcesManager.saveButtonRegion, vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    try {
                        gameFieldHandler.saveGameField(gameService.getGameField());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        };
        registerTouchArea(sprite);
        setTouchAreaBindingOnActionDownEnabled(true);
        attachChild(sprite);
    }


    private void addBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void initializeLogic(Object o) {
        if (o != null && o instanceof GameField) {
            gameField = (GameField) o;
        }

        gameService = new GameServiceImpl();
        if (gameField == null) {
            gameService.startGame();
        } else {
            gameService.startGame(gameField);
        }
    }

    @Override
    public void onBackKeyPressed() {
        if(gameField != null && gameField instanceof GameField) {
            sceneService.loadLevelChoiceSceneFromGameScene();
        } else {
            sceneService.loadMenuSceneFromGameScene();

        }
    }

    @Override
    public void disposeScene() {
        final  GameScene gameScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                gameScene.detachChildren();
            }
        }));
    }
}
