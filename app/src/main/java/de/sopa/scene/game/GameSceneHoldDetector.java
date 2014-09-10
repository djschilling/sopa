package de.sopa.scene.game;

import android.util.Log;
import de.sopa.model.GameService;
import org.andengine.input.touch.detector.HoldDetector;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class GameSceneHoldDetector implements HoldDetector.IHoldDetectorListener {
    private final GameService gameService;
    private final float cameraWidth;
    private final GameFieldView gameFieldView;
    private float moveStartX;
    private float moveStartY;
    private boolean vertical;
    private boolean horizontal;
    private float startX;
    private float startY;
    private float widthPerTile;
    private int row;

    public GameSceneHoldDetector(float startX, float startY, float widthPerTile, GameFieldView gameFieldView, GameService gameService, float cameraWidth) {
        this.gameFieldView = gameFieldView;
        vertical = horizontal = false;
        this.startX = startX;
        this.startY = startY;
        this.widthPerTile = widthPerTile;
        this.gameService = gameService;
        this.cameraWidth = cameraWidth;
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
            if(Math.abs(moveStartX - pHoldX)> cameraWidth / 100) {
                horizontal = true;
                row = (int) ((moveStartY - startY) / widthPerTile);
            }
            if(Math.abs(moveStartY - pHoldY)> cameraWidth / 100) {
                vertical = true;
                row = (int) ((moveStartX - startX) / widthPerTile);
            }
        } else {
            if(horizontal) {
                float moveSize = pHoldX - moveStartX;
                   if(moveSize > widthPerTile) {
                    gameService.shiftLine(true, row, 1);
                    moveStartX = pHoldX;
                    gameFieldView.moveTiles(true, row, 0, true);
                } else if(moveSize < (0 - widthPerTile )) {
                    gameService.shiftLine(true, row, -1);
                    moveStartX = pHoldX;
                    gameFieldView.moveTiles(true, row, 0, true);
                } else {
                    gameFieldView.moveTiles(true, row, moveSize, false);
                }


            } else {
                float moveSize = pHoldY - moveStartY;
                if(moveSize > widthPerTile) {
                    gameService.shiftLine(false, row, 1);
                    moveStartY = pHoldY;
                    gameFieldView.moveTiles(false, row, 0, true);
                } else if(moveSize < (0 - widthPerTile )) {
                    gameService.shiftLine(false, row, -1);
                    moveStartY = pHoldY;
                    gameFieldView.moveTiles(false, row, 0, true);
                } else {
                    gameFieldView.moveTiles(false, row, moveSize, false);
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
