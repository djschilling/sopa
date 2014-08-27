package de.sopa;

import java.util.Map;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameScene extends Scene {

    private final Map<Character, TextureRegion> regionMap;
    private final VertexBufferObjectManager vertexBufferObjectManager;

    public GameScene(Map<Character, TextureRegion> regionMap, VertexBufferObjectManager vertexBufferObjectManager) {
        this.regionMap = regionMap;
        this.vertexBufferObjectManager = vertexBufferObjectManager;
    }

    public void addTiles(Tile[][] field, int spaceHorizontal) {
        int width = field.length;
        int heigth = field[0].length;
        int spacePerTile = spaceHorizontal / width;
        int tilePositionY = 0;
        for (int y = 0; y < heigth; y++) {
            int tilePositionX = 0;
            for (int x = 0; x < width; x++) {
                if (field[x][y].getShortcut() != 'n') {
                    TextureRegion pTextureRegion = regionMap.get(field[x][y].getShortcut());
                    Sprite tileSprite = new Sprite(tilePositionX, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, vertexBufferObjectManager);
                    this.attachChild(tileSprite);
                }
                tilePositionX += spacePerTile;
            }
            tilePositionY += spacePerTile;
        }
    }

    public void setSolved(boolean solved) {
        Sprite solvedSprite;
        if(solved) {
            solvedSprite = new Sprite(0,0,50,50,regionMap.get('s'), vertexBufferObjectManager);
        }else {
            solvedSprite = new Sprite(0,0,50,50,regionMap.get('i'), vertexBufferObjectManager);
        }
        attachChild(solvedSprite);
    }
}
