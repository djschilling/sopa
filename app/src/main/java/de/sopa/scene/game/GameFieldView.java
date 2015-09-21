package de.sopa.scene.game;

import de.sopa.model.game.GameService;
import de.sopa.model.game.Tile;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;

import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import org.andengine.util.modifier.ease.EaseQuadInOut;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class GameFieldView extends Entity {

    private final GameService gameService;
    private final float spacePerTile;
    private final Map<Character, TextureRegion> tileRegionMap;
    private final VertexBufferObjectManager vbom;
    private final ITextureRegion tilesBorderRegion;
    private TileSprite[][] tileSprites;
    private boolean active = false;

    private int countModifier;
    private int modifierFinished;

    public GameFieldView(float pX, float pY, float spacePerTile, GameService gameService,
                         Map<Character, TextureRegion> regionMap, VertexBufferObjectManager vbom, ITextureRegion tilesBorderRegion) {

        super(pX, pY);
        this.gameService = gameService;
        this.spacePerTile = spacePerTile;
        this.tileRegionMap = regionMap;
        this.vbom = vbom;
        this.tilesBorderRegion = tilesBorderRegion;
    }

    public void addTiles(boolean finished) {

        int tileIndex;

        if (finished) {
            tileIndex = 1;
        } else {
            tileIndex = 0;
        }


        detachChildren();
        if (active){
            oneModifierFinished(true);
        }

        Tile[][] field = gameService.getLevel().getField();
        int width = field.length;
        int heigth = field[0].length;
        tileSprites = new TileSprite[width][heigth];

        int tilePositionY = 0;

        for (int y = 0; y < heigth; y++) {
            int tilePositionX = 0;

            for (int x = 0; x < width; x++) {
                if (field[x][y].getShortcut() != 'n') {
                    TextureRegion pTextureRegion = tileRegionMap.get(field[x][y].getShortcut());
                    TextureRegion pTextureRegionFilled = tileRegionMap.get(Character.toUpperCase(
                            field[x][y].getShortcut()));
                    List<ITextureRegion> textureRegions = Arrays.<ITextureRegion>asList(pTextureRegion,
                            pTextureRegionFilled);

                    switch (field[x][y].getTileType()) {
                        case PUZZLE:

                            TileSprite tileSprite = new TileSprite(tilePositionX, tilePositionY, spacePerTile,
                                    spacePerTile, textureRegions, vbom);
                            tileSprite.setITextureRegionIndex(tileIndex);
                            attachChild(tileSprite);
                            tileSprites[x][y] = tileSprite;
                            break;

                        case FINISH:
                            TileSprite finish = createFinishAnsStart(x, y, tilePositionX, tilePositionY, textureRegions, field);
                            finish.setITextureRegionIndex(tileIndex);
                            break;

                        case START:
                            TileSprite start = createFinishAnsStart(x, y, tilePositionX, tilePositionY, textureRegions, field);
                            start.setITextureRegionIndex(tileIndex);
                            break;

                        default:
                            break;
                    }
                }

                tilePositionX += spacePerTile;
            }

            tilePositionY += spacePerTile;
        }

        attachChild(new Sprite(spacePerTile, spacePerTile, spacePerTile * (width - 2), spacePerTile * (width - 2),
                tilesBorderRegion, vbom));
    }


    public void oneStep(final boolean horizontal, int row, final int direction) {

        if (row < 0) {
            return;
        }

        row++;

        if (horizontal) {
            if (row > tileSprites.length - 2) {
                return;
            }

            gameService.shiftLine(true, row - 1, direction, true);
            active = true;
            this.countModifier = tileSprites.length - 2;
            this.modifierFinished = 0;

            for (int x = 1; x < tileSprites.length - 1; x++) {
                TileSprite tileSprite = tileSprites[x][row];
                tileSprite.registerEntityModifier(new MoveXModifier(0.3f, tileSprite.getX(),
                        tileSprite.getX() + tileSprite.getWidth() * direction, EaseQuadInOut.getInstance()) {

                    @Override
                    protected void onModifierFinished(IEntity pItem) {

                        oneModifierFinished(false);
                        super.onModifierFinished(pItem);
                    }
                });
            }
        } else {
            if (row > tileSprites[0].length - 2) {
                return;
            }

            gameService.shiftLine(false, row - 1, direction, true);
            active = true;
            this.countModifier = tileSprites[row].length - 2;
            this.modifierFinished = 0;

            for (int y = 1; y < tileSprites[row].length - 1; y++) {
                TileSprite tileSprite = tileSprites[row][y];
                tileSprite.registerEntityModifier(new MoveYModifier(0.3f, tileSprite.getY(),
                        tileSprite.getY() + tileSprite.getWidth() * direction, EaseQuadInOut.getInstance()) {

                    @Override
                    protected void onModifierFinished(IEntity pItem) {

                        oneModifierFinished(false);
                        super.onModifierFinished(pItem);
                    }
                });
            }
        }
    }


    private TileSprite createFinishAnsStart(int x, int y, float tilePositionX, float tilePositionY,
                                            List<ITextureRegion> pTextureRegion, Tile[][] field) {

        TileSprite tileSprite;

        if (x == 0) {
            tileSprite = new TileSprite(tilePositionX + spacePerTile, tilePositionY, spacePerTile, spacePerTile,
                    pTextureRegion, vbom);
            attachChild(tileSprite);
        } else if (x == field.length - 1) {
            tileSprite = new TileSprite(tilePositionX - spacePerTile, tilePositionY, spacePerTile, spacePerTile,
                    pTextureRegion, vbom);
            tileSprite.setRotationCenter(tileSprite.getWidth() / 2, tileSprite.getHeight() / 2);
            tileSprite.setRotation(180f);
            attachChild(tileSprite);
        } else if (y == 0) {
            tileSprite = new TileSprite(tilePositionX, tilePositionY + spacePerTile, spacePerTile, spacePerTile,
                    pTextureRegion, vbom);
            tileSprite.setRotationCenter(tileSprite.getWidth() / 2, tileSprite.getHeight() / 2);
            tileSprite.setRotation(90f);
            attachChild(tileSprite);
        } else {
            tileSprite = new TileSprite(tilePositionX, tilePositionY - spacePerTile, spacePerTile, spacePerTile,
                    pTextureRegion, vbom);
            tileSprite.setRotationCenter(tileSprite.getWidth() / 2, tileSprite.getHeight() / 2);
            tileSprite.setRotation(270f);
            attachChild(tileSprite);
        }

        return tileSprite;
    }


    @Override
    public void dispose() {

        detachChildren();
        super.dispose();
    }


    private void oneModifierFinished(boolean now) {

        modifierFinished++;

        if (modifierFinished == countModifier | now) {
            active = false;
            gameService.notifyAllObserver();
        }
    }
}
