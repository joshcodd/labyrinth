package game;
public class Tile {
    public Tile() {
    }

    public void Tile() {
    }

    public static void main(String[] args) {
        FloorTile myFTile = new FloorTile(1, false, ShapeOfTile.BEND);
        System.out.println(myFTile.toString());
        myFTile.setFixed(true);
        myFTile.setOrientation(3);
        System.out.println(myFTile.toString());
        myFTile.setOrientation(9);
        System.out.println(myFTile.toString());
        System.out.println(new Tile());
        FloorTile thisTile = new FloorTile(130, true, ShapeOfTile.GOAL_TILE);
        System.out.println(thisTile.toString());
    }
}