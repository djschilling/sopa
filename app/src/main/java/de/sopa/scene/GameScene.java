package de.sopa.scene;


import android.util.Log;
import de.sopa.MainActivity;
import de.sopa.model.GameField;
import de.sopa.model.GameFieldHandler;
import de.sopa.model.GameService;
import de.sopa.model.GameServiceImpl;
import de.sopa.model.Tile;
import de.sopa.model.TileType;
import de.sopa.observer.Observer;
import java.io.IOException;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameScene extends BaseScene implements Observer {

    private GameService gameService;
    private Sprite solvedSprite;
    private Sprite unsolvedSprite;
    private Entity tileGroup;
    private GameFieldHandler gameFieldHandler;
    private TileSprite[][] tileSprites;
    private ContinuousHoldDetector continuousHoldDetector;
    private int spacePerTile;
    private GameField gameField;

    public GameScene(Object o) {
        super(o);
    }

    public void addTiles() {
        tileGroup.detachChildren();
        Tile[][] field = gameService.getGameField().getField();
        int width = field.length;
        int heigth = field[0].length;
        spacePerTile = MainActivity.CAMERA_WIDTH / width;
        int tilesSceneStartY = getTileSceneStartY(spacePerTile);
        tileGroup.setPosition(0, tilesSceneStartY);
        int tilePositionY = 0;
        tileSprites = new TileSprite[width][heigth];
        for (int y = 0; y < heigth; y++) {
            int tilePositionX = 0;
            for (int x = 0; x < width; x++) {
                if (field[x][y].getShortcut() != 'n') {
                    TextureRegion pTextureRegion = resourcesManager.regionTileMap.get(field[x][y].getShortcut());
                    TileSprite tileSprite = new TileSprite(tilePositionX, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, vbom);
                    tileGroup.attachChild(tileSprite);
                    if (field[x][y].getTileType() == TileType.PUZZLE) {
                        tileSprites[x][y] = tileSprite;
                    }
                }
                tilePositionX += spacePerTile;
            }
            tilePositionY += spacePerTile;
        }
        tileGroup.attachChild(new Sprite(spacePerTile, spacePerTile, spacePerTile * (width - 2),
                spacePerTile * (width - 2), resourcesManager.tilesBorderRegion, vbom));

        setSolved(gameService.solvedPuzzle());

    }

    protected int getTileSceneStartY(int spacePerTile) {
        return (MainActivity.CAMERA_HEIGHT - (spacePerTile * gameService.getGameField().getField().length)) / 2;
    }

    public void moveTiles(boolean horizontal, int row, float moveSize, boolean moveOver) {
        if (row < 0) {
            return;
        }

        row++;
        if (horizontal) {
            if (row > tileSprites.length - 2) {
                return;
            }
            for (int x = 1; x < tileSprites.length - 1; x++) {
                TileSprite tileSprite = tileSprites[x][row];
                float toX = tileSprite.getStartX() + moveSize;
                tileSprite.setX(toX);
                if (moveOver) {
                    tileSprite.setStartX(toX);
                }

            }
        } else {
            if (row > tileSprites[0].length - 2) {
                return;
            }

            for (int y = 1; y < tileSprites[row].length - 1; y++) {
                TileSprite tileSprite = tileSprites[row][y];
                float toY = tileSprite.getStartY() + moveSize;
                tileSprite.setY(toY);
                if (moveOver) {
                    tileSprite.setStartY(toY);

                }
            }
        }

    }


    public void setSolved(boolean solved) {
        if (solved) {
            Log.i("Solved", "Solved");
            solvedSprite.setVisible(true);
            unsolvedSprite.setVisible(false);
        } else {
            Log.i("Solved", "False");
            unsolvedSprite.setVisible(true);
            solvedSprite.setVisible(false);
        }


    }

    @Override
    public void update() {
        addTiles();
    }

    @Override
    public void setSubject(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void createScene(Object o) {
        if(o!=null && o instanceof GameField) {
            this.gameField = (GameField)o;

        }
        initializeLogic();
        addBackground();
        addSolvedIcon();
        tileGroup = new Entity();
        attachChild(tileGroup);
        addTiles();
        gameFieldHandler = new GameFieldHandler();
        addButtons();
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

    private void initializeLogic() {
        gameService = new GameServiceImpl();
        if(gameField == null) {
            gameService.startGame();
        } else {
            gameService.startGame(gameField);
        }
        gameService.attach(this);
        final int widthPerTile = MainActivity.CAMERA_WIDTH / gameService.getGameField().getField().length;
        MyHoldDetector myHoldDetector = new MyHoldDetector(widthPerTile, getTileSceneStartY(widthPerTile) + widthPerTile, widthPerTile, this, gameService);
        continuousHoldDetector = new ContinuousHoldDetector(0, 100, 0.01f, myHoldDetector);
        registerUpdateHandler(continuousHoldDetector);
        setOnSceneTouchListener(continuousHoldDetector);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromGameScene();
    }

    @Override
    public void disposeScene() {
        //TODO: remove scene and children
    }
}
