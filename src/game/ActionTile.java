package game;

/**
 * Super Class for IceTile, FireTile, BackTrackTile and  DoubleMoveTile
 */
abstract class  ActionTile {

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
