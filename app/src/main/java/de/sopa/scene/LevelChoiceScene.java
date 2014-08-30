package de.sopa.scene;


import de.sopa.IOHandler;
import de.sopa.LevelFileHandler;
import de.sopa.manager.ResourcesManager;
import de.sopa.model.GameFieldHandler;
import org.andengine.input.touch.TouchEvent;

/**
 * @author Raphael Schilling
 */
public class LevelChoiceScene extends BaseScene {
    private static final int COLUMNS = 4;
    private static final int ROWS = 4;

    @Override
    public void createScene(Object o) {
        IOHandler ioHandler = new LevelFileHandler();
        final String[] strings = ioHandler.getAvailableLevels();
        final GameFieldHandler gameFieldHandler = new GameFieldHandler();
        float widthPerLevel = getWidhtPerTile();
        float heightPerLevel = getHeightPerTile();
        for (int levelIndex = 0; levelIndex < strings.length; levelIndex++) {
            ChoiceLevelSprite sprite = new ChoiceLevelSprite(getLevelSpriteX(widthPerLevel, levelIndex),
                    getLevelSpriteY(heightPerLevel, levelIndex), widthPerLevel, heightPerLevel, ResourcesManager.getInstance().levelChoiceRegion, vbom, strings[levelIndex]) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    sceneService.loadGameSceneFromLevelChoiceScene(gameFieldHandler.getGameField(this.getFilename()));
                    return true;
                }
            };
            registerTouchArea(sprite);
            setTouchAreaBindingOnActionDownEnabled(true);
            attachChild(sprite);
        }
    }

    private float getHeightPerTile() {
        return (camera.getHeight() / ROWS);
    }

    private float getWidhtPerTile() {
        return (camera.getWidth() / COLUMNS);
    }

    private float getLevelSpriteY(float heightPerLevel, int levelIndex) {
        return levelIndex / COLUMNS * heightPerLevel;
    }

    private float getLevelSpriteX(float widthPerLevel, int i) {
        return (i % COLUMNS) * widthPerLevel;
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromLevelChoiceScene();
    }


    @Override
    public void disposeScene() {
        this.detachChildren();
    }
}
