package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to represent a game board in the game Labyrinth.
 * @author Josh Codd
 */
public class GameBoard {
    private int height;
    private int width;
    private FloorTile [][] board;
    private ArrayList<ActionTile> activeTiles;

    /**
     * Constructs a game board.
     * @param height The height of the game board.
     * @param width The width of the game board.
     * @param fixedTiles The fixed tiles and their positions of the game board at start.
     * @param tileBag The tile bag of tiles to replace empty spaces on the board with.
     */
    public GameBoard(int height, int width, HashMap<String, FloorTile> fixedTiles, TileBag tileBag) {
        this.height = height;
        this.width = width;
        board = new FloorTile[height][width];
        activeTiles = new ArrayList<>();
        initializeBoard(fixedTiles, tileBag);
    }

    /**
     * Method to insert a tile into the game board from a specified edge and row.
     * @param tile The tile to insert into the board.
     * @param edge The direction to insert the tile from.
     * @param row The row number for which the tile should be inserted.
     * @return The tile pushed off the opposite edge of the board.
     */
	public Tile insertTile(FloorTile tile, String edge, int row){
        FloorTile returnTile = null;

        switch (edge) {
            case "LEFT" :
                returnTile = board[row][ width - 1];
                for (int i = width - 1; i > 0; i--){
                    board[row][i] = board[row][i - 1];
                }
                board[row][0] = tile;
                break;

            case "RIGHT" :
                returnTile = board[row][ 0];
                for (int i = 0; i < width - 1; i++){
                    board[row][i] = board[row][i + 1];
                }
                board[row][width - 1] = tile;
                break;

            case "DOWN" :
                returnTile = board[height - 1][ row];
                for (int i = height - 1; i > 0; i--){
                    board[i][row] = board[i - 1][row];
                }
                board[0][row] = tile;
                break;

            case "UP" :
                returnTile = board[0][row];
                for (int i = 0; i < height - 1; i++){
                    board[i][row] = board[i + 1][row];
                }
                board[height - 1][row] = tile;
                break;
        }
        return returnTile;
    }

    /**
     * Method that returns if the specified row contains a fixed tile.
     * @param row The row to check.
     * @return Whether the row contains a fixed tile or not.
     */
    public boolean isRowFixed(int row) {
        for (int i = 0; i < getWidth(); i++){
            if (board[row][i].isFixed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that returns if the specified column contains a fixed tile.
     * @param column The column to check.
     * @return Whether the row contains a fixed tile or not.
     */
    public boolean isColumnFixed(int column) {
        for (int i = 0; i < getHeight(); i++){
            if (board[i][column].isFixed()){
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get the height of the game board.
     * @return Height of the game board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method to get the width of the game board.
     * @return Width of the game board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Method to get the tile at a specified index of the board.
     * @return Tile at the specified position.
     */
    public FloorTile getTileAt(int x, int y) {
        return board[x][y];
    }

    private void initializeBoard(HashMap<String, FloorTile> tiles, TileBag tileBag){
        for (Map.Entry<String, FloorTile> tile : tiles.entrySet()) {
            String key = tile.getKey();
            FloorTile value = tile.getValue();
            String[] coords = key.split("\\s+");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            board[x][y] = value;
        }

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j] == null){
                    Tile tile = tileBag.drawTile();
                    if (tile instanceof FloorTile){
                        board[i][j] = (FloorTile) tile;
                    } else {
                        // looop to get other tile
                    }
                }
            }
        }
    }

    //THIS IS A METHOD FOR TESTING -> WILL BE DELETED.
    public void printBoard() {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.printf("%1$"+ 7 + "s", board[i][j]);
            }
            System.out.println();
        }
    }
}
