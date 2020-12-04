package models;

/**
 * Super Class for IceTile, FireTile, BackTrackTile and  DoubleMoveTile
 * @author Andreas Eleftheriades   StudentID 1906277
 * @version 1.0
 */
public abstract class  ActionTile extends Tile {



  private int turnsSinceUse = -1;

    /**
     * insures that activates in an area or player is effect
     */
public abstract void Action();

    public void incrementTurnsSinceUse() {
        turnsSinceUse++;
    }

    public int getTurnsSinceUse() {
        return turnsSinceUse;
    }

    public void setTurnsSinceUse(int turns) {
        this.turnsSinceUse = turns;

    }
}