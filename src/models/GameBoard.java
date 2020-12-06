package models;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class to represent a game board in the game.
 * Allows tiles to be inserted and action tiles to be places.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameBoard {
    //Entry side index's
    private final int TOP_SIDE = 0;
    private final int LEFT_SIDE = 1;
    private final int DOWN_SIDE = 2;
    private final int RIGHT_SIDE = 3;

    private int height;
    private int width;
    private FloorTile[][] board;
    private ActionTile[][] actionBoard;

    /**
     * Creates a new game board.
     * @param height The height of the game board.
     * @param width The width of the game board.
     * @param fixedTiles The fixed tiles and their positions of the game
     *                   board at start.
     * @param tileBag The tile bag of tiles to replace empty spaces on
     *                the board with.
     */
    public GameBoard(int height, int width, HashMap<Coord,
            FloorTile> fixedTiles, TileBag tileBag) {
        this.height = height;
        this.width = width;
        board = new FloorTile[height][width];
        actionBoard = new ActionTile[height][width];
        initializeBoard(fixedTiles, tileBag);
    }

    /**
     * Creates a game board from an existing board.
     * @param height The height of the game board.
     * @param width The width of the game board.
     * @param boardMap The Floor Tiles on the board.
     * @param actionMap The Action Tiles on the board.
     */
    public GameBoard(int height, int width, HashMap<Coord, FloorTile> boardMap,
                     HashMap<Coord, ActionTile> actionMap) {
        this.height = height;
        this.width = width;
        board = new FloorTile[height][width];
        actionBoard = new ActionTile[height][width];
        for (Map.Entry<Coord, FloorTile> tile : boardMap.entrySet()) {
            Coord key = tile.getKey();
            FloorTile value = tile.getValue();
            board[key.getX()][key.getY()] = value;
        }
        for (Map.Entry<Coord, ActionTile> tile : actionMap.entrySet()) {
            Coord key = tile.getKey();
            ActionTile value = tile.getValue();
            actionBoard[key.getX()][key.getY()] = value;
        }
    }

    /**
     * Method to insert a tile into the game board from a
     * specified edge and row.
     * @param tile The tile to insert into the board.
     * @param edge The direction to insert the tile from.
     * @param row The row number for which the tile should be inserted.
     * @return The tile pushed off the opposite edge of the board.
     */
    public Tile insertTile(FloorTile tile, String edge, int row) {
        FloorTile returnTile = null;
        //Alter the board depending on direction tile is inserted.
        switch (edge) {
            case "LEFT" :
                returnTile = board[row][ width - 1];
                for (int i = width - 1; i > 0; i--) {
                    board[row][i] = board[row][i - 1];
                    actionBoard[row][i] = actionBoard[row][i - 1];
                }
                board[row][0] = tile;
                break;

            case "RIGHT" :
                returnTile = board[row][ 0];
                for (int i = 0; i < width - 1; i++) {
                    board[row][i] = board[row][i + 1];
                    actionBoard[row][i] = actionBoard[row][i + 1];
                }
                board[row][width - 1] = tile;
                actionBoard[row][width - 1] = null;
                break;

            case "DOWN" :
                returnTile = board[height - 1][ row];
                for (int i = height - 1; i > 0; i--) {
                    board[i][row] = board[i - 1][row];
                    actionBoard[i][row] = actionBoard[i - 1][row];
                }
                board[0][row] = tile;
                actionBoard[0][row] = null;
                break;

            default :
                returnTile = board[0][row];
                for (int i = 0; i < height - 1; i++) {
                    board[i][row] = board[i + 1][row];
                    actionBoard[i][row] = actionBoard[i + 1][row];
                }
                board[height - 1][row] = tile;
                actionBoard[height - 1][row] = null;
                break;
        }
        return returnTile;
    }

    /**
     * Gets if the specified row contains a fixed tile or and ice tile.
     * @param row The row to check.
     * @return Whether the row contains a fixed tile or not.
     */
    public boolean isRowFixed(int row) {
        for (int i = 0; i < getWidth(); i++) {
            if (board[row][i].isFixed()) {
                return true;
            }
            if (actionBoard[row][i] instanceof IceTile) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets if the specified column contains a fixed tile or an ice tile.
     * @param column The column to check.
     * @return Whether the row contains a fixed tile or not.
     */
    public boolean isColumnFixed(int column) {
        for (int i = 0; i < getHeight(); i++) {
            if (board[i][column].isFixed()) {
                return true;
            }
            if (actionBoard[i][column] instanceof IceTile) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the height of the game board.
     * @return Height of the game board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the width of the game board.
     * @return Width of the game board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the tile at a specified index of the board.
     * @param position The coordinate of the tile to get.
     * @return Tile at the specified position.
     */
    public FloorTile getTileAt(Coord position) {
        return board[position.getX()][position.getY()];
    }

    /**
     * Checks the adjacent tile in the 4 cardinal directions from the player,
     * and returns the coordinates of the tiles where a move to that tile
     * would be legal.
     * @param player The player to get valid moves for.
     * @return All valid moves.
     */
    public ArrayList<Coord> getValidMoves(Player player) {
        ArrayList<Coord> validMoves = new ArrayList<>();
        Coord playerPosition = player.getCurrentPosition();
        boolean[] currentTile = getTileAt(playerPosition).getEntryPoints();

        boolean[] leftTile = playerPosition.getY() == 0 ? null
                : getTileAt(new Coord(playerPosition.getX(),
                playerPosition.getY() - 1)).getEntryPoints();

        boolean[] rightTile = playerPosition.getY() == width - 1 ? null
                : getTileAt(new Coord(playerPosition.getX(),
                playerPosition.getY() + 1)).getEntryPoints();

        boolean[] upTile = playerPosition.getX() == 0 ? null
                : getTileAt(new Coord(playerPosition.getX() - 1,
                playerPosition.getY())).getEntryPoints();

        boolean[] downTile = playerPosition.getX() == height - 1 ? null
                : getTileAt(new Coord(playerPosition.getX() + 1,
                playerPosition.getY())).getEntryPoints();

        /*Check if current tile edge matches (has an entry point) on the
        adjacent side of the tile in question. If there is, then that
        is a valid move and should be added to the list.*/
        if (leftTile != null && currentTile[RIGHT_SIDE]
                && leftTile[LEFT_SIDE]) {
            validMoves.add(new Coord(playerPosition.getX(),
                    playerPosition.getY() - 1));
        }
        if (rightTile != null && currentTile[LEFT_SIDE]
                && rightTile[RIGHT_SIDE]) {
            validMoves.add(new Coord(playerPosition.getX(),
                    playerPosition.getY() + 1));
        }
        if (upTile != null && currentTile[TOP_SIDE]
                && upTile[DOWN_SIDE]) {
            validMoves.add(new Coord(playerPosition.getX() - 1,
                    playerPosition.getY()));
        }
        if (downTile != null && currentTile[DOWN_SIDE]
                && downTile[TOP_SIDE]) {
            validMoves.add(new Coord(playerPosition.getX() + 1,
                    playerPosition.getY()));
        }
        return validMoves;
    }

    /**
     * Plays a specified action tile on to the board.
     * @param action The selected action tile to be placed.
     * @param position The location on the board to place.
     */
    public void addAction(ActionTile action, Coord position) {
        int x = position.getX();
        int y = position.getY();
        if (position.isInBounds(height, width) && actionBoard[x][y] == null) {
            actionBoard[x][y] = action;
        }
    }

    /**
     * Gets a action tile at a specified location on the board.
     * @param position The position on the board.
     * @return The actions tile at the specified location.
     */
    public ActionTile getAction(Coord position) {
        if (position.isInBounds(height, width)) {
            return actionBoard[position.getX()][position.getY()];
        } else {
            return null;
        }
    }

    /**
     * Removes action tiles from the board once they have expired.
     * @param numPlayers The number of players.
     */
    public void refreshActionBoard(int numPlayers) {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (actionBoard[x][y] != null) {
                    ActionTile currentAction = actionBoard[x][y];
                    currentAction.incrementTurnsSinceUse();
                    if (currentAction instanceof FireTile
                            && ((currentAction
                            .getTurnsSinceUse() >= (numPlayers * 2)))) {
                        actionBoard[x][y] = null;
                    } else if (currentAction instanceof IceTile
                            && ((currentAction
                            .getTurnsSinceUse() >= numPlayers))) {
                        actionBoard[x][y] = null;
                    }
                }
            }
        }
    }

    /**
     * Initializes the board with the specified tiles.
     * @param tiles The tiles to be placed at the exact coordinates specified.
     * @param tileBag Tiles to fill up any empty spaces with.
     */
    private void initializeBoard(HashMap<Coord, FloorTile> tiles,
                                 TileBag tileBag) {
        for (Map.Entry<Coord, FloorTile> tile : tiles.entrySet()) {
            Coord key = tile.getKey();
            FloorTile value = tile.getValue();
            board[key.getX()][key.getY()] = value;
        }
        fillEmptySpace(tileBag);
    }

    /**
     * Fills remaining spaces on the board with tiles from a tile bag.
     * @param tileBag Tiles to fill up any empty spaces with.
     */
    public void fillEmptySpace(TileBag tileBag) {
        //Fill empty spaces with tiles from the tile bag.
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null) {
                    boolean pulledFloorTile = false;
                    /* Draw and place back until a floor tile
                    is drawn */
                    while (!pulledFloorTile) {
                        Tile tile = tileBag.drawTile();
                        if (tile instanceof FloorTile) {
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
}
