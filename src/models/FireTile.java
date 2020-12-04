package models;
/**
 * When used, inflames tiles so players cannot step on them.
 * @author George Boumphrey
 */
public class FireTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * Constructor for fire tile.
     */
    public FireTile() {
        this.turnsSinceUse = -1;
    }


    /**
     * Activates the area in which the players can't enter.
     */
    public void Action() {

    }
    
}
