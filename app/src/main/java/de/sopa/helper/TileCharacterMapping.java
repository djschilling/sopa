package de.sopa.helper;

import java.util.HashMap;
import java.util.Map;

import de.sopa.model.game.Tile;
import de.sopa.model.game.TileType;

/**
 * @author David Schilling -davejs92@gmail.com
 */
public final class TileCharacterMapping {

    static final Map<Tile, Character> TILE_CHARACTER_MAP;
    static final Map<Character, Tile> CHARACTER_TILE_MAP;

    static {
        CHARACTER_TILE_MAP = new HashMap<>();
        CHARACTER_TILE_MAP.put('s', new Tile(false, false, false, false, TileType.START, 's'));
        CHARACTER_TILE_MAP.put('f', new Tile(false, false, false, false, TileType.FINISH, 'f'));
        CHARACTER_TILE_MAP.put('n', new Tile(false, false, false, false, TileType.NONE, 'n'));
        CHARACTER_TILE_MAP.put('o', new Tile(false, false, false, false, TileType.PUZZLE, 'o'));
        CHARACTER_TILE_MAP.put('t', new Tile(true, false, false, false, TileType.PUZZLE, 't'));
        CHARACTER_TILE_MAP.put('b', new Tile(false, true, false, false, TileType.PUZZLE, 'b'));
        CHARACTER_TILE_MAP.put('r', new Tile(false, false, false, true, TileType.PUZZLE, 'r'));
        CHARACTER_TILE_MAP.put('l', new Tile(false, false, true, false, TileType.PUZZLE, 'l'));
        CHARACTER_TILE_MAP.put('a', new Tile(false, false, true, true, TileType.PUZZLE, 'a'));
        CHARACTER_TILE_MAP.put('u', new Tile(false, true, false, true, TileType.PUZZLE, 'u'));
        CHARACTER_TILE_MAP.put('c', new Tile(false, true, true, false, TileType.PUZZLE, 'c'));
        CHARACTER_TILE_MAP.put('d', new Tile(false, true, true, true, TileType.PUZZLE, 'd'));
        CHARACTER_TILE_MAP.put('e', new Tile(true, false, false, true, TileType.PUZZLE, 'e'));
        CHARACTER_TILE_MAP.put('g', new Tile(true, false, true, false, TileType.PUZZLE, 'g'));
        CHARACTER_TILE_MAP.put('h', new Tile(true, false, true, true, TileType.PUZZLE, 'h'));
        CHARACTER_TILE_MAP.put('i', new Tile(true, true, false, false, TileType.PUZZLE, 'i'));
        CHARACTER_TILE_MAP.put('j', new Tile(true, true, false, true, TileType.PUZZLE, 'j'));
        CHARACTER_TILE_MAP.put('k', new Tile(true, true, true, false, TileType.PUZZLE, 'k'));
        CHARACTER_TILE_MAP.put('m', new Tile(true, true, true, true, TileType.PUZZLE, 'm'));

        TILE_CHARACTER_MAP = new HashMap<>();
        TILE_CHARACTER_MAP.put(new Tile(false, false, false, false, TileType.NONE, 'o'), 'n');
        TILE_CHARACTER_MAP.put(new Tile(false, false, false, false, TileType.PUZZLE, 'o'), 'o');
        TILE_CHARACTER_MAP.put(new Tile(true, false, false, false, TileType.PUZZLE, 'o'), 't');
        TILE_CHARACTER_MAP.put(new Tile(false, true, false, false, TileType.PUZZLE, 'o'), 'b');
        TILE_CHARACTER_MAP.put(new Tile(false, false, false, true, TileType.PUZZLE, 'o'), 'r');
        TILE_CHARACTER_MAP.put(new Tile(false, false, true, false, TileType.PUZZLE, 'o'), 'l');
        TILE_CHARACTER_MAP.put(new Tile(false, false, true, true, TileType.PUZZLE, 'o'), 'a');
        TILE_CHARACTER_MAP.put(new Tile(false, true, false, true, TileType.PUZZLE, 'o'), 'u');
        TILE_CHARACTER_MAP.put(new Tile(false, true, true, false, TileType.PUZZLE, 'o'), 'c');
        TILE_CHARACTER_MAP.put(new Tile(false, true, true, true, TileType.PUZZLE, 'o'), 'd');
        TILE_CHARACTER_MAP.put(new Tile(true, false, false, true, TileType.PUZZLE, 'o'), 'e');
        TILE_CHARACTER_MAP.put(new Tile(true, false, true, false, TileType.PUZZLE, 'o'), 'g');
        TILE_CHARACTER_MAP.put(new Tile(true, false, true, true, TileType.PUZZLE, 'o'), 'h');
        TILE_CHARACTER_MAP.put(new Tile(true, true, false, false, TileType.PUZZLE, 'o'), 'i');
        TILE_CHARACTER_MAP.put(new Tile(true, true, false, true, TileType.PUZZLE, 'o'), 'j');
        TILE_CHARACTER_MAP.put(new Tile(true, true, true, false, TileType.PUZZLE, 'o'), 'k');
        TILE_CHARACTER_MAP.put(new Tile(true, true, true, true, TileType.PUZZLE, 'o'), 'm');
    }

    private TileCharacterMapping() {

    }
}
