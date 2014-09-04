package de.sopa.scene.game;

import de.sopa.scene.game.GameScene;

/**
 * David Schilling - davejs92@gmail.com
 */
public class JustPlayGameScene extends GameScene {
    public JustPlayGameScene(Object o) {
        super(o);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromGameScene();
    }

    public void onSolvedGame() {
        System.out.println("foo");
    }
}
