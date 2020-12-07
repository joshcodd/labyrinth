package models;

/**
 * Represents an action tile.
 * @author Andreas Eleftheriades
 * @version 1.0
 */
public abstract class ActionTile extends Tile {
    private int turnsSinceUse = -1;

    /**
     * Increments turns since use.
     */
    public void incrementTurnsSinceUse() {
        turnsSinceUse++;
    }

    /**
     * Gets the action tiles turns since use.
     * @return The turns since use.
     */
    public int getTurnsSinceUse() {
        return turnsSinceUse;
    }

    /**
     * Sets the action tiles turns since use.
     * @param turns The value of turns since use.
     */
    public void setTurnsSinceUse(int turns) {
        this.turnsSinceUse = turns;
    }
}
