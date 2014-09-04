package de.sopa.scene.loading;

import de.sopa.manager.ResourcesManager;
import de.sopa.scene.BaseScene;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LoadingScene extends BaseScene {
    @Override
    public void createScene(Object o) {
        float spriteSTartY = (camera.getHeight() - camera.getWidth()) / 2;
        setBackground(new Background(Color.WHITE));
        this.attachChild(new Sprite(0, spriteSTartY, camera.getWidth(), camera.getWidth(), ResourcesManager.getInstance().loadingScreenBackgroundRegion, vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
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
