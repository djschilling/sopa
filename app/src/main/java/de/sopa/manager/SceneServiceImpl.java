package de.sopa.manager;


import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import de.sopa.scene.BaseScene;
import de.sopa.scene.loading.LoadingScene;
import de.sopa.scene.settings.SettingsScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class SceneServiceImpl implements SceneService {

    private final JustPlaySceneService justPlaySceneService;
    private final LevelModeSceneService levelModeSceneService;
    private final MenuSceneService menuSceneService;
    private BaseScene loadingScene;
    private BaseScene settingsScene;

    private BaseScene currentScene;

    private Engine engine;
    private CreditsScene creditsScene;
    private BaseSceneService currentSceneService;

    public SceneServiceImpl(Engine engine) {
        this.engine = engine;
        this.justPlaySceneService = new JustPlaySceneServiceImpl(engine);
        this.levelModeSceneService = new LevelModeSceneServiceImpl(engine);
        this.menuSceneService = new MenuSceneServiceImpl(engine);
    }

    private void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }

    private void setSceneService(BaseSceneService sceneService) {
        this.currentSceneService = sceneService;
    }


    @Override
    public BaseScene getCurrentScene() {
        return currentScene != null? currentScene: currentSceneService.getCurrentScene();
    }

    @Override
    public void createLoadingScene() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();
        currentScene = loadingScene;
    }


    @Override
    public void createMenuScene() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        menuSceneService.startSynchron();
    }

    @Override
    public void loadMenuSceneFromLevelChoiceScene() {
        setScene(loadingScene);
        endSceneService(levelModeSceneService);
        menuSceneService.start();
    }

    public void loadGameSceneFromLevelChoiceScene(final Level level) {
        levelModeSceneService.loadGameSceneFromLevelChoiceScene(level);
    }

    public void loadScoreScreen(final LevelResult level) {
        levelModeSceneService.loadScoreScreen(level);
    }


    public void loadLevelChoiceSceneFromMenuScene() {
        setScene(loadingScene);
        menuSceneService.end();
        levelModeSceneService.start();
        setSceneService(levelModeSceneService);
        currentScene = null;
    }

    @Override
    public void loadCreditsFromMenuScene() {
        setScene(loadingScene);
        menuSceneService.end();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelCreditsSceneResources();
                creditsScene = new CreditsScene();
                setScene(creditsScene);
            }
        }));
    }

    @Override
    public void loadMenuSceneFromCreditsScene() {
        setScene(loadingScene);
        disposeCreditsScene();
        ResourcesManager.getInstance().unloadCreditsSceneResources();
        menuSceneService.start();
    }

    private void disposeCreditsScene() {
        ResourcesManager.getInstance().unloadCreditsSceneResources();
        creditsScene.disposeScene();
        creditsScene = null;
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
        setScene(loadingScene);
        menuSceneService.end();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadSettingsScene();
                settingsScene = new SettingsScene();
                setScene(settingsScene);
            }
        }));
    }

    @Override
    public void loadMenuSceneFromSettingsScene() {
        setScene(loadingScene);
        disposeSettingsScene();
        menuSceneService.start();
    }

    @Override
    public void loadMenuSceneFromScoreScene() {
        setScene(loadingScene);
        endSceneService(levelModeSceneService);
        menuSceneService.start();
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

    private void disposeSettingsScene() {
        ResourcesManager.getInstance().unloadSettingsScene();
    }


    public void loadLevelChoiceSceneFromGameScene() {
        levelModeSceneService.loadLevelChoiceSceneFromGameScene();
    }

    @Override
    public void loadJustPlaySceneSceneFromMenuScene() {
        setScene(loadingScene);
        menuSceneService.end();
        justPlaySceneService.start();
        setSceneService(justPlaySceneService);
        this.currentScene = null;
    }


    @Override
    public void loadMenuSceneFromJustPlayGameScene() {
        setScene(loadingScene);
        endSceneService(justPlaySceneService);
        menuSceneService.start();
    }

    @Override
    public void loadJustPlaySceneFromJustPlayScoreScene() {
        justPlaySceneService.loadJustPlaySceneFromJustPlayScoreScene();
    }

    @Override
    public void loadJustPlayScoreSceneSceneFromJustPlaySceneScene(final Level level) {
        justPlaySceneService.loadJustPlayScoreSceneSceneFromJustPlaySceneScene(level);
    }


    private void endSceneService(BaseSceneService sceneService) {
        sceneService.end();
        this.currentSceneService = null;
    }
}
