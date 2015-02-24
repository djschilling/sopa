package de.sopa.scene.game;

import de.sopa.model.game.GameService;
import de.sopa.model.game.Tile;
import java.util.Map;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.ease.*;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameFieldView extends Entity {
    private final GameService gameService;
    private final float spacePerTile;
    private final Map<Character, TextureRegion> tileRegionMap;
    private final VertexBufferObjectManager vbom;
    private final ITextureRegion tilesBorderRegion;
    private TileSprite[][] tileSprites;

    public GameFieldView(float pX, float pY, float spacePerTile, GameService gameService, Map<Character, TextureRegion> regionMap, VertexBufferObjectManager vbom, ITextureRegion tilesBorderRegion) {
        super(pX, pY);
        this.gameService = gameService;
        this.spacePerTile = spacePerTile;
        this.tileRegionMap = regionMap;
        this.vbom = vbom;
        this.tilesBorderRegion = tilesBorderRegion;
    }

    public void addTiles() {
        detachChildren();
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
                    switch (field[x][y].getTileType()) {
                        case PUZZLE:
                            TileSprite tileSprite = new TileSprite(tilePositionX, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, vbom);
                            attachChild(tileSprite);
                            tileSprites[x][y] = tileSprite;
                            break;
                        case FINISH:
                            createFinishAnsStart(x, y, tilePositionX,tilePositionY, pTextureRegion, field);
                            break;
                        case START:
                            createFinishAnsStart(x, y, tilePositionX, tilePositionY, pTextureRegion, field);
                            break;
                        default:
                            break;
                    }


                }
                tilePositionX += spacePerTile;
            }
            tilePositionY += spacePerTile;
        }
        attachChild(new Sprite(spacePerTile, spacePerTile, spacePerTile * (width - 2),
                spacePerTile * (width - 2), tilesBorderRegion, vbom));
    }

    public void moveTiles(boolean horizontal, int row, float moveSize, boolean moveOver) {
        if (row < 0) {
            return;
        }
        row++;
        if (horizontal) {
            if (row > tileSprites.length - 2) {
                return;
            }
            for (int x = 1; x < tileSprites.length - 1; x++) {
                TileSprite tileSprite = tileSprites[x][row];
                float toX = tileSprite.getStartX() + moveSize;
                tileSprite.setX(toX);
                if (moveOver) {
                    tileSprite.setStartX(toX);
                }
            }
        } else {
            if (row > tileSprites[0].length - 2) {
                return;
            }
            for (int y = 1; y < tileSprites[row].length - 1; y++) {
                TileSprite tileSprite = tileSprites[row][y];
                float toY = tileSprite.getStartY() + moveSize;
                tileSprite.setY(toY);
                if (moveOver) {
                    tileSprite.setStartY(toY);
                }
            }
        }
    }

    public void oneStep(final boolean horizontal, int row, final int direction) {
        if (row < 0) {
            return;
        }
        final int finalRow = row;

        row++;
        if (horizontal) {
            if (row > tileSprites.length - 2) {
                return;
            }
            for (int x = 1; x < tileSprites.length - 1; x++) {
                TileSprite tileSprite = tileSprites[x][row];
                tileSprite.registerEntityModifier(new MoveXModifier(0.3f, tileSprite.getX(), tileSprite.getX() + tileSprite.getWidth() * direction, EaseQuadInOut.getInstance()) {
                    @Override
                    protected void onModifierFinished(IEntity pItem) {
                        gameService.shiftLine(horizontal, finalRow, direction);
                        super.onModifierFinished(pItem);
                    }
                });

            }
        } else {
            if (row > tileSprites[0].length - 2) {
                return;
            }
            for (int y = 1; y < tileSprites[row].length - 1; y++) {
                TileSprite tileSprite = tileSprites[row][y];
                tileSprite.registerEntityModifier(new MoveYModifier(0.3f, tileSprite.getY(), tileSprite.getY() + tileSprite.getWidth() * direction,EaseQuadInOut.getInstance()) {
                    @Override
                    protected void onModifierFinished(IEntity pItem) {
                        gameService.shiftLine(horizontal, finalRow, direction);
                        super.onModifierFinished(pItem);
                    }
                });
            }
        }
    }
    private void createFinishAnsStart(int x,int y, float tilePositionX, float tilePositionY, TextureRegion pTextureRegion, Tile[][] field ) {

        if(x == 0) {
            Sprite sprite = new TileSprite(tilePositionX + spacePerTile, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, vbom);
            attachChild(sprite);
        } else if(x == field.length - 1) {
            Sprite sprite = new TileSprite(tilePositionX - spacePerTile, tilePositionY, spacePerTile, spacePerTile, pTextureRegion, vbom);
            sprite.setRotationCenter(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(180f);
            attachChild(sprite);
        } else if(y == 0) {
            Sprite sprite = new TileSprite(tilePositionX, tilePositionY + spacePerTile, spacePerTile, spacePerTile, pTextureRegion, vbom);
            sprite.setRotationCenter(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(90f);
            attachChild(sprite);
        } else if(y == field[x].length - 1) {
            Sprite sprite = new TileSprite(tilePositionX, tilePositionY - spacePerTile, spacePerTile, spacePerTile, pTextureRegion, vbom);
            sprite.setRotationCenter(sprite.getWidth() / 2, sprite.getHeight() / 2);
            sprite.setRotation(270f);
            attachChild(sprite);
        }
    }

    @Override
    public void dispose() {
        detachChildren();
        super.dispose();
    }
}
