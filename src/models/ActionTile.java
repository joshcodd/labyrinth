package models;

/**
 * Super Class for IceTile, FireTile, BackTrackTile and  DoubleMoveTile
 * @author Andreas Eleftheriades    @StudentID 1906277
 */
public abstract class  ActionTile extends Tile {

    /**
     *
     */
    private int turnsSinceUse;

    /**
     *
     */
    public abstract void Action();

    /**
     *
     */
    public void  clearAction(){

    }
}
