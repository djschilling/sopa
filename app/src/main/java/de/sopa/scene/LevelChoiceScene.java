package de.sopa.scene;


import de.sopa.helper.LevelFileService;
import de.sopa.model.LevelInfo;
import java.util.List;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseQuartInOut;

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
    private List<LevelInfo> levelInfos;
    private float widthPerLevel;

    @Override
    public void createScene(Object o) {
        levelInfos = levelService.getLevelInfos();
        widthPerLevel =  (camera.getWidth() / COLUMNS);
        addLevelChooseTiles(levelInfos, widthPerLevel);
        addChangeLevelButtons();
        if(resourcesManager.musicIsPlaying == false){
            resourcesManager.menuMusic.play();
            resourcesManager.musicIsPlaying = true;
        }
    }

    private void addLevelChooseTiles(final List<LevelInfo> levelInfos, float widthPerLevel) {
        final LevelFileService levelFileService = new LevelFileService(resourcesManager.activity);
        for (int levelIndex = 0; levelIndex < levelInfos.size(); levelIndex++) {

            final int finalLevelIndex = levelIndex;
            ITextureRegion iTextureRegion = null;
            if (levelInfos.get(finalLevelIndex).isLocked()) {
                iTextureRegion = resourcesManager.levelChoiceRegionSW;
            } else {
                iTextureRegion = resourcesManager.levelChoiceRegion;
            }
            final ChoiceLevelSprite choiceLevelSprite = new ChoiceLevelSprite(getLevelSpriteX(widthPerLevel, levelIndex),
                    getLevelSpriteY(widthPerLevel, levelIndex), iTextureRegion, vbom, new ButtonSprite.OnClickListener() {
                @Override
                public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    sceneService.loadGameSceneFromLevelChoiceScene(levelFileService.getLevel(levelInfos.get(finalLevelIndex).getLevelId()));
                }

            }
            );

            choiceLevelSprite.setWidth(widthPerLevel);
            choiceLevelSprite.setHeight(widthPerLevel);
            registerTouchArea(choiceLevelSprite);
            attachChild(choiceLevelSprite);
            int fontOffset;
            if ((levelIndex + 1) > 99) {
                fontOffset = 105;
            } else if ((levelIndex + 1) > 9) {
                fontOffset = 120;
            } else {
                fontOffset = 150;
            }
            attachChild(new Text(getLevelSpriteX(widthPerLevel, levelIndex) + fontOffset, getLevelSpriteY(widthPerLevel, levelIndex) + 110,
                    resourcesManager.levelChoiceFont, String.valueOf(levelIndex + 1), vbom));


        }
    }


    private void addChangeLevelButtons() {
        screenCount = (levelInfos.size() / 12) + 1;
        currentScreen = 0;
        rightArrow = new ButtonSprite(camera.getWidth() * 0.93f - LEVEL_SELECT_ICON_WIDTH, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowRightRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (currentScreen < screenCount - 1) {
                    currentScreen++;
                    entityToFollow.registerEntityModifier(new MoveXModifier(0.5f, entityToFollow.getX(), currentScreen * camera.getWidth() + camera.getWidth() / 2, EaseQuartInOut.getInstance()));
                    if (currentScreen == screenCount - 1) {
                        rightArrow.setVisible(false);
                    }
                    leftArrow.setVisible(true);
                }
            }
        });
        leftArrow = new ButtonSprite(camera.getWidth() * 0.07f, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowLeftRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (currentScreen > 0) {
                    currentScreen--;
                    entityToFollow.registerEntityModifier(new MoveXModifier(0.5f, entityToFollow.getX(), currentScreen * camera.getWidth() + camera.getWidth() / 2, EaseQuartInOut.getInstance()));
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
        entityToFollow = new Entity(camera.getWidth() / 2, camera.getHeight() / 2);
        attachChild(entityToFollow);
        camera.setChaseEntity(entityToFollow);
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
