package de.sopa;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.input.touch.detector.HoldDetector;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class MyHoldDetector implements HoldDetector.IHoldDetectorListener {
    private ContinuousHoldDetector continuousHoldDetector;
    private Sprite[][] tiles;

    public MyHoldDetector(Scene scene, Sprite[][] tiles) {
        this.continuousHoldDetector = new ContinuousHoldDetector(this);
        scene.registerUpdateHandler(continuousHoldDetector);
        this.tiles = tiles;
    }

    @Override
    public void onHoldStarted(HoldDetector pHoldDetector, int pPointerID, float pHoldX, float pHoldY) {
        tiles[2][2].setVisible(false);
    }

    @Override
    public void onHold(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {

    }

    @Override
    public void onHoldFinished(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {

    }
}
