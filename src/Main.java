import game.*;
import javafx.application.Application;
import javafx.stage.Stage;
import views.scenes.GameScene;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {
        HashMap<String, FloorTile> tiles = new HashMap<>();
        tiles.put("0 0", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("0 1", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("0 2", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("0 3", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("0 4", new FloorTile(0, true, ShapeOfTile.CROSSROADS));

 //      tiles.put("1 1", "FTile");
//        tiles.put("3 1", "FTile");

        tiles.put("2 0", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("2 1", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("2 2", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("2 3", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("2 4", new FloorTile(0, true, ShapeOfTile.CROSSROADS));

        tiles.put("4 0", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("4 1", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("4 2", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("4 3", new FloorTile(0, true, ShapeOfTile.CROSSROADS));
        tiles.put("4 4", new FloorTile(0, true, ShapeOfTile.CROSSROADS));

        TileBag tileBag = new TileBag();
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));
        tileBag.addTile(new FloorTile(0, false, ShapeOfTile.BEND));


        GameBoard board = new GameBoard(5, 5, tiles, tileBag);
        board.printBoard();
        GameScene game = new GameScene(primaryStage, board);
        //board.insertTile("ins", "UP", 1);
        //game.drawGameBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}