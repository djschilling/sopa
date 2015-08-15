package de.sopa.scene.levelmode;

import android.util.Log;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by raphael on 17.03.15.
 */
public abstract class LevelChoiceSceneSwipeDetector implements ITouchArea {


    public static final int SWIPE_DISTACE = 100;
    private final Entity entityToFollow;
    private float startX;
    private boolean finished;

    protected LevelChoiceSceneSwipeDetector(Entity entityToFollow) {
        this.entityToFollow = entityToFollow;

    }

    @Override
    public boolean contains(float pX, float pY) {
        return true;
    }

    @Override
    public float[] convertSceneToLocalCoordinates(float pX, float pY) {

        float[] coordinates = new float[2];
        coordinates[0] = pX - entityToFollow.getX();
        coordinates[1] = pY;
        return coordinates;
    }

    @Override
    public float[] convertLocalToSceneCoordinates(float pX, float pY) {
        float[] coordinates = new float[2];
        coordinates[0] = pX + entityToFollow.getX();
        coordinates[1] = pY;
        return coordinates;
    }

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

        if(pSceneTouchEvent.getMotionEvent().getAction() == 2) {
            if(pTouchAreaLocalX - startX > SWIPE_DISTACE && finished == false) {
                swipeLeft();
                finished = true;
            } else if(pTouchAreaLocalX - startX < -200 && finished == false) {
                swipeRight();
                finished = true;
            }
        } else if(pSceneTouchEvent.getMotionEvent().getAction() == 0) {
            finished = false;
            startX = pTouchAreaLocalX;
        } else {
            finished = true;
        }
        Log.i("asdf", String.valueOf(pSceneTouchEvent.getMotionEvent().getAction()));
        Log.i(String.valueOf(pSceneTouchEvent.getX()), String.valueOf(pTouchAreaLocalX));
        return false;
    }

    protected abstract void swipeRight();
    protected abstract void swipeLeft();
}

