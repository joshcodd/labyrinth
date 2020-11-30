package models;

/**
 * FloorTile sub class of Tile
 * @author James Charnock  @StudentID 1909700
 */
public class FloorTile extends Tile {
    private int orientation;
    private boolean isFixed;
    private ShapeOfTile shape;

    /**
     * @param orientation
     * @param isFixed
     * @param shape
     */
    public FloorTile(int orientation, boolean isFixed, ShapeOfTile shape) {
        this.orientation = orientation % 4;
        this.isFixed = isFixed;
        this.shape = shape;
    }

    /**
     * @return sets and prevents the time from being moved
     */
    public boolean isFixed() {
        return this.isFixed;
    }

    /**
     * @param fixed checks whether a tile cant move
     */
    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
    }

    /**
     * @return
     */
    public ShapeOfTile getShape() {
        return this.shape;
    }

    /**
     * @param direction
     */
    public void setOrientation(int direction) {
        this.orientation = direction % 4;
    }

    /**
     *
     */
    public void decrementOrientation() {
        if (this.orientation == 0) {
            this.orientation = 3;
        } else {
            this.orientation--;
        }
    }

    /**
     *
     */
    public void incrementOrientation() {
        if (this.orientation == 3) {
            this.orientation = 0;
        } else {
            this.orientation++;
        }
    }

    /**
     * @return the entry point if the user can enter the tile or not
     */
    public boolean[] getEntryPoints(){
        boolean[] entryPoints;
        if (shape == ShapeOfTile.BEND){
            entryPoints = new boolean[]{false, false ,true, true};
        } else if (shape == ShapeOfTile.T_SHAPE){
            entryPoints = new boolean[]{true, true ,false, true};
        } else if (shape == ShapeOfTile.STRAIGHT) {
            entryPoints = new boolean[]{false, true ,false, true};
        } else {
            entryPoints = new boolean[]{true, true, true, true};
        }

        for (int i = 0; i < getOrientation(); i++) {
            int j;
            boolean temp;
            temp = entryPoints[entryPoints.length - 1];
            for (j = entryPoints.length - 1; j > 0; j--) {
                entryPoints[j] = entryPoints[j - 1];
            }
            entryPoints[j] = temp;
        }
        return entryPoints;
    }

    /**
     * @return the direction the tile is facing
     */
    public int getOrientation() {
        return this.orientation;
    }

    /**
     * @return aome details about the floor tile to help with developing the game
     */
    public String toString() {
        return "Orientation: " + this.orientation + ", isFixed: " + this.isFixed + ", Shape: " + this.shape;
    }
}