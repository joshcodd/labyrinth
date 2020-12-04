package models;
/**
 * When used, inflames tiles so players cannot step on them
 * @author George Boumphrey    @StudentID 851192
 */
public class FireTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * constructor for fire tile
     */
    public FireTile() {
        this.turnsSinceUse = -1;
    }


    /**
     * activates the area in which the players can't enter
     */
    public void Action() {

    }
    
}
