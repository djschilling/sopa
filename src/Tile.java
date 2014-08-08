/**
 * David Schilling - davejs92@gmail.com
 */
public class Tile {

    private boolean top;
    private boolean bottom;
    private boolean left;
    private boolean right;
    private TileType tileType;

    public Tile(boolean top, boolean bottom, boolean left, boolean right, TileType tileType) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.tileType = tileType;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public TileType getTileType() {
        return tileType;
    }
}
