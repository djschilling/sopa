package de.sopa.scene;


import de.sopa.helper.LevelCreator;
import de.sopa.model.GameFieldDestroyer;
import de.sopa.model.GameService;
import de.sopa.model.GameServiceImpl;
import de.sopa.model.Level;
import de.sopa.observer.Observer;
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
    private ContinuousHoldDetector continuousHoldDetector;
    private float spacePerTile;
    private Text scoreText;
    private GameFieldView gameFieldView;
    private Level level;

    public GameScene(Object o) {
        super(o);
    }

    @Override
    public void createScene(Object o) {
        initializeLogic(o);
        calculateSpacePerTile(gameService.getLevel().getField().length);
        addBackground();
        addTiles();
        addSolvedIcon();
        setSolved(gameService.solvedPuzzle());
        addButtons();
        addScoreText();
        registerTouchHandler();
        gameService.attach(this);
    }

    @Override
    public void update() {
        updateTiles();
        setSolved(gameService.solvedPuzzle());
        scoreText.setText(String.valueOf(gameService.getLevel().getMovesCount()));
    }


    private void addTiles() {
        float tilesSceneStartY = getTileSceneStartY();
        gameFieldView = new GameFieldView(0, tilesSceneStartY, spacePerTile,
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
        scoreText = new Text(camera.getWidth() * 0.7f, camera.getHeight() * 0.01f, resourcesManager.scoreFont, String.valueOf(gameService.getLevel().getMovesCount()), 4, vbom);
        attachChild(scoreText);
    }

    private void registerTouchHandler() {
        final float widthPerTile = camera.getWidth() / gameService.getLevel().getField().length;
        MyHoldDetector myHoldDetector = new MyHoldDetector(widthPerTile, getTileSceneStartY() + widthPerTile, widthPerTile, gameFieldView, gameService, camera.getWidth());
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
        Sprite saveLevelButton = new Sprite(0, camera.getHeight() * 0.8f, resourcesManager.saveButtonRegion, vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    levelService.createLevel(gameService.getLevel());
                }
                return true;
            }
        };
        registerTouchArea(saveLevelButton);
        setTouchAreaBindingOnActionDownEnabled(true);
        attachChild(saveLevelButton);
    }


    private void addBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void initializeLogic(Object o) {
        if (o != null && o instanceof Level) {
            level = (Level) o;
        }
        gameService = new GameServiceImpl();
        if (level == null) {
            level = new LevelCreator().generateSolvedField(6, 6);
            new GameFieldDestroyer().destroyField(level, 3, 5);
            gameService.startGame(level);
            level = null;
        } else {
            gameService.startGame(level);
        }

    }

    @Override
    public void onBackKeyPressed() {
        if (level != null && level instanceof Level) {
            sceneService.loadLevelChoiceSceneFromGameScene();
        } else {
            sceneService.loadMenuSceneFromGameScene();

        }
    }

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
}
