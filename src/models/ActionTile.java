package models;

/**
 * Super Class for IceTile, FireTile, BackTrackTile and  DoubleMoveTile
 * @author Andreas Eleftheriades   StudentID 1906277
 */
public abstract class  ActionTile extends Tile {

    private int turnsSinceUse = -1;

    /**
     *
     */
    public abstract void Action();

    /**
     *
     */
    public void  clearAction(){

    }

    public void incrementTurnsSinceUse() {
        turnsSinceUse++;
    }

    public int getTurnsSinceUse() {
        return turnsSinceUse;
    }
}
