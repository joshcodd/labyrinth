package models;

public class NewPlayer {
    public String profileName = "No Player Selected";
    public String colour = "Auto-assign colour";
    public boolean startFirst = false;

    @Override
    public String toString() {
        return profileName+" "+colour+" "+startFirst;
    }
}
