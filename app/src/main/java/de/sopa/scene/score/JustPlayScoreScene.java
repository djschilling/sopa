package de.sopa.scene.score;

import de.sopa.model.game.Level;
import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import static org.andengine.util.color.Color.*;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class JustPlayScoreScene extends BaseScene {


    public JustPlayScoreScene(final JustPlayResult justPlayResult) {
        super();

        Rectangle rectangleScore = new Rectangle(0, (float) (camera.getHeight() * 0.45), camera.getWidth(), camera.getHeight() / 10, vbom);
        rectangleScore.setColor(0, 102/255f, 255/255f);
        attachChild(rectangleScore);

        Rectangle rectangleTime = new Rectangle(0, (float) (camera.getHeight() * 0.6), camera.getWidth(), (float) (camera.getHeight() * 0.2), vbom);
        rectangleTime.setColor(153/255f,0 , 0);
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
                sceneService.loadJustPlaySceneFromJustPlayScoreScene();
            }
        });

        attachChild(nextLevelButton);
        registerTouchArea(nextLevelButton);
        engine.registerUpdateHandler(new TimerHandler(0.0001f, true, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                currentScore[0]++;
                if(currentScore[0] >= justPlayResult.getScore()) {
                    engine.unregisterUpdateHandler(pTimerHandler);
                }
                score.setText(String.valueOf(currentScore[0]));
            }
        }));



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
