package models;
/**
 * When used, freezes tiles so they are temporarily fixed
 * @author George Boumphrey
 */
public class IceTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * Constructor for ice tile.
     */
    public IceTile() {
        this.turnsSinceUse = -1;
    }

    /**
     * Activates the area in which tiles become locked.
     */
    public void Action() {

    }

}
