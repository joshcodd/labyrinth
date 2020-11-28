package models;

/**
 *  adds a player to be selected
 * @author
 */
public class NewPlayer {

    public String profileName = "No Player Selected";
    public String colour = "Auto-assign colour";
    public boolean startFirst = false;

    /**
     * @return details about the player to help game development
     */
    @Override
    public String toString() {
        return profileName+" "+colour+" "+startFirst;
    }
}
