package de.sopa.scene;

import de.sopa.manager.SceneManager;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class MainMenuScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {
    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_LEVEL_MODE = 1;

    @Override
    public void createScene() {
        createBackground();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera);

        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region, vbom), 1.2f, 1);
        final IMenuItem levelModeItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_LEVEL_MODE, resourcesManager.level_mode_region, vbom), 1.2f, 1);

        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(levelModeItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        menuChildScene.setOnMenuItemClickListener(this);

        setChildScene(menuChildScene);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case MENU_PLAY:
                SceneManager.getInstance().loadGameScene(engine);
            case MENU_LEVEL_MODE:
                return true;
            default:
                return false;
        }
    }
}
