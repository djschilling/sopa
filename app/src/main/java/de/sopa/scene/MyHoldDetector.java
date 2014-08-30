package de.sopa.scene;

import android.util.Log;
import de.sopa.model.GameService;
import org.andengine.input.touch.detector.HoldDetector;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class MyHoldDetector implements HoldDetector.IHoldDetectorListener {
    private final GameService gameService;
    private float moveStartX;
    private float moveStartY;
    private boolean vertical;
    private boolean horizontal;
    private float startX;
    private float startY;
    private float widthPerTile;
    private int row;
    private GameScene gameScene;

    public MyHoldDetector(float startX, float startY, float widthPerTile, GameScene gameScene, GameService gameService) {
        this.gameScene = gameScene;
        vertical = horizontal = false;
        this.startX = startX;
        this.startY = startY;
        this.widthPerTile = widthPerTile;
        this.gameService = gameService;
    }

    @Override
    public void onHoldStarted(HoldDetector pHoldDetector, int pPointerID, float pHoldX, float pHoldY) {
        Log.i("Position", new String(pHoldX + " : " + pHoldY));
        moveStartX = pHoldX;
        moveStartY = pHoldY;
    }

    @Override
    public void onHold(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {
        if(!horizontal&&!vertical) {
            if(Math.abs(moveStartX - pHoldX)> gameScene.camera.getWidth() / 100) {
                horizontal = true;
                row = (int) ((moveStartY - startY) / widthPerTile);
            }
            if(Math.abs(moveStartY - pHoldY)> gameScene.camera.getWidth() / 100) {
                vertical = true;
                row = (int) ((moveStartX - startX) / widthPerTile);
            }
        } else {
            if(horizontal) {
                float moveSize = pHoldX - moveStartX;
                if(moveSize > widthPerTile) {
                    gameService.shiftLine(true, row, 1);
                    moveStartX = pHoldX;
                    gameScene.moveTiles(true, row, 0, true);
                } else if(moveSize < (0 - widthPerTile )) {
                    gameService.shiftLine(true, row, -1);
                    moveStartX = pHoldX;
                    gameScene.moveTiles(true, row, 0, true);
                } else {
                    gameScene.moveTiles(true, row, moveSize, false);
                }


            } else {
                float moveSize = pHoldY - moveStartY;
                if(moveSize > widthPerTile) {
                    gameService.shiftLine(false, row, 1);
                    moveStartY = pHoldY;
                    gameScene.moveTiles(false, row, 0, true);
                } else if(moveSize < (0 - widthPerTile )) {
                    gameService.shiftLine(false, row, -1);
                    moveStartY = pHoldY;
                    gameScene.moveTiles(false, row, 0, true);
                } else {
                    gameScene.moveTiles(false, row, moveSize, false);
                }


            }
        }

    }


    @Override
    public void onHoldFinished(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {
        if(horizontal) {
            float moveSize = pHoldX - moveStartX;
            if(moveSize > widthPerTile * 0.5f) {
                gameService.shiftLine(true, row, 1);
            } else if(moveSize < (-0.5) * widthPerTile) {
                gameService.shiftLine(true, row, -1);
            } else {
                gameService.notifyAllObserver();
            }
        } else if(vertical) {
            float moveSize = pHoldY - moveStartY;
            if(moveSize > widthPerTile * 0.5f) {
                gameService.shiftLine(false, row, 1);
            } else if(moveSize < (-0.5) * widthPerTile) {
                gameService.shiftLine(false, row, -1);
            } else {
                gameService.notifyAllObserver();
            }

        }
        horizontal = vertical = false;
    }
}
