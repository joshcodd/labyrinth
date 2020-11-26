package models;
import java.util.*;

/**
 * Class to represent a game board in the game Labyrinth.
 * @author Josh Codd
 */
public class GameBoard {
    private int height;
    private int width;
    private FloorTile [][] board;
    private ActionTile [][] actionBoard;
    private ArrayList<ActionTile> activeTiles;

    /**
     * Constructs a game board.
     * @param height The height of the game board.
     * @param width The width of the game board.
     * @param fixedTiles The fixed tiles and their positions of the game board at start.
     * @param tileBag The tile bag of tiles to replace empty spaces on the board with.
     */
    public GameBoard(int height, int width, HashMap<Coord, FloorTile> fixedTiles, TileBag tileBag) {
        this.height = height;
        this.width = width;
        board = new FloorTile[height][width];
        actionBoard = new ActionTile[height][width];
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
                    actionBoard[row][i] = actionBoard[row][i - 1];
                }
                board[row][0] = tile;
                break;

            case "RIGHT" :
                returnTile = board[row][ 0];
                for (int i = 0; i < width - 1; i++){
                    board[row][i] = board[row][i + 1];
                    actionBoard[row][i] = actionBoard[row][i + 1];
                }
                board[row][width - 1] = tile;
                break;

            case "DOWN" :
                returnTile = board[height - 1][ row];
                for (int i = height - 1; i > 0; i--){
                    board[i][row] = board[i - 1][row];
                    actionBoard[i][row] = actionBoard[i - 1][row];
                }
                board[0][row] = tile;
                break;

            case "UP" :
                returnTile = board[0][row];
                for (int i = 0; i < height - 1; i++){
                    board[i][row] = board[i + 1][row];
                    actionBoard[i][row] = actionBoard[i + 1][row];
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
    public FloorTile getTileAt(Coord position) {
        return board[position.getX()][position.getY()];
    }

    /**
     * Checks the next tile in the 4 cardinal directions from the player, and returns the coordinates of the tiles
     * where a move to that tile would be legal.
     * @param player The player to get valid moves for.
     * @return The list of all valid moves.
     */
    public ArrayList<Coord> getValidMoves(Player player){
        ArrayList<Coord> validMoves = new ArrayList<>();
        Coord playerPosition = player.getCurrentPosition();

        boolean[] currentTile = getTileAt(playerPosition).getEntryPoints();
        boolean[] leftTile = playerPosition.getY() == 0 ? null :
                getTileAt(new Coord(playerPosition.getX(), playerPosition.getY() - 1)).getEntryPoints();
        boolean[] rightTile = playerPosition.getY() == width - 1 ? null :
                getTileAt(new Coord(playerPosition.getX(), playerPosition.getY() + 1)).getEntryPoints();
        boolean[] upTile = playerPosition.getX() == 0 ? null :
                getTileAt(new Coord(playerPosition.getX()  - 1, playerPosition.getY())).getEntryPoints();
        boolean[] downTile = playerPosition.getX() == height -1 ? null :
                getTileAt(new Coord(playerPosition.getX() + 1, playerPosition.getY())).getEntryPoints();

        if (leftTile != null && currentTile[3] && leftTile[1]){
            validMoves.add(new Coord(playerPosition.getX(), playerPosition.getY() - 1));
        }

        if (rightTile != null && currentTile[1] && rightTile[3]){
            validMoves.add(new Coord(playerPosition.getX(), playerPosition.getY() + 1));
        }

        if (upTile != null && currentTile[0] && upTile[2]){
            validMoves.add(new Coord(playerPosition.getX() - 1, playerPosition.getY()));
        }

        if (downTile != null && currentTile[2] && downTile[0]){
            validMoves.add(new Coord(playerPosition.getX() + 1, playerPosition.getY()));
        }
        return validMoves;
    }

    private void initializeBoard(HashMap<Coord, FloorTile> tiles, TileBag tileBag){
        for (Map.Entry<Coord, FloorTile> tile : tiles.entrySet()) {
            Coord key = tile.getKey();
            FloorTile value = tile.getValue();
            board[key.getX()][key.getY()] = value;
        }

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j] == null){
                    boolean pulledFloorTile = false;
                    while (!pulledFloorTile) {
                        Tile tile = tileBag.drawTile();
                        if (tile instanceof FloorTile){
                            pulledFloorTile = true;
                            board[i][j] = (FloorTile) tile;
                        } else {
                            tileBag.addTile(tile);
                        }
                    }
                }
            }
        }
    }

    public void addAction(ActionTile action, Coord position) {
        int x = position.getX();
        int y = position.getY();
        if (actionBoard[x][y] == null) {
            actionBoard[x][y] = action;
        }
    }

    public ActionTile getAction(Coord position) {
        return actionBoard[position.getX()][position.getY()];
    }

    public void refreshActionBoard(int numPlayers) {
        int turnsPerRound = (numPlayers * 2) - 1;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                ActionTile currentAction = actionBoard[x][y];
                currentAction.incrementTurnsSinceUse();
                if (currentAction instanceof FireTile && (currentAction.getTurnsSinceUse() >= (turnsPerRound * 2))) {
                    actionBoard[x][y] = null;
                }
                else if (currentAction instanceof IceTile && (currentAction.getTurnsSinceUse() >= turnsPerRound)) {
                    actionBoard[x][y] = null;
                }
            }
        }
    }

    //THIS IS A METHOD FOR TESTING -> WILL BE DELETED.
    public void printBoard() {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.printf("%1$"+ 15 + "s", board[i][j].getShape());
            }
            System.out.println();
        }
    }
}
