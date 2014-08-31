package de.sopa.scene;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

/**
 * @author Raphael Schilling
 */
public class ScoreScreen extends BaseScene {
    @Override
    public void createScene(Object o) {
        attachChild(new Text((float) (camera.getWidth() * 0.14), (float) (camera.getHeight() * 0.17), resourcesManager.scoreCompleteFont, "     Level\nComplete", vbom));
        attachChild(new Sprite(0 , (float) (camera.getHeight() * 0.55), 400, 400, resourcesManager.starRegion, vbom));
        attachChild(new Sprite((float) (camera.getWidth() * 0.64), (float) (camera.getHeight() * 0.55), 400, 400, resourcesManager.starSWRegion, vbom));
        attachChild(new Sprite((float) (camera.getWidth() / 2 - 200), (float) (camera.getHeight() * 0.6), 400, 400, resourcesManager.starRegion, vbom));

        attachChild(new ButtonSprite(0,                                    (float) (camera.getHeight() - 400),resourcesManager.backToChoiceRegion,vbom));
        attachChild(new ButtonSprite((float) (camera.getWidth() / 2 - 200), (float) (camera.getHeight() - 400),resourcesManager.backToChoiceRegion,vbom));
        attachChild(new ButtonSprite((float) (camera.getWidth() * 0.64), (float) (camera.getHeight() - 400),resourcesManager.nextLevelRegion,vbom));
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
