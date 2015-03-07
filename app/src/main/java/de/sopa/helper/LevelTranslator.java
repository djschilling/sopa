package de.sopa.helper;

import java.util.ArrayList;

import de.sopa.model.game.Level;
import de.sopa.model.game.Tile;
import de.sopa.model.game.TileType;

import static de.sopa.helper.TileCharacterMapping.CHARACTER_TILE_MAP;

/**
 * Helper to translate SOPA Level specification conform String arrays to
 * {@link de.sopa.model.game.Level} and back.
 *
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelTranslator {

    /**
     * Translates a String array after sopa level specification
     * to an {@link de.sopa.model.game.Level}
     *
     * @param string sopa level specified string array.
     *
     * @return translated {@link de.sopa.model.game.Level}
     *
     * @throws java.lang.IllegalArgumentException when there are wrong tiles in the border.
     */
    public Level fromString(String[] string) {
        Tile[][] field;
        Level level = new Level();
        int indexOfProperties = 0;
        for (; string[indexOfProperties].charAt(0) != '#'; indexOfProperties++) {
            switch (indexOfProperties) {
                case 0:
                    level.setId(Integer.parseInt(string[0]));
                    break;
                case 1:
                    level.setMinimumMovesToSolve(Integer.parseInt(string[1]));
                    break;
                case 2:
                    level.setTilesCount(Integer.parseInt(string[2]));
                default:
                    break;
            }
        }
        ArrayList<String> stringsForField = new ArrayList<>();
        for (int i = indexOfProperties + 1; i < string.length; i++) {
            stringsForField.add(i - indexOfProperties - 1, string[i]);
        }

        field = getTiles(stringsForField.toArray(new String[stringsForField.size()]), level);
        level.setField(field);
        return level;
    }

    private Tile[][] getTiles(String[] stringsForField, Level level) {
        Tile[][] field;
        field = new Tile[stringsForField[0].length()][stringsForField.length];
        for (int stringIndex = 0; stringIndex < stringsForField[0].length(); stringIndex++) {
            for (int arrayIndex = 0; arrayIndex < stringsForField.length; arrayIndex++) {
                Tile currentTile = new Tile(CHARACTER_TILE_MAP.get(stringsForField[arrayIndex].charAt(stringIndex)));
                if (currentTile.getTileType() != TileType.NONE) {
                    if (stringIndex == 0) {
                        checkInvalidTile(currentTile);
                        currentTile.setRight(true);
                    }
                    if (stringIndex == stringsForField[0].length() - 1) {
                        checkInvalidTile(currentTile);
                        currentTile.setLeft(true);
                    }
                    if (arrayIndex == 0) {
                        checkInvalidTile(currentTile);
                        currentTile.setBottom(true);
                    }
                    if (arrayIndex == stringsForField.length - 1) {
                        checkInvalidTile(currentTile);
                        currentTile.setTop(true);
                    }
                }
                field[stringIndex][arrayIndex] = currentTile;
                if (currentTile.getTileType() == TileType.START) {
                    level.setStartX(stringIndex);
                    level.setStartY(arrayIndex);
                }
            }
        }
        return field;
    }

    /**
     * Translates a {@link de.sopa.model.game.Level} to an sopa level specification
     * conform String array.
     *
     * @param level to translate.
     *
     * @return translated SOPA level specification conform String array.
     *
     */
    public String[] fromGameField(Level level) {
        Tile tiles[][] = level.getField();
        if(level.getId() == null) {
            level.setId(-1);
        }
        String levelAsStrings[] = new String[tiles[0].length + 4];
        levelAsStrings[0] = String.valueOf(level.getId());
        levelAsStrings[1] = String.valueOf(level.getMinimumMovesToSolve());
        levelAsStrings[2] = String.valueOf(level.getTilesCount());
        levelAsStrings[3] = "#";
        for(int y = 0; y < tiles[0].length; y++) {
            levelAsStrings[y + 4]= "";
            for (Tile[] tile : tiles) {
                levelAsStrings[y + 4] = levelAsStrings[y + 4] + String.valueOf(tile[y].getShortcut());
            }
        }
        return levelAsStrings;
    }

    private void checkInvalidTile(Tile currentTile) {
        if (currentTile.getTileType() == TileType.PUZZLE) {
            throw new IllegalArgumentException("Just Finish, Start and None allowed in the borders");
        }
    }

}
