package game;

import java.util.Observer;

/**
 * Stores a integer (x,y) game position, and allows it to be updated, or retrieved
 * @author Neil Woodhouse
 * @studentID 851182
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

    @Override
    public String toString() {
        return "Coord{" +
                "xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Coord){
            return xPos == ((Coord) obj).getX() && yPos == ((Coord) obj).getY();
        } else {
            return false;
        }
    }
}
