package de.sopa;

import android.graphics.Point;
import android.view.Display;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.Map;


public class MainActivity extends SimpleBaseGameActivity {


    private static int CAMERA_HEIGHT;
    private static int CAMERA_WIDTH;
    private GameFieldService gameFieldService;
    private FieldCreator fieldCreator;
    private Map<Character, TextureRegion> regionMap;

    @Override
    protected Scene onCreateScene() {
        fieldCreator = new FieldCreator();

        gameFieldService = new GameFieldService(fieldCreator.generateSolvedField(6,6));
        GameScene scene = new GameScene(regionMap, getVertexBufferObjectManager());
        scene.addTiles(gameFieldService.getField(), CAMERA_WIDTH);

        return scene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
        size.set(display.getWidth(), display.getHeight());
            } else {
        display.getSize(size);
        }
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
