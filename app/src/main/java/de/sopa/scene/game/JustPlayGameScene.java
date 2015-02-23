package de.sopa.scene.game;

import de.sopa.helper.LevelFileService;
import java.io.IOException;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * David Schilling - davejs92@gmail.com
 */
public class JustPlayGameScene extends GameScene {
    public JustPlayGameScene(Object o) {
        super(o);
    }

    @Override
    protected void addButtons() {
        {
            final LevelFileService levelFileService = new LevelFileService(activity);
            Sprite saveLevelButton = new Sprite(0, camera.getHeight() * 0.8f, resourcesManager.saveButtonRegion, vbom) {
                @Override
                public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    if (pSceneTouchEvent.isActionUp()) {
                        try {
                            levelFileService.saveGameField(gameService.getLevel());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
            };
            registerTouchArea(saveLevelButton);
            setTouchAreaBindingOnActionDownEnabled(true);
            attachChild(saveLevelButton);
        }
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromGameScene();
    }

    @Override
    public void onSolvedGame() {

    }

}
