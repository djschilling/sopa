package de.sopa.scene;


import de.sopa.LevelFileHandler;
import de.sopa.manager.ResourcesManager;
import de.sopa.model.GameFieldHandler;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * @author Raphael Schilling
 */
public class LevelChoiceScene extends BaseScene {
    private static final int COLUMNS = 3;
    private int currentScreen;
    private int screenCount;
    private Sprite leftArrow;
    private Sprite rightArrow;
    private Entity entityToFollow;
    private static final int LEVEL_SELECT_ICON_WIDTH = 300;

    @Override
    public void createScene(Object o) {
        final String[] availableLevels = getAvailableLevels();
        final float widthPerLevel = getWidhtPerTile();
        addLevelChooseTiles(availableLevels, widthPerLevel);
        screenCount = (availableLevels.length / 12) + 1;
        currentScreen = 0;
        addChangeLevelButtons();
        entityToFollow = new Entity(camera.getWidth()/2, camera.getHeight()/2);
        attachChild(entityToFollow);
        camera.setChaseEntity(entityToFollow);

    }

    private void addChangeLevelButtons() {
        rightArrow = new ButtonSprite(camera.getWidth() * 0.93f - LEVEL_SELECT_ICON_WIDTH, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowRightRegion, vbom, new ButtonSprite.OnClickListener(){
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if(currentScreen < screenCount - 1) {
                    currentScreen++;
                    entityToFollow.registerEntityModifier(new MoveXModifier(0.5f, entityToFollow.getX(), currentScreen * camera.getWidth() + camera.getWidth() / 2));
                    if(currentScreen == screenCount -1) {
                        rightArrow.setVisible(false);
                    }
                    leftArrow.setVisible(true);
                }
            }
        });
        leftArrow = new ButtonSprite(camera.getWidth() * 0.07f, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowLeftRegion, vbom, new ButtonSprite.OnClickListener(){
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (currentScreen > 0) {
                        currentScreen--;
                        entityToFollow.registerEntityModifier(new MoveXModifier(0.5f, entityToFollow.getX(), currentScreen * camera.getWidth() + camera.getWidth() / 2));
                        if (currentScreen == 0) {
                            leftArrow.setVisible(false);
                        }
                        rightArrow.setVisible(true);
                    }
            }
        });

        leftArrow.setVisible(false);
        if (screenCount == 1) {
            rightArrow.setVisible(false);
        }
        HUD arrowHud = new HUD();
        arrowHud.attachChild(leftArrow);
        arrowHud.attachChild(rightArrow);
        arrowHud.registerTouchArea(leftArrow);
        arrowHud.registerTouchArea(rightArrow);
        camera.setHUD(arrowHud);
    }

    private String[] getAvailableLevels() {
        return new LevelFileHandler().getAvailableLevels();
    }

    private void addLevelChooseTiles(final String[] strings, float widthPerLevel) {
        final GameFieldHandler gameFieldHandler = new GameFieldHandler();
        for (int levelIndex = 0; levelIndex < strings.length; levelIndex++) {
            ChoiceLevelSprite sprite = new ChoiceLevelSprite(getLevelSpriteX(widthPerLevel, levelIndex),
                    getLevelSpriteY(widthPerLevel, levelIndex), widthPerLevel, widthPerLevel, ResourcesManager.getInstance().levelChoiceRegion, vbom, strings[levelIndex]) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
                        sceneService.loadGameSceneFromLevelChoiceScene(gameFieldHandler.getGameField(this.getFilename()));
                        return true;
                    }
                    return false;
                }
            };
            registerTouchArea(sprite);
            attachChild(sprite);
        }
    }

    private float getWidhtPerTile() {
        return (camera.getWidth() / COLUMNS);
    }

    private float getLevelSpriteY(float heightPerLevel, int levelIndex) {
        return ((int) (levelIndex % 12 / COLUMNS)) * heightPerLevel;
    }

    private float getLevelSpriteX(float widthPerLevel, int levelIndex) {
        return (levelIndex % 12 % COLUMNS) * widthPerLevel + widthPerLevel * COLUMNS * (int) (levelIndex / 12);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromLevelChoiceScene();
    }


    @Override
    public void disposeScene() {
        camera.setChaseEntity(null);
        camera.setCenter(camera.getWidth() / 2, camera.getHeight() / 2);
        rightArrow.setVisible(false);
        leftArrow.setVisible(false);
        camera.setHUD(null);
        final LevelChoiceScene levelChoiceScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                levelChoiceScene.detachChildren();
            }
        }));
    }
}
