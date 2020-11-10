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

    public void printBoard() {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(String.format("%1$"+ 7 + "s", board[i][j]));
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

class testGB{
    public static void main (String[] args) {

        HashMap<String, String> tiles = new HashMap<>();
        tiles.put("0 0", "FTile");
        tiles.put("0 1", "FTile");
        tiles.put("0 2", "FTile");
        tiles.put("0 3", "FTile");
        tiles.put("0 4", "FTile");

        tiles.put("2 0", "FTile");
        tiles.put("2 1", "FTile");
        tiles.put("2 2", "FTile");
        tiles.put("2 3", "FTile");
        tiles.put("2 4", "FTile");

        tiles.put("4 0", "FTile");
        tiles.put("4 1", "FTile");
        tiles.put("4 2", "FTile");
        tiles.put("4 3", "FTile");
        tiles.put("4 4", "FTile");

        ArrayList<String> randomTiles = new ArrayList<>();
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");

        GameBoard gb = new GameBoard(5, 5, tiles, randomTiles);
        gb.printBoard();
    }
}
