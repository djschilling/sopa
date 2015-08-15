package de.sopa.scene.justplay;

import de.sopa.scene.BaseScene;
import de.sopa.model.justplay.JustPlayResult;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

import static org.andengine.util.color.Color.*;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class JustPlayScoreScene extends BaseScene {


    private boolean leaveScene;

    public JustPlayScoreScene(final JustPlayResult justPlayResult) {
        super();

        this.leaveScene = false;

        Rectangle rectangleScore = new Rectangle(0, (float) (camera.getHeight() * 0.45), camera.getWidth(), camera.getHeight() / 10, vbom);
        rectangleScore.setColor(0, 102/255f, 255/255f);
        attachChild(rectangleScore);

        Rectangle rectangleTime = new Rectangle(0, (float) (camera.getHeight() * 0.6), camera.getWidth(), (float) (camera.getHeight() * 0.2), vbom);
        rectangleTime.setColor(153 / 255f, 0, 0);
        attachChild(rectangleTime);


        String levelCompleteString = justPlayResult.getLevelAnzahl() +
                ".  Level\nComplete";
        Text levelCompleteTextShape = new Text((float) (camera.getWidth() * 0.12), (float) (camera.getHeight() * 0.1), resourcesManager.levelCompleteFont, levelCompleteString, 20, vbom);
        attachChild(levelCompleteTextShape);
        Text scoreText = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.45), resourcesManager.justPlayScoreFont,
                "Score:      ", vbom);
        scoreText.setColor(BLACK);
        attachChild(scoreText);
        final int[] currentScore = {justPlayResult.getLastScore()};
        final Text score = new Text((float) (camera.getWidth() * 0.6), (float) (camera.getHeight() * 0.45), resourcesManager.justPlayScoreFont, "" + currentScore[0], 8, vbom);
        score.setColor(BLACK);
        attachChild(score);

        Text timeText = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.605), resourcesManager.justPlayScoreFont,
                "Left Time:  \t" + justPlayResult.getLeftTime() + "\n" +
                "Extra Time:\t+" + justPlayResult.getExtraTime(), vbom);
        attachChild(timeText);
        timeText.setColor(WHITE);
        ButtonSprite nextLevelButton = new ButtonSprite((camera.getWidth() / 2 - 200), (camera.getHeight() - 400), resourcesManager.nextJustPlayLevel, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                storyService.loadJustPlaySceneFromJustPlayScoreScene();
            }
        });

        attachChild(nextLevelButton);
        registerTouchArea(nextLevelButton);
        engine.registerUpdateHandler(new TimerHandler(0.0005f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if(!leaveScene) {
                    if(currentScore[0] >= justPlayResult.getScore()) {
                        score.setText(String.valueOf(justPlayResult.getScore()));
                        engine.unregisterUpdateHandler(pTimerHandler);
                    } else {
                        currentScore[0]++;
                        score.setText(String.valueOf(currentScore[0]));
                    }
                } else {
                    engine.unregisterUpdateHandler(pTimerHandler);
                }
            }
        }));
    }

    @Override
    public void onBackKeyPressed() {
            leaveScene = true;
            storyService.loadMenuSceneFromJustPlayScoreScene();
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
