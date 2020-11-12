import game.GameBoard;
import javafx.application.Application;
import javafx.stage.Stage;
import views.scenes.GameScene;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        HashMap<String, String> tiles = new HashMap<>();
        tiles.put("0 0", "FTile");
        tiles.put("0 1", "1");
        tiles.put("0 2", "FTile");
        tiles.put("0 3", "FTile");
        tiles.put("0 4", "FTile");

        tiles.put("2 0", "FTile");
        tiles.put("2 1", "3");
        tiles.put("2 2", "FTile");
        tiles.put("2 3", "FTile");
        tiles.put("2 4", "FTile");

        tiles.put("4 0", "FTile");
        tiles.put("4 1", "5");
        tiles.put("4 2", "FTile");
        tiles.put("4 3", "FTile");
        tiles.put("4 4", "FTile");

        ArrayList<String> randomTiles = new ArrayList<>();
        randomTiles.add("1");
        randomTiles.add("2");
        randomTiles.add("3");
        randomTiles.add("4");
        randomTiles.add("5");
        randomTiles.add("Tile");
        randomTiles.add("4");
        randomTiles.add("Tile");
        randomTiles.add("Tile");
        randomTiles.add("Tile");

        GameBoard board = new GameBoard(5,5, tiles, randomTiles);
        GameScene game = new GameScene(primaryStage, board);
        board.insertTile("ins", "UP", 1);
        game.drawGameBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}