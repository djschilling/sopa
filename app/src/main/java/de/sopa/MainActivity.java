package de.sopa;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import de.sopa.model.FieldCreator;
import de.sopa.model.GameField;
import de.sopa.model.GameFieldService;
import de.sopa.model.GameService;
import de.sopa.model.GameServiceImpl;
import de.sopa.observer.GameScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.Map;


public class MainActivity extends SimpleBaseGameActivity {


    private static int CAMERA_HEIGHT;
    private static int CAMERA_WIDTH;
    private Map<Character, TextureRegion> regionMap;
    private GestureDetector gestureDetector;
    private GameServiceImpl gameService;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        gameService = new GameServiceImpl();
        gameService.startGame();
        int widthPerTile = CAMERA_WIDTH / gameService.getGameField().getField().length;
        gestureDetector = new GestureDetector(new SwipeGestureDetector(gameService, widthPerTile, widthPerTile, widthPerTile));

    }

    @Override
    protected Scene onCreateScene() {
        GameScene scene = new GameScene(regionMap, getVertexBufferObjectManager(), CAMERA_WIDTH);
        scene.setSubject(gameService);
        gameService.attach(scene);
        gameService.notifyAllObserver();
        TouchListener touchListener = new TouchListener(gestureDetector);
        scene.setOnSceneTouchListener(touchListener);
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
