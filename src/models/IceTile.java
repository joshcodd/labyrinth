package models;
/**
 * When used, freezes tiles so they are temporarily fixed
 * @author George Boumphrey    @StudentID 851192
 */
public class IceTile extends ActionTile {
    private int turnsSinceUse;

    /**
     * constructor for ice tile
     */
    public IceTile() {
        this.turnsSinceUse = -1;
    }

    /**
     * activates the area in which tiles become locked
     */
    public void Action() {

    }

}
