package de.sopa;

import android.util.Log;
import de.sopa.manager.ResourcesManager;
import de.sopa.model.GameService;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.detector.HoldDetector;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class MyHoldDetector implements HoldDetector.IHoldDetectorListener {
    private Sprite[][] tiles;
    private GameService gameService;
    private float moveStartX;
    private float moveStartY;
    private boolean vertical;
    private boolean horizontal;
    private int startX;
    private int startY;
    private int widthPerTile;
    private int row;

    public MyHoldDetector(Scene scene, Sprite[][] tiles, GameService gameService,int startX, int startY, int widthPerTile) {

        this.tiles = tiles;
        this.gameService = gameService;
        vertical = horizontal = false;
        this.startX = startX;
        this.startY = startY;
        this.widthPerTile = widthPerTile;
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
            if(Math.abs(moveStartX - pHoldX)> ResourcesManager.getInstance().activity.CAMERA_WIDTH/20) {
                horizontal = true;
                row = (int) ((pHoldY - startY) / widthPerTile);
            }
            if(Math.abs(moveStartY - pHoldY)> ResourcesManager.getInstance().activity.CAMERA_HEIGHT/20) {
                vertical = true;
                row = (int) (pHoldX - startX) / widthPerTile;
            }
        } else {
            if(horizontal) {
                Log.i("xVerschiebung", String.valueOf(pHoldX - moveStartX + " : an Reihe: " + row));

            } else {
                Log.i("yVerschiebung", String.valueOf(pHoldY - moveStartY + " : an Reihe:" + row));


            }
        }
    }

    @Override
    public void onHoldFinished(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {
        horizontal = vertical = false;
    }
}
