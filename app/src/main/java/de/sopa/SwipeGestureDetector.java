package de.sopa;

import android.view.MotionEvent;

import static android.view.GestureDetector.SimpleOnGestureListener;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SwipeGestureDetector extends SimpleOnGestureListener {

    private static final float SWIPE_MIN_DISTANCE = 50;

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                System.out.println("Left");
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                System.out.println("Right");
                return true;
            }
            // Vertical
        } else {
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                System.out.println("Top");
                return true;
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                System.out.println("Down");
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        System.out.println("single tab");
        return true;
    }
}
