package models;

/**
 * Represents a coordinate on the game board.
 * Stores a integer (x,y) game position, and allows it to be updated,
 * or retrieved.
 * @author Neil Woodhouse
 * @version 1.0
 */
public class Coord {
    private int xPos;
    private int yPos;

    /**
     * Create a coordinate.
     * @param x The position's x coordinate.
     * @param y The position's y coordinate.
     */
    public Coord(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * Get the x coordinate.
     * @return The position's x coordinate.
     */
    public int getX() {
        return xPos;
    }

    /**
     * Get the y coordinate.
     * @return The position's y coordinate.
     */
    public int getY() {
        return yPos;
    }

    /**
     * Sets new values for the x and y positions.
     * @param x The new x coordinate to be assigned.
     * @param y The new y coordinate to be assigned.
     */
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    /**
     * Get if the coord is empty or not.
     * @return True if the coordinate represents an invalid position,
     * false otherwise.
     */
    public boolean isEmpty() {
        return xPos == -1 || yPos == -1;
    }

    /**
     * Checks to see if the coord is equivalent to some object.
     * @param obj The object to be compared against.
     * @return If the coord is equivalent to specified object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            return xPos == ((Coord) obj).getX() && yPos == ((Coord) obj).getY();
        } else {
            return false;
        }
    }
}
