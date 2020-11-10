package game;


import java.util.ArrayList;

/**
 * ...
 *
 * @auther Andreas Eleftheriades
 * @StudentID 1906277
 */
public class TileBag {
    
    private ArrayList<Tile> tileBag;

    /**
     * Creates TileBag
     */
    public TileBag(){
        tileBag = new ArrayList<Tile>();

    }

    /**
     * Randomly removes and returns a tile from the tile bag
     * @return Random tile from TileBag
     */
    public Tile drawTile(){
        int randomIndex = (int)(Math.random() * tileBag.size());
        return tileBag.remove(randomIndex);
    }

    /**
     * Adds tile to tile bag
     * @param tile - passed tile to be added
     */
    public void addTiles(Tile tile){
        tileBag.add(tile);
    }

}
