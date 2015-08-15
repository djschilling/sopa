package de.sopa.scene.credits;

import de.sopa.scene.BaseScene;
import org.andengine.entity.text.Text;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class CreditsScene extends BaseScene{

    public CreditsScene() {
        super();
        Text headingText = new Text(camera.getWidth() * 0.01f, camera.getHeight() * 0.01f, resourcesManager.creditsHeading, "Credits", vbom);
        attachChild(headingText);

        Text subHeading = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.15f, resourcesManager.creditsSubHeading, "Developement", vbom);
        attachChild(subHeading);

        Text davidschilling = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.22f, resourcesManager.creditsText, "David Schilling - @schillda710", vbom);
        attachChild(davidschilling);
        Text raphaelschilling = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.25f, resourcesManager.creditsText, "Raphael Schilling - @ubuntius", vbom);
        attachChild(raphaelschilling);

        Text design = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.3f, resourcesManager.creditsSubHeading, "Design", vbom);
        attachChild(design);

        Text raphael = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.37f, resourcesManager.creditsText, "Raphael Schilling - @ubuntius", vbom);
        attachChild(raphael);

        Text libraries = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.42f, resourcesManager.creditsSubHeading, "Libraries", vbom);
        attachChild(libraries);

        Text andengine = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.49f, resourcesManager.creditsText, "AndEngine - andengine.org", vbom);
        attachChild(andengine);

        Text music = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.54f, resourcesManager.creditsSubHeading, "Music", vbom);
        attachChild(music);

        Text menumusic = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.61f, resourcesManager.creditsText, "Menu - axtoncrolley on opengameart.org", vbom);
        attachChild(menumusic);

        Text icons = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.66f, resourcesManager.creditsSubHeading, "Icons", vbom);
        attachChild(icons);

        Text mute = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.73f, resourcesManager.creditsText, "Mute/Unmute - mikhog on opengameart.org", vbom);
        attachChild(mute);
        Text star = new Text(camera.getWidth() * 0.05f, camera.getHeight() * 0.76f, resourcesManager.creditsText, "Star - Estrella Clip Art from de.clipartlogo.com", vbom);
        attachChild(star);



    }

    @Override
    public void onBackKeyPressed() {
        storyService.loadMenuSceneFromCreditsScene();
    }

    @Override
    public void disposeScene() {

    }
}
