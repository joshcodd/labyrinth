package models;
/**
 * When used, freezes tiles so they are temporarily fixed
 * @author George Boumphrey    @StudentID 851192
 */
public class IceTile extends ActionTile {
    private int turnsSinceUse;

    public IceTile() {
        this.turnsSinceUse = -1;
    }
    public void Action() {

    }

}
