package de.sopa.scene;

import de.sopa.helper.LevelFileService;
import de.sopa.model.Level;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * @author Raphael Schilling
 */
public class ScoreScreen extends BaseScene {
    public ScoreScreen(Level level) {
        super(level);
    }

    @Override
    public void createScene(final Object o) {
        final Level level = (Level) o;
        final LevelFileService levelFileService = new LevelFileService(resourcesManager.activity);

        attachChild(new Text((float) (camera.getWidth() * 0.14), (float) (camera.getHeight() * 0.17), resourcesManager.scoreCompleteFont, "     Level\nComplete", vbom));
        attachChild(new Sprite(0 , (float) (camera.getHeight() * 0.55), 400, 400, resourcesManager.starRegion, vbom));
        attachChild(new Sprite((float) (camera.getWidth() * 0.64), (float) (camera.getHeight() * 0.55), 400, 400, resourcesManager.starSWRegion, vbom));
        attachChild(new Sprite((float) (camera.getWidth() / 2 - 200), (float) (camera.getHeight() * 0.6), 400, 400, resourcesManager.starRegion, vbom));
        ButtonSprite choiceLevelButton = new ButtonSprite(0, (float) (camera.getHeight() - 400),resourcesManager.backToChoiceRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadLevelChoiceSceneFromScoreScene();
            }
        });
        ButtonSprite nextLevelButton = new ButtonSprite((float) (camera.getWidth() * 0.64), (float) (camera.getHeight() - 400),resourcesManager.nextLevelRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                //TODO:Load GameScene over DB
                sceneService.loadGameSceneFromScoreScene(levelFileService.getLevel(level.getId() + 1));
            }
        });
        registerTouchArea(choiceLevelButton);
        registerTouchArea(nextLevelButton);
        attachChild(choiceLevelButton);
        attachChild(new ButtonSprite((float) (camera.getWidth() / 2 - 200), (float) (camera.getHeight() - 400),resourcesManager.backToChoiceRegion,vbom));
        attachChild(nextLevelButton);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromGameScene();
    }

    @Override
    public void disposeScene() {
        final ScoreScreen scoreScreen = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                scoreScreen.detachChildren();
            }
        }));
    }
}
