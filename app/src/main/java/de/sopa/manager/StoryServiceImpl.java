package de.sopa.manager;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import de.sopa.model.game.TimeBasedGameService;
import de.sopa.model.justplay.JustPlayLevel;
import de.sopa.model.justplay.JustPlayLevelResult;

import de.sopa.scene.BaseScene;
import de.sopa.scene.credits.CreditsSceneService;
import de.sopa.scene.credits.CreditsSceneServiceImpl;
import de.sopa.scene.justplay.JustPlaySceneService;
import de.sopa.scene.justplay.JustPlaySceneServiceImpl;
import de.sopa.scene.levelmode.LevelModeSceneService;
import de.sopa.scene.levelmode.LevelModeSceneServiceImpl;
import de.sopa.scene.loading.LoadingSceneService;
import de.sopa.scene.loading.LoadingSceneServiceImpl;
import de.sopa.scene.menu.MenuSceneService;
import de.sopa.scene.menu.MenuSceneServiceImpl;
import de.sopa.scene.settings.SettingSceneService;
import de.sopa.scene.settings.SettingSceneServiceImpl;

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


    private void startSceneService(BaseSceneService sceneService) {

        sceneService.start();
        currentSceneService = sceneService;
    }


    private void endSceneService(BaseSceneService sceneService) {

        sceneService.end();
        this.currentSceneService = null;
    }
}
