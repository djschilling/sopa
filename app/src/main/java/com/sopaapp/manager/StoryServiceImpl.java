package com.sopaapp.manager;

import com.sopaapp.model.game.Level;
import com.sopaapp.model.game.LevelResult;
import com.sopaapp.model.game.TimeBasedGameService;
import com.sopaapp.model.justplay.JustPlayLevel;
import com.sopaapp.model.justplay.JustPlayLevelResult;

import com.sopaapp.scene.BaseScene;
import com.sopaapp.scene.credits.CreditsSceneService;
import com.sopaapp.scene.credits.CreditsSceneServiceImpl;
import com.sopaapp.scene.justplay.JustPlaySceneService;
import com.sopaapp.scene.justplay.JustPlaySceneServiceImpl;
import com.sopaapp.scene.levelmode.LevelModeSceneService;
import com.sopaapp.scene.levelmode.LevelModeSceneServiceImpl;
import com.sopaapp.scene.loading.LoadingSceneService;
import com.sopaapp.scene.loading.LoadingSceneServiceImpl;
import com.sopaapp.scene.menu.MenuSceneService;
import com.sopaapp.scene.menu.MenuSceneServiceImpl;
import com.sopaapp.scene.settings.SettingSceneService;
import com.sopaapp.scene.settings.SettingSceneServiceImpl;

import org.andengine.engine.Engine;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class StoryServiceImpl implements StoryService {

    private final JustPlaySceneService justPlaySceneService;
    private final LevelModeSceneService levelModeSceneService;
    private final MenuSceneService menuSceneService;
    private final CreditsSceneService creditsSceneService;
    private SettingSceneService settingsSceneService;
    private LoadingSceneService loadingSceneService;

    private BaseSceneService currentSceneService;

    public StoryServiceImpl(Engine engine) {

        this.justPlaySceneService = new JustPlaySceneServiceImpl(engine);
        this.levelModeSceneService = new LevelModeSceneServiceImpl(engine);
        this.menuSceneService = new MenuSceneServiceImpl(engine);
        this.creditsSceneService = new CreditsSceneServiceImpl(engine);
        this.settingsSceneService = new SettingSceneServiceImpl(engine);
        this.loadingSceneService = new LoadingSceneServiceImpl(engine);
    }

    @Override
    public BaseScene getCurrentScene() {

        return currentSceneService.getCurrentScene();
    }


    @Override
    public void createLoadingScene() {

        loadingSceneService.initialStart();
        currentSceneService = loadingSceneService;
    }


    @Override
    public void createMenuScene() {

        ResourcesManager.getInstance().loadLoadingSceneResources();
        menuSceneService.startSynchron();
        this.currentSceneService = menuSceneService;
    }


    @Override
    public void loadMenuSceneFromLevelChoiceScene() {

        startSceneService(loadingSceneService);
        endSceneService(levelModeSceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadGameSceneFromLevelChoiceScene(final Level level) {

        levelModeSceneService.loadGameSceneFromLevelChoiceScene(level);
    }


    @Override
    public void loadScoreScreen(final LevelResult level) {

        levelModeSceneService.loadScoreScreen(level);
    }


    @Override
    public void loadLevelModeCompleteSceneFromScoreScene() {

        levelModeSceneService.loadLevelModeCompleteSceneFromScoreScene();
    }


    @Override
    public void loadLevelChoiceFromLevelModeCompleteScene() {

        levelModeSceneService.loadLevelChoiceFromLevelModeCompleteScene();
    }


    @Override
    public void loadJustPlaySceneFromJustPlayLostScene() {

        justPlaySceneService.loadJustPlaySceneFromJustPlayLostScene();
    }


    @Override
    public void loadLevelChoiceSceneFromMenuScene() {

        startSceneService(loadingSceneService);
        endSceneService(menuSceneService);
        startSceneService(levelModeSceneService);
    }


    @Override
    public void loadCreditsFromMenuScene() {

        startSceneService(loadingSceneService);
        menuSceneService.end();
        startSceneService(creditsSceneService);
    }


    @Override
    public void loadMenuSceneFromCreditsScene() {

        startSceneService(loadingSceneService);
        endSceneService(creditsSceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadLevelChoiceSceneFromScoreScene() {

        levelModeSceneService.loadLevelChoiceSceneFromScoreScene();
    }


    @Override
    public void loadGameSceneFromScoreScene(final Level level) {

        levelModeSceneService.loadGameSceneFromScoreScene(level);
    }


    @Override
    public void loadSettingsFromMenuScene() {

        startSceneService(loadingSceneService);
        endSceneService(menuSceneService);
        startSceneService(settingsSceneService);
    }


    @Override
    public void loadMenuSceneFromSettingsScene() {

        startSceneService(loadingSceneService);
        endSceneService(settingsSceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadMenuSceneFromScoreScene() {

        startSceneService(loadingSceneService);
        endSceneService(levelModeSceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadGameSceneFromGameScene(final Level level) {

        levelModeSceneService.loadGameSceneFromGameScene(level);
    }


    @Override
    public void loadTutorialSceneFromLevelChoiceScene() {

        levelModeSceneService.loadTutorialSceneFromLevelChoiceScene();
    }


    @Override
    public void loadFirstLevelFromTutorial() {

        levelModeSceneService.loadFirstLevelFromTutorial();
    }


    @Override
    public void loadLevelChoiceFromTutorial() {

        levelModeSceneService.loadLevelChoiceFromTutorial();
    }


    @Override
    public void loadLevelChoiceSceneFromGameScene() {

        levelModeSceneService.loadLevelChoiceSceneFromGameScene();
    }


    @Override
    public void loadJustPlaySceneFromMenuScene() {

        startSceneService(loadingSceneService);
        endSceneService(menuSceneService);
        startSceneService(justPlaySceneService);
    }


    @Override
    public void loadMenuSceneFromJustPlayGameScene() {

        startSceneService(loadingSceneService);
        endSceneService(justPlaySceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadJustPlaySceneFromJustPlayScoreScene() {

        justPlaySceneService.loadJustPlaySceneFromJustPlayScoreScene();
    }


    @Override
    public void loadMenuSceneFromJustPlayScoreScene() {

        startSceneService(loadingSceneService);
        endSceneService(justPlaySceneService);
        startSceneService(menuSceneService);
    }


    @Override
    public void loadJustPlaySceneFromJustPlayScene(TimeBasedGameService timeBasedGameService,
        JustPlayLevel justPlayLevel) {

        justPlaySceneService.loadJustPlaySceneFromJustPlayScene(timeBasedGameService, justPlayLevel);
    }


    @Override
    public void loadJustPlayScoreSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult) {

        justPlaySceneService.loadJustPlayScoreSceneFromJustPlayScene(justPlayLevelResult);
    }


    @Override
    public void loadJustPlayLostSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult) {

        justPlaySceneService.loadJustPlayLostSceneFromJustPlayScene(justPlayLevelResult);
    }


    private void startSceneService(BaseSceneService sceneService) {

        sceneService.start();
        currentSceneService = sceneService;
    }


    private void endSceneService(BaseSceneService sceneService) {

        sceneService.end();
        this.currentSceneService = null;
    }
}
