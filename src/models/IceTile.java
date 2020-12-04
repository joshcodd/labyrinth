package models;
/**
 * Represents an Ice tile, a form of action tile.
 * @author George Boumphrey
 */
public class IceTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * Creates a Ice action tile.
     */
    public IceTile() {
        this.turnsSinceUse = -1;
    }

}
