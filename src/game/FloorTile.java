package game;

public class FloorTile extends Tile {
    private int orientation;
    private boolean isFixed;
    private ShapeOfTile shape;

    public FloorTile(int orientation, boolean isFixed, ShapeOfTile shape) {
        this.orientation = orientation % 4;
        this.isFixed = isFixed;
        this.shape = shape;
    }

    public boolean isFixed() {
        return this.isFixed;
    }

    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
    }

    public ShapeOfTile getShape() {
        return this.shape;
    }

    public void setOrientation(int direction) {
        this.orientation = direction % 4;
    }

    public void decrementOrientation() {
        if (this.orientation == 0) {
            this.orientation = 3;
        } else {
            this.orientation--;
        }
    }

    public void incrementOrientation() {
        if (this.orientation == 3) {
            this.orientation = 0;
        } else {
            this.orientation++;
        }
    }



    public int getOrientation() {
        return this.orientation;
    }

    public String toString() {
        return "Orientation: " + this.orientation + ", isFixed: " + this.isFixed + ", Shape: " + this.shape;
    }
}
