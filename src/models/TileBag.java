package models;
import java.util.ArrayList;

/**
 * Tile bag is used to store tiles for which the player can draw from.
 * @author Andreas Eleftheriades
 */
public class TileBag {
    private ArrayList<Tile> tileBag;

    /**
     * Creates a tile bag.
     */
    public TileBag() {
        tileBag = new ArrayList<>();
    }

    /**
     * Gets a random tile from the bag, while also removing it from the bag.
     * @return Random tile from TileBag.
     */
    public Tile drawTile() {
        int randomIndex = (int) (Math.random() * tileBag.size());
        return tileBag.remove(randomIndex);
    }

    /**
     * Adds a tile to the bag.
     * @param tile The tile to be added.
     */
    public void addTile(Tile tile) {
        tileBag.add(tile);
    }

    /**
     * @return The amount of tiles in the bag.
     */
    public int getSize() {
        return tileBag.size();
    }

}
