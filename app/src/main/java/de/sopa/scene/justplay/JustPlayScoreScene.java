package de.sopa.scene.justplay;

import de.sopa.model.justplay.JustPlayResult;
import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.util.modifier.ease.EaseQuadInOut;

import static org.andengine.util.color.Color.BLACK;
import static org.andengine.util.color.Color.WHITE;

/**
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class JustPlayScoreScene extends BaseScene {

    private boolean leaveScene;
    private JustPlayResult justPlayResult;
    private Text score;

    public JustPlayScoreScene(final JustPlayResult justPlayResult) {
        super();

        this.leaveScene = false;
        this.justPlayResult = justPlayResult;

        final int[] currentScore = {justPlayResult.getLastScore()};

        addRectangles();
        addTexts(currentScore);
        addAnimation(currentScore);
        addNextLevelButton();

    }

       private void addAnimation(final int[] currentScore) {
        engine.registerUpdateHandler(new TimerHandler(0.01f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (!leaveScene) {
                    if (currentScore[0] >= justPlayResult.getScore()) {
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
        registerEntityModifier(new MoveYModifier(0.15f, -camera.getHeight(), 0, EaseQuadInOut.getInstance()));
    }

    private void addNextLevelButton() {
        ButtonSprite nextLevelButton = new ButtonSprite((camera.getWidth() / 2 - 200), (camera.getHeight() - 400),
                resourcesManager.nextJustPlayLevel, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                storyService.loadJustPlaySceneFromJustPlayScoreScene();
            }
        });
        attachChild(nextLevelButton);
        registerTouchArea(nextLevelButton);
    }

    private void addTexts(int[] currentScore) {
        Text levelCompleteTextShape = new Text((float) (camera.getWidth() * 0.12), (float) (camera.getHeight() * 0.1), resourcesManager.levelCompleteFont, getHeadText(), 20, vbom);
        attachChild(levelCompleteTextShape);

        Text scoreText = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.45), resourcesManager.justPlayScoreFont,
                "Score:      ", vbom);
        scoreText.setColor(BLACK);
        attachChild(scoreText);


        score = new Text((float) (camera.getWidth() * 0.65), (float) (camera.getHeight() * 0.45), resourcesManager.justPlayScoreFont, "" + currentScore[0], 8, vbom);
        score.setColor(BLACK);
        attachChild(score);

        Text timeText = new Text((float) (camera.getWidth() * 0.05), (float) (camera.getHeight() * 0.605), resourcesManager.justPlayScoreFont,
                 "Left Time:      \t" + justPlayResult.getLeftTime() + "\n" +
                         "Extra Time:\t+" + justPlayResult.getExtraTime(), vbom);
        timeText.setColor(WHITE);
        attachChild(timeText);

    }

    private String getHeadText() {
       return justPlayResult.getLevelCount() +
                    ".  Level\nComplete";
    }

    private void addRectangles() {
        Rectangle rectangleScore = new Rectangle(0, (float) (camera.getHeight() * 0.45), camera.getWidth(), camera.getHeight() / 10, vbom);
        rectangleScore.setColor(0, 102 / 255f, 255 / 255f);
        attachChild(rectangleScore);
        Rectangle rectangleTime = new Rectangle(0, (float) (camera.getHeight() * 0.6), camera.getWidth(), (float) (camera.getHeight() / 5), vbom);
        rectangleTime.setColor(153 / 255f, 0, 0);
        attachChild(rectangleTime);
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
