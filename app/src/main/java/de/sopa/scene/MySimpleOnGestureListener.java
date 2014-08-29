package de.sopa.scene;

import android.view.GestureDetector;
import android.view.MotionEvent;
import de.sopa.manager.ResourcesManager;
import org.andengine.input.touch.TouchEvent;

import static de.sopa.manager.ResourcesManager.getInstance;

/**
 * David Schilling - davejs92@gmail.com
 */
public class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1 == null) {
            return true;
        }
        return onFlingTouchEvent(convertMotionToTouchEvent(e1), convertMotionToTouchEvent(e2), velocityX, velocityY);
    }


    public boolean onFlingTouchEvent(TouchEvent e1, TouchEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private TouchEvent convertMotionToTouchEvent(MotionEvent motionEvent){
        int i = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        TouchEvent touchEvent = TouchEvent.obtain(motionEvent.getX(), motionEvent.getY(), motionEvent.getAction(), i, MotionEvent.obtain(motionEvent));
        getInstance().camera.convertSurfaceToSceneTouchEvent(touchEvent, ResourcesManager.getInstance().engine.getSurfaceWidth(), ResourcesManager.getInstance().engine.getSurfaceHeight());
        return touchEvent;
    }

}
