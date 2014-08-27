package de.sopa;

import android.app.Activity;
import android.view.GestureDetector;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

/**
 * David Schilling - davejs92@gmail.com
 */
public class TouchListener implements IOnSceneTouchListener {
    private final GestureDetector gestureDetector;

    public TouchListener(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;

    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, final TouchEvent pSceneTouchEvent) {
        gestureDetector.onTouchEvent(pSceneTouchEvent.getMotionEvent());
        return true;
    }
}
