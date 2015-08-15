package de.sopa.scene.score;

import de.sopa.model.game.Level;
import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class JustPlayScoreScene extends BaseScene {

    private final Level level;

    public JustPlayScoreScene(final Level level) {
        super();
        this.level = level;
        String levelCompleteString = 1 + ". Level\nComplete";
        Text levelCompleteTextShape = new Text((float) (camera.getWidth() * 0.12), (float) (camera.getHeight() * 0.05), resourcesManager.levelCompleteFont, levelCompleteString, 20, vbom);
        attachChild(levelCompleteTextShape);
        int score = 257;
        attachChild(new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.4), resourcesManager.justPlayScoreFont, "Score:          \t" + score, vbom));
        int leftTime = 23;
        int extraTime = 5;
        attachChild(new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.5), resourcesManager.justPlayScoreFont, "Left Time:           \t" + leftTime + "\nExtra Time:   \t" + extraTime,vbom));

    }

    @Override
    public void onBackKeyPressed() {
    }

    @Override
    public void disposeScene() {
        final JustPlayScoreScene scoreScreen = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                scoreScreen.detachChildren();
            }
        }));
    }
}
