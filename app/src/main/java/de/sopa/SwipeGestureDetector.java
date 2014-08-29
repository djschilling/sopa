package de.sopa;

import de.sopa.scene.MySimpleOnGestureListener;
import de.sopa.model.GameService;
import org.andengine.input.touch.TouchEvent;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SwipeGestureDetector extends MySimpleOnGestureListener {

    private static final float SWIPE_MIN_DISTANCE = 25;

    private final GameService gameService;
    private final int startX;
    private final int startY;
    private final int widthPerTile;

    public SwipeGestureDetector(GameService gameService, int startX, int startY, int widthPerTile) {
        this.gameService = gameService;
        this.startX = startX;
        this.startY = startY;
        this.widthPerTile = widthPerTile;
    }

    @Override
    public boolean onFlingTouchEvent(TouchEvent e1, TouchEvent e2, float velocityX, float velocityY) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            int row = (int) ((e1.getY() - startY) / widthPerTile);
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
                System.out.println("Left");
                gameService.shiftLine(true, row, -1);
                return true;
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
                gameService.shiftLine(true, row, 1);
                System.out.println("Right");
                return true;
            }
        } else {
            int column = (int) (e1.getX() - startX) / widthPerTile;
            if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
                System.out.println("Top");
                gameService.shiftLine(false, column, -1);
                return true;
            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                System.out.println("Down");
                gameService.shiftLine(false, column, 1);
                return true;
            }
        }
        return true;
    }
}
