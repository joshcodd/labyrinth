package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameBoard {
    private int height;
    private int width;
    private String [][] board;
    private ArrayList<String> activeTiles;

    public GameBoard(int height, int width, HashMap<String, String> fixedTiles, ArrayList<String> randomTiles) {
        this.height = height;
        this.width = width;
        board = new String[height][width];
        activeTiles = new ArrayList<>();
        initializeBoard(fixedTiles, randomTiles);
    }

    public String insertTile(String tile, String edge, int row){
        String returnTile = "";

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

    public boolean isRowFixed(int row) {
        for (int i = 0; i < getWidth(); i++){
            if (board[row][i].equals("FTile")){
                return true;
            }
        }
        return false;
    }

    public boolean isColumnFixed(int column) {
        for (int i = 0; i < getHeight(); i++){
            if (board[i][column].equals("FTile")){
                return true;
            }
        }
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTileAt(int x, int y) {
        return board[x][y];
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.printf("%1$"+ 7 + "s", board[i][j]);
            }
            System.out.println();
        }
    }

    private void initializeBoard(HashMap<String, String> tiles, ArrayList<String> randomTiles){
        for (Map.Entry<String, String> tile : tiles.entrySet()) {
            String key = tile.getKey();
            String value = tile.getValue();
            String[] coords = key.split("\\s+");
            int x = Integer.parseInt(coords[0]);
            int y = Integer.parseInt(coords[1]);
            board[x][y] = value;
        }

        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j] == null){
                    board[i][j] = randomTiles.remove(0);
                }
            }
        }
    }
}
