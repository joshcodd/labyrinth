package models;
/**
 * Represents a fire tile, a form of action tile.
 * @author George Boumphrey
 */
public class FireTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * Creates a fire tile.
     */
    public FireTile() {
        this.turnsSinceUse = -1;
    }
}
