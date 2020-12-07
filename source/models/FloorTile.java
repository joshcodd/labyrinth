package models;

/**
 * Represents a floor tile.
 * @author James Charnock
 * @version 1.0
 */
public class FloorTile extends Tile {
    private final int ORIENTATION_MAX = 3;
    private final int ORIENTATION_MIN = 0;
    private int orientation;
    private boolean isFixed;
    private ShapeOfTile shape;

    /**
     * Creates a floor tile.
     * @param orientation The orientation of the tile.
     * @param isFixed If the file is fixed to the board or not.
     * @param shape The shape of the tile.
     */
    public FloorTile(int orientation, boolean isFixed, ShapeOfTile shape) {
        this.orientation = orientation % (ORIENTATION_MAX + 1);
        this.isFixed = isFixed;
        this.shape = shape;
    }

    /**
     * Gets if the tile is fixed to the board or not.
     * @return If the tile is fixed to the board or not.
     */
    public boolean isFixed() {
        return this.isFixed;
    }

    /**
     * Gets the shape of the tile.
     * @return The shape of the tile.
     */
    public ShapeOfTile getShape() {
        return this.shape;
    }

    /**
     * Decrements the orientation of the tile. This rotates
     * the tile anti-clockwise.
     */
    public void decrementOrientation() {
        if (this.orientation == ORIENTATION_MIN) {
            this.orientation = ORIENTATION_MAX;
        } else {
            this.orientation--;
        }
    }

    /**
     * Increments the orientation of the tile. This rotates
     * the tile clockwise.
     */
    public void incrementOrientation() {
        if (this.orientation == ORIENTATION_MAX) {
            this.orientation = ORIENTATION_MIN;
        } else {
            this.orientation++;
        }
    }

    /**
     * Gets the entry points for the tile. These are the sides of the
     * tile that a player can move in to.
     * @return The entry points of the tile.
     */
    public boolean[] getEntryPoints() {
        boolean[] entryPoints;

        //Array indexing = [top edge, left edge, bottom edge, right edge]
        if (shape == ShapeOfTile.BEND) {
            entryPoints = new boolean[] {false, false, true, true};
        } else if (shape == ShapeOfTile.T_SHAPE) {
            entryPoints = new boolean[] {true, true, false, true};
        } else if (shape == ShapeOfTile.STRAIGHT) {
            entryPoints = new boolean[] {false, true, false, true};
        } else {
            entryPoints = new boolean[] {true, true, true, true};
        }

        //Rotates the entry points depending on current orientation.
        for (int i = 0; i < getOrientation(); i++) {
            int j;
            boolean temp = entryPoints[entryPoints.length - 1];
            for (j = entryPoints.length - 1; j > 0; j--) {
                entryPoints[j] = entryPoints[j - 1];
            }
            entryPoints[j] = temp;
        }
        return entryPoints;
    }

    /**
     * Get the orientation of the tile.
     * @return The tile's orientation.
     */
    public int getOrientation() {
        return this.orientation;
    }

}
