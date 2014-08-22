package de.sopa;

import android.graphics.Point;
import android.view.Display;
import java.util.Map;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;


public class MainActivity extends SimpleBaseGameActivity {


    private static int CAMERA_HEIGHT;
    private static int CAMERA_WIDTH;
    private GameFieldService gameFieldService;
    private FieldCreator fieldCreator;
    private Map<Character, TextureRegion> regionMap;

    @Override
    protected Scene onCreateScene() {
        fieldCreator = new FieldCreator();

        gameFieldService = new GameFieldService(fieldCreator.fromString(new String[]{"nnnnnn",
                "noooon",
                "noouas",
                "nooion",
                "nooion",
                "nnnfnn"
        }));
        Scene scene = new Scene();

        Tile[][] field = gameFieldService.getField();
        int width = field.length;
        int heigth = field[0].length;
        int spacePerTile = CAMERA_WIDTH / width;
        int tilePositionY = 0;
        for (int y = 0; y < heigth; y++) {
            int tilePositionX = 0;
            for (int x = 0; x < width; x++) {
                if (field[x][y].getTileType() != TileType.NONE) {
                    TextureRegion pTextureRegion = regionMap.get(field[x][y].getShortcut());
                    Sprite tileSprite = new Sprite(tilePositionX, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, getVertexBufferObjectManager());
                    scene.attachChild(tileSprite);
                }
                tilePositionX += spacePerTile;
            }
            tilePositionY += spacePerTile;
        }
        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        CAMERA_WIDTH = size.x;
        CAMERA_HEIGHT = size.y;
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

    }

    @Override
    protected void onCreateResources() {
        TileResourceLoader tileResourceLoader = new TileResourceLoader(getTextureManager(), getAssets());
        regionMap = tileResourceLoader.getTileTextures();
    }
}
