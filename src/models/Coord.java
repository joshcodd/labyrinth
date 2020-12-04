package models;

/**
 * Stores a integer (x,y) game position, and allows it to be updated, or retrieved
 * @author Neil Woodhouse  studentID 851182
 * @version 1.0
 */
public class Coord {
    private int xPos;
    private int yPos;

    /**
     * @param x the position's x coordinate
     * @param y the position's y coordinate
     */
    public Coord(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * @return the x coordinate
     */
    public int getX() {
        return xPos;
    }

    /**
     * @return the y coordinate
     */
    public int getY() {
        return yPos;
    }

    /**
     * @param x the new x coordinate to be assigned
     * @param y the new y coordinate to be assigned
     */
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * @return Returns true if the coordinate represents an invalid position, false otherwise
     */
    public boolean isEmpty() {
        return xPos == -1 || yPos == -1;
    }

    /**
     * @return A string representation of the Coord object, displaying its X and Y position.
     */
    @Override
    public String toString() {
        return "Coord{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

    /**
     * @param obj the object to be compared against
     * @return Returns true if obj is a Coord type object, with equal X and Y values to this object
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Coord){
            return xPos == ((Coord) obj).getX() && yPos == ((Coord) obj).getY();
        } else {
            return false;
        }
    }
}
