package de.sopa.scene.choicelevel;


import de.sopa.model.game.LevelInfo;
import de.sopa.model.levelchoice.LevelChoiceService;
import de.sopa.model.levelchoice.LevelChoiceServiceImpl;
import de.sopa.observer.Observer;
import de.sopa.scene.BaseScene;
import java.util.List;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.modifier.ease.EaseQuartInOut;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelChoiceScene extends BaseScene implements Observer {
    private static final int COLUMNS = 3;
    private int screenCount;
    private Sprite leftArrow;
    private Sprite rightArrow;
    private Entity entityToFollow;
    private static final int LEVEL_SELECT_ICON_WIDTH = 300;
    private List<LevelInfo> levelInfos;
    private HUD arrowHud;
    private LevelChoiceService levelChoiceService;

    @Override
    public void createScene(Object o) {
        levelInfos = levelService.getLevelInfos();
        levelChoiceService = new LevelChoiceServiceImpl(levelInfos.size(), 12);
        levelChoiceService.attach(this);
        float widthPerLevel = (camera.getWidth() / COLUMNS);
        addLevelChooseTiles(levelInfos, widthPerLevel);
        addChangeLevelButtons();
        moveToLastUnlocked();
        resourcesManager.musicService.playMusic();
    }

    private void moveToLastUnlocked() {
        LevelInfo lastUnlocked = levelService.getLastUnlocked();
        int screenToJumpTo = (int)(lastUnlocked.getLevelId() - 0.1) / 12;
        levelChoiceService.moveRight(screenToJumpTo);
    }

    private void addLevelChooseTiles(final List<LevelInfo> levelInfos, float widthPerLevel) {
        for (int levelIndex = 0; levelIndex < levelInfos.size(); levelIndex++) {

            final int finalLevelIndex = levelIndex;
            ITextureRegion levelTextureRegion;
            IFont levelChoiceFont;
            final LevelInfo levelInfo = levelInfos.get(finalLevelIndex);
            if (levelInfo.isLocked()) {
                levelTextureRegion = resourcesManager.levelChoiceRegionSW;
                levelChoiceFont = resourcesManager.levelChoiceSWFont;
            } else {
                levelTextureRegion = resourcesManager.levelChoiceRegion;
                levelChoiceFont = resourcesManager.levelChoiceFont;
            }
            float levelSpriteX = getLevelSpriteX(widthPerLevel, levelIndex);
            float levelSpriteY = getLevelSpriteY(widthPerLevel, levelIndex);
            final ButtonSprite choiceLevelSprite = new ButtonSprite(levelSpriteX,
                    levelSpriteY, levelTextureRegion, vbom, new ButtonSprite.OnClickListener() {
                @Override
                public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (!levelInfo.isLocked()) {
                        if (finalLevelIndex != 0) {
                            sceneService.loadGameSceneFromLevelChoiceScene(levelService.getLevelById(levelInfo.getLevelId()));
                        } else {
                            sceneService.loadTutorialSceneFromLevelChoiceScene();
                        }

                    }
                }

            }
            );
            choiceLevelSprite.setWidth(widthPerLevel);
            choiceLevelSprite.setHeight(widthPerLevel);

            attachChild(choiceLevelSprite);
            registerTouchArea(choiceLevelSprite);

            int fontOffset;
            if ((levelIndex + 1) > 99) {
                fontOffset = 105;
            } else if ((levelIndex + 1) > 9) {
                fontOffset = 120;
            } else {
                fontOffset = 150;
            }
            attachChild(new Text(levelSpriteX + fontOffset, levelSpriteY + 110,
                    levelChoiceFont, String.valueOf(levelIndex + 1), vbom));


            if (!levelInfo.isLocked()) {
                ITextureRegion starOneRegion;
                ITextureRegion starTwoRegion;
                ITextureRegion starThreeRegion;
                if (levelInfo.getStars() >= 1) {
                    starOneRegion = resourcesManager.levelChoiseStarRegion;
                } else {
                    starOneRegion = resourcesManager.levelChoiseStarSWRegion;
                }
                if (levelInfo.getStars() >= 2) {
                    starTwoRegion = resourcesManager.levelChoiseStarRegion;
                } else {
                    starTwoRegion = resourcesManager.levelChoiseStarSWRegion;
                }
                if (levelInfo.getStars() >= 3) {
                    starThreeRegion = resourcesManager.levelChoiseStarRegion;
                } else {
                    starThreeRegion = resourcesManager.levelChoiseStarSWRegion;
                }

                Sprite starOne = new Sprite(levelSpriteX + 0.04f * widthPerLevel, levelSpriteY + widthPerLevel * 0.7f, starOneRegion, vbom);
                Sprite starTwo = new Sprite(levelSpriteX + 0.37f * widthPerLevel, levelSpriteY + widthPerLevel * 0.65f, starTwoRegion, vbom);
                Sprite starThree = new Sprite(levelSpriteX + 0.68f * widthPerLevel, levelSpriteY + widthPerLevel * 0.7f, starThreeRegion, vbom);

                attachChild(starOne);
                attachChild(starTwo);
                attachChild(starThree);
            }
        }
    }


    private void addChangeLevelButtons() {
        screenCount = (((int) (levelInfos.size() - 0.1) / 12)) + 1;
        rightArrow = new ButtonSprite(camera.getWidth() * 0.93f - LEVEL_SELECT_ICON_WIDTH, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowRightRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                levelChoiceService.moveRight();
            }
        });
        leftArrow = new ButtonSprite(camera.getWidth() * 0.07f, camera.getHeight() * 0.8f, resourcesManager.levelChoiceArrowLeftRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                levelChoiceService.moveLeft();
            }
        });

        leftArrow.setVisible(false);
        if (screenCount == 1) {
            rightArrow.setVisible(false);
        }
        arrowHud = new HUD();
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
        return ((levelIndex % 12 / COLUMNS)) * heightPerLevel;
    }

    private float getLevelSpriteX(float widthPerLevel, int levelIndex) {
        return (levelIndex % 12 % COLUMNS) * widthPerLevel + widthPerLevel * COLUMNS * (levelIndex / 12);
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
                arrowHud.detachChildren();
                levelChoiceScene.detachChildren();
            }
        }));
    }

    @Override
    public void update() {
        entityToFollow.registerEntityModifier(new MoveXModifier(0.5f, entityToFollow.getX(), levelChoiceService.getCurrentScreen() * camera.getWidth() + camera.getWidth() / 2, EaseQuartInOut.getInstance()));
        if (levelChoiceService.isFirstScene()) {
            leftArrow.setVisible(false);
        } else {
            leftArrow.setVisible(true);
        }
        if (levelChoiceService.isLastScene()) {
            rightArrow.setVisible(false);
        } else {
            rightArrow.setVisible(true);
        }
    }
}
