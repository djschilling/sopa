package de.sopa.scene.game;

import de.sopa.model.GameService;
import org.andengine.input.touch.detector.HoldDetector;

/**
 * Created by raphael on 10.09.14.
 */
public class GameSceneSingleMoveDetector implements HoldDetector.IHoldDetectorListener{
    private final float fieldStartX;
    private final GameService gameService;
    private final float cameraWidth;
    private float firstX;
    private float firstY;
    private float widthPerTile;
    private GameFieldView gameFieldView;
    private float fieldStartY;
    private boolean isMoved;

    public GameSceneSingleMoveDetector(float startX, float startY, float widthPerTile, GameFieldView gameFieldView, GameService gameService, float cameraWidth) {
        this.fieldStartX = startX;
        this.fieldStartY = startY;
        this.widthPerTile = widthPerTile;
        this.gameFieldView = gameFieldView;
        this.gameService = gameService;
        this.cameraWidth = cameraWidth;
        isMoved = false;
    }

    @Override
    public void onHoldStarted(HoldDetector pHoldDetector, int pPointerID, float pHoldX, float pHoldY) {
        firstX = pHoldX;
        firstY = pHoldY;
        isMoved = false;
    }

    @Override
    public void onHold(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {
        int row;
        if(!isMoved) {
            if (pHoldX - firstX > widthPerTile) {
                row = (int) ((firstY - fieldStartY) / widthPerTile);
                gameFieldView.oneStep(true, row, 1);
                isMoved = true;
            }
            if (firstX - pHoldX > widthPerTile) {
                row = (int) ((firstY - fieldStartY) / widthPerTile);
                gameFieldView.oneStep(true, row, -1);
                isMoved = true;
            }
            if (pHoldY - firstY > widthPerTile) {
                row = (int) ((firstX - fieldStartX) / widthPerTile);
                gameFieldView.oneStep(false, row, 1);
                isMoved = true;
            }
            if (firstY - pHoldY > widthPerTile) {
                row = (int) ((firstX - fieldStartX) / widthPerTile);
                gameFieldView.oneStep(false, row, -1);
                isMoved = true;
            }
        }
    }

    @Override
    public void onHoldFinished(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {

    }
}
