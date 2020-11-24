package models;
/**
 * Tile superclass
 * @author James Charnock
 * @StudentID 1909700
 */
public abstract class Tile {
    public Tile() {
    }

    /**
     * main method to test the Tile hierarchy
     */
    public static void main(String[] args) {
        FloorTile myFTile = new FloorTile(1, false, ShapeOfTile.BEND);
        System.out.println(myFTile.toString());
        myFTile.setFixed(true);
        myFTile.setOrientation(3);
        System.out.println(myFTile.toString());
        myFTile.setOrientation(9);
        System.out.println(myFTile.toString());
        FloorTile thisTile = new FloorTile(130, true, ShapeOfTile.GOAL_TILE);
        System.out.println(thisTile.toString());
    }
}