package de.sopa.scene.levelmode;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public class LevelModeCompleteScene extends de.sopa.scene.BaseScene {
    public LevelModeCompleteScene() {
        Text levelCompleteTextShape = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.05),
                resourcesManager.levelModeCompleteFont, "Level Mode\nCompleted", vbom);
        levelCompleteTextShape.setScaleCenter(0, 0);
        attachChild(levelCompleteTextShape);
        Sprite cup = new Sprite(0, 0, resourcesManager.levelModeCup, vbom);
        cup.setPosition(camera.getWidth() / 2 - cup.getWidth() /2, camera.getHeight() * 0.4f);
        attachChild(cup);
        setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
                onBackKeyPressed();
                return false;
            }
        });

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
