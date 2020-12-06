package controllers;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import views.scenes.MenuScene;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller class for GameScene. Runs through a game and then renders
 * changes throughout the game to the view.
 * @author Josh Codd, Neil Woodhouse
 */
public class GameController implements Initializable {
    private final int TILE_SIZE = 50;
    private final int TANK_SIZE = 32;
    private final int MAX_ROTATE = 360;
    private final int PACKAGE_LENGTH = 7;
    private final int ACTION_TILE_SIZE = 100;
    private final int QUARTER_TURN = 90;
    private final int HALF_TURN = 180;
    private final int THIRD_TURN = 270;
    private final int NO_TURN = 0;
    private final int DOWN_ORIENTATION = 2;
    private final int LEFT_ORIENTATION = 1;
    private final int RIGHT_ORIENTATION = 3;
    private final String RESOURCES_PATH = "/resources/";

    @FXML
    private GridPane bottomButtons;
    @FXML
    private GridPane topButtons;
    @FXML
    private GridPane leftButtons;
    @FXML
    private GridPane rightButtons;
    @FXML
    private Label playerLabel;
    @FXML
    private HBox boardArea;
    @FXML
    private javafx.scene.image.ImageView selectedTile;
    @FXML
    private Button actionButton;
    @FXML
    private Button continueButton;
    @FXML
    private ImageView incrementOrientation;
    @FXML
    private ImageView decrementOrientation;
    @FXML
    private Button drawTile;
    @FXML
    private GridPane gameBoardPane;
    @FXML
    private VBox playerConsole;
    @FXML
    private VBox menu;
    @FXML
    private Button menuButton;
    @FXML
    private Button muteButton;
    @FXML
    private Button saveButton;
    @FXML
    private MediaView backgroundMusic;
    @FXML
    private HBox actionTilePane;
    @FXML
    private Button quitButton;
    @FXML
    private VBox window;
    private ActionTile selectedActionTile;
    private GameBoard gameBoard;
    private SimpleDoubleProperty tileSize = new SimpleDoubleProperty(0);
    private Game game;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;

    /**
     * Constructs a GameController.
     * @param game The game to be played.
     * @param stage The stage that this application is being displayed on.
     * @param backgroundMusic The audio to play in the background.
     */
    public GameController(Game game, Stage stage, MediaPlayer backgroundMusic) {
        this.gameBoard = game.getGameBoard();
        this.game = game;
        this.primaryStage = stage;
        this.mediaPlayer = backgroundMusic;
    }

    /**
     * Initializes the Game and the Game GUI to be ready for start of game.
     * @param location The location used to resolve relative paths for the root
     *                object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null
     *                  if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //All button event handlers.
        topButtons.setTranslateX(TILE_SIZE);
        bottomButtons.setTranslateX(TILE_SIZE);
        tileSize.bind(boardArea.heightProperty()
                .divide(gameBoard.getHeight() + 2));
        setPlayerLabel(game.getCurrentPlayerName() + "'s turn'");
        backgroundMusic.setMediaPlayer(mediaPlayer);
        muteButton.setText(backgroundMusic.getMediaPlayer()
                .isMute() ? "Un-mute" : "Mute");
        menuButton.setOnMouseClicked((event) -> handleMenuClick());
        drawTile.setOnAction((event) -> handleDrawTile());
        decrementOrientation.setOnMouseClicked(event ->
                handleOrientationDecrement());
        incrementOrientation.setOnMouseClicked(event ->
                handleOrientationIncrement());
        continueButton.setOnAction((event) -> handleContinue());
        continueButton.setDisable(true);
        actionButton.setDisable(true);
        saveButton.setOnMouseClicked(event -> handleSaveClick());
        quitButton.setOnMouseClicked(event ->
                new MenuScene(primaryStage, backgroundMusic.getMediaPlayer()));
        actionButton.setOnMouseClicked(e -> handleActionClick());
        muteButton.setOnMouseClicked(event -> {
            backgroundMusic.getMediaPlayer()
                    .setMute(!backgroundMusic.getMediaPlayer().isMute());
            muteButton.setText(backgroundMusic
                    .getMediaPlayer().isMute() ? "UnMute" : "Mute");
        });
        updateActionTileHand();
        drawActions();
        window.setOnMouseMoved(this::setTankSpin);

        boolean isActionTile = game.getCurrentTile() != null
                && game.getCurrentTile() instanceof ActionTile;
        boolean isFloorTile = game.getCurrentTile() != null
                && game.getCurrentTile() instanceof FloorTile;

        // Handles if a saved game has been loaded.
        if (isActionTile) {
            selectedActionTile = (ActionTile) game.getCurrentTile();
            selectedTile.setImage(new Image(RESOURCES_PATH
                    + selectedActionTile.getClass().getName()
                    .substring(PACKAGE_LENGTH) + ".png"));
            continueButton.setDisable(false);
            actionButton.setDisable(false);
            drawTile.setDisable(true);
        } else if (isFloorTile) {
            FloorTile tile = (FloorTile) game.getCurrentTile();
            selectedTile.setImage(getTileImage(tile).getImage());
            drawTile.setDisable(true);
        }
    }

    /**
     * Draws buttons for inserting tiles at the ends of every row or column
     * where the rows are not fixed. That is, there are no fixed tiles in
     * that row or column.
     * @param visible If the arrows should be visible or hidden.
     */
    public void updateArrows(boolean visible) {
        clearArrows();
        // So arrow size holds even when cleared
        ColumnConstraints gridWidth = new ColumnConstraints();
        gridWidth.minWidthProperty().bind(tileSize);
        RowConstraints gridHeight = new RowConstraints();
        gridHeight.minHeightProperty().bind(tileSize);

        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                // Left and right arrows
                if (j == 0) {
                    leftButtons.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrow("LEFT", i,
                            DOWN_ORIENTATION);
                    arrow.setVisible(visible && !gameBoard.isRowFixed(i));
                    leftButtons.add(new StackPane(arrow), j, i);
                } else if (j == gameBoard.getWidth() - 1) {
                    rightButtons.getRowConstraints().add(gridHeight);
                    ImageView arrow = getArrow("RIGHT", i, NO_TURN);
                    arrow.setVisible(visible && !gameBoard.isRowFixed(i));
                    rightButtons.add(new StackPane(arrow), j, i);
                }

                //Up and down arrows.
                if (i == 0) {
                    topButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrow("DOWN", j,
                            RIGHT_ORIENTATION);
                    arrow.setVisible(visible && !gameBoard.isColumnFixed(j));
                    topButtons.add(new StackPane(arrow), j, i);
                } else if (i == gameBoard.getHeight() - 1) {
                    bottomButtons.getColumnConstraints().add(gridWidth);
                    ImageView arrow = getArrow("UP", j, LEFT_ORIENTATION);
                    arrow.setVisible(visible && !gameBoard.isColumnFixed(j));
                    bottomButtons.add(new StackPane(arrow), j, i);
                }
            }
        }
    }

    /**
     * Draws the players currently in the game to the scene at their
     * current positions.
     */
    public void drawPlayers() {
        for (Player player : game.getPlayers()) {
            StackPane tile = getTilePane(player.getCurrentPosition().getX(),
                    player.getCurrentPosition().getY());
            ImageView tank = new ImageView(RESOURCES_PATH
                    + player.getColour() + ".png");
            tank.setFitHeight(TANK_SIZE);
            tank.setFitWidth(TANK_SIZE);
            tile.getChildren().add(tank);
        }
    }

    /**
     * Updates the current game board GUI being displayed in the scene.
     */
    public void updateGameBoard() {
        gameBoardPane.getChildren().clear();
        for (int i = 0; i < gameBoard.getHeight(); i++) {
            for (int j = 0; j < gameBoard.getWidth(); j++) {
                gameBoardPane.add(new StackPane(getTileImage(gameBoard
                        .getTileAt(new Coord(i, j)))), j, i);
            }
        }
        drawActions();
    }

    /**
     * Draws the action tiles that the current player has to screen.
     */
    private void updateActionTileHand() {
        actionTilePane.getChildren().clear();
        ArrayList<ActionTile> currentActionTiles
                = game.getCurrentPlayer().getActionTiles();
        for (int i = 0; i < currentActionTiles.size(); i++) {
            int actionTileNum = i;

            String imagePath = RESOURCES_PATH + currentActionTiles
                    .get(i).getClass().getName().substring(PACKAGE_LENGTH)
                    + ".png";
            ImageView actionTile = new ImageView(new Image(imagePath));
            actionTile.setFitWidth(ACTION_TILE_SIZE);
            actionTile.setFitHeight(ACTION_TILE_SIZE);
            actionTile.getStyleClass().add("hover-effect");

            actionTile.setOnMouseClicked(event -> {
                if (!actionButton.isDisable()) {
                    selectedActionTile = currentActionTiles.get(actionTileNum);
                    selectedTile.setImage(new Image(RESOURCES_PATH
                            + currentActionTiles.get(actionTileNum)
                            .getClass().getName().substring(PACKAGE_LENGTH)
                            + ".png"));
                    selectedTile.setRotate(NO_TURN);
                    game.setCurrentTile(selectedActionTile);
                }
            });

            actionTilePane.getChildren().add(actionTile);
        }
    }


    /**
     * Retrieves the available moves for a player.
     * @param moves The game board valid moves for a player.
     * @return The actual available moves for the player on this turn.
     */
    private ArrayList<Coord> availableMoves(ArrayList<Coord> moves) {
        for (Player player : game.getPlayers()) {
            moves.remove(player.getCurrentPosition());
        }
        //Remove fire tiles.
        moves.removeIf(move -> gameBoard.getAction(move) instanceof FireTile);
        return moves;
    }

    /**
     * Updates tiles so that they are selectable and can be used for a player
     * to move on to that tile. Tiles are only updated if that tile is a
     * possible and legal move.
     * @param moves The moves that are available to the current player.
     */
    private void updateMoves(ArrayList<Coord> moves) {
        availableMoves(moves);
        for (Node node : gameBoardPane.getChildren()) {
            Coord curr = new Coord(GridPane.getRowIndex(node),
                    GridPane.getColumnIndex(node));

            if (moves.contains(curr)) {
                StackPane tile = (StackPane) node;
                ImageView x = new ImageView(RESOURCES_PATH + "X.png");
                x.setFitWidth(TILE_SIZE);
                x.setFitHeight(TILE_SIZE);
                tile.getChildren().add(x);
                tile.getStyleClass().add("tile-selection");
                tile.setOnMouseClicked(event -> {
                    window.setOnMouseMoved(null);
                    tile.getChildren().remove(x);
                    animateMove(curr);
                    game.getCurrentPlayer().movePlayer(curr);
                    new AudioPlayer().clickPlay();
                });
            }
        }
    }

    /**
     * Retrieves the player sprite for a player.
     * @param tile The tile that the player is standing on.
     * @return The player sprite.
     */
    private ImageView getPlayer(StackPane tile) {
        return (ImageView) tile.getChildren().get(tile.getChildren()
                .size() - 1);
    }

    /**
     * Retrieves the tile pane at a specified index of the board.
     * @param x The x coordinate of the tile.
     * @param y The y coordinate of the tile.
     * @return The tile pane at that location.
     */
    private StackPane getTilePane(int x, int y) {
        StackPane tile = null;
        for (Node node : gameBoardPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == y
                    && GridPane.getRowIndex(node) == x) {
                tile = (StackPane) node;
            }
        }
        return tile;
    }

    /**
     * Makes players available to be selected to be backtracked to an earlier
     * position.
     */
    private void selectBacktrack() {
        for (Player player : game.getPlayers()) {
            if (player.canBackTrack() && player != game.getCurrentPlayer()) {
                StackPane tile = getTilePane(player.getCurrentPosition().getX(),
                        player.getCurrentPosition().getY());
                tile.setOnMouseClicked(event -> backtrackPlayer(player));
                tile.getStyleClass().add("tile-selection");
            }
        }
    }

    /**
     * Animates a player sprite as it moves from one tile to another.
     * @param destination The Coordinate of the tile the player is moving to.
     */
    private void animateMove(Coord destination) {
        Coord currPosition = game.getCurrentPlayer().getCurrentPosition();
        StackPane currentPane = getTilePane(currPosition.getX(),
                currPosition.getY());
        StackPane destPane = getTilePane(destination.getX(),
                destination.getY());
        StackPane finalPane = getTilePane(gameBoard.getHeight() - 1,
                gameBoard.getWidth() - 1);
        ImageView tank = getPlayer(currentPane);

        Point2D tankReal = currentPane.localToScene(currentPane.getWidth() / 2,
                currentPane.getHeight() / 2);
        Point2D tankDest = destPane.localToScene(destPane.getWidth() / 2,
                destPane.getHeight() / 2);
        Point2D tankFake = finalPane.localToScene(finalPane.getWidth() / 2,
                finalPane.getHeight() / 2);

        double fakeX = tankReal.getX() - tankFake.getX();
        double fakeY = tankReal.getY() - tankFake.getY();
        double transX = tankDest.getX() - tankFake.getX();
        double transY = tankDest.getY() - tankFake.getY();
        currentPane.getChildren().remove(tank);
        finalPane.getChildren().add(tank);
        tank.setTranslateX(fakeX);
        tank.setTranslateY(fakeY);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(1));
        translateTransition.setToX(transX);
        translateTransition.setToY(transY);
        translateTransition.setNode(tank);
        translateTransition.play();

        translateTransition.setOnFinished(e -> {
            if (continueButton.isDisabled() && drawTile.isDisabled()) {
                nextRound();
            }
            updateGameBoard();
            drawPlayers();
        });
    }

    /**
     * Set a message to displayed in the player label.
     * @param message The message to be displayed.
     */
    private void setPlayerLabel(String message) {
        playerLabel.setText(message);
    }

    /**
     * Adds selectable ability to tiles, allowing an action tile to be placed
     * on it.
     * @param action The action tile to be played.
     * @param node The current tile being inspected.
     * @param positions The surrounding tiles of placement.
     */
    private void allowSelect(Coord[] positions, Node node, ActionTile action) {
        StackPane tile = (StackPane) node;
        tile.getStyleClass().add("tile-selection");
        tile.setOnMouseClicked(event -> {
            for (Coord pos: positions) {
                if (!pos.isEmpty()) {
                    if (action instanceof FireTile) {
                        gameBoard.addAction(new FireTile(), pos);
                    } else if (action instanceof IceTile) {
                        gameBoard.addAction(new IceTile(), pos);
                    }
                }
            }
            updateGameBoard();
            drawPlayers();
            continueButton.setDisable(false);
        });
    }

    /**
     * Checks if its valid for an action tile to be placed at a position
     * on the board.
     * @param action The action tile to be played.
     * @param positions The surrounding tiles of placement.
     * @return If the action placement is valid or not.
     */
    private boolean isPlacementValid(ActionTile action, Coord[] positions) {
        if (action instanceof FireTile) {
            for (Player player : game.getPlayers()) {
                for (Coord surroundingTile : positions) {
                    Coord currentPos = player.getCurrentPosition();
                    if (currentPos.equals(surroundingTile)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Sets all tiles on the game board to be selectable. Therefore allowing
     * a player to place a action tile.
     * @param action The action tile to be played.
     */
    private void playActionToBoard(ActionTile action) {
        for (Node node : gameBoardPane.getChildren()) {
            Coord position = new Coord(GridPane.getRowIndex(node),
                    GridPane.getColumnIndex(node));
            //Selects surrounding tiles for a 3x3
            int xPos = position.getX();
            int yPos = position.getY();
            Coord[] positions = new Coord[] {
                    new Coord(xPos - 1, yPos + 1),
                    new Coord(xPos, yPos + 1),
                    new Coord(xPos + 1, yPos + 1),
                    new Coord(xPos - 1, yPos),
                    position,
                    new Coord(xPos + 1, yPos),
                    new Coord(xPos - 1, yPos - 1),
                    new Coord(xPos, yPos - 1),
                    new Coord(xPos + 1, yPos - 1)
            };

            boolean validPlacement = isPlacementValid(action, positions);
            if ((gameBoard.getAction(position) == null) && (validPlacement)) {
                allowSelect(positions, node, action);
            }
        }
    }

    /**
     * Draws action tiles that are currently placed the board, to the screen.
     */
    private void drawActions() {
        for (Node node : gameBoardPane.getChildren()) {
            Coord position = new Coord(GridPane.getRowIndex(node),
                    GridPane.getColumnIndex(node));
            ActionTile currentAction = gameBoard.getAction(position);

            if (currentAction instanceof FireTile) {
                StackPane action = (StackPane) node;
                ImageView fire = new ImageView(RESOURCES_PATH + "FireTile.png");
                fire.setFitWidth(TILE_SIZE);
                fire.setFitHeight(TILE_SIZE);
                fire.setRotate(new Random().nextInt(MAX_ROTATE));
                action.getChildren().add(fire);
            } else if (currentAction instanceof IceTile) {
                StackPane action = (StackPane) node;
                ImageView ice = new ImageView(RESOURCES_PATH + "IceTile.png");
                ice.setFitWidth(TILE_SIZE);
                ice.setFitHeight(TILE_SIZE);
                action.getChildren().add(ice);
            }
        }
    }

    /**
     * Moves a player to their location two turns ago. If that tile is on fire
     * then they are backtracked to 1 turn ago.
     * @param player the player to be backtracked to an earlier position.
     */
    private void backtrackPlayer(Player player) {
        Coord pastPosition1 = player.getPrevPosition(0);
        Coord pastPosition2 = player.getPrevPosition(1);
        for (Player currPlayer : game.getPlayers()) {
            Coord currPosition = currPlayer.getCurrentPosition();
            if (currPosition == pastPosition1) {
                pastPosition1.setPos(-1, -1);
            } else if (currPosition == pastPosition2) {
                pastPosition2.setPos(-1, -1);
            }
        }

        ActionTile targetTile = gameBoard.getAction(pastPosition1);
        if (!pastPosition1.isEmpty() && !(targetTile instanceof FireTile)) {
            player.movePlayer(pastPosition1);
            player.setCanBackTrack(false);
        } else {
            targetTile = gameBoard.getAction(pastPosition2);
            if (pastPosition2.isEmpty() && !(targetTile instanceof FireTile)) {
                player.movePlayer(pastPosition2);
                player.setCanBackTrack(false);
            }
        }
        continueButton.setDisable(false);
        updateGameBoard();
        drawPlayers();
    }

    /**
     * Retrieves the correct sprite for a given tile.
     * @param tile The FloorTile object to fetch a sprite for.
     * @return an ImageView object with the correct sprite rendering for
     * the given tile, with correct on click behaviour.
     */
    private ImageView getTileImage(FloorTile tile) {
        ImageView tileImage = new ImageView(RESOURCES_PATH + tile.isFixed()
                + tile.getShape() + ".png");
        tileImage.fitHeightProperty().bind(tileSize);
        tileImage.fitWidthProperty().bind(tileSize);
        tileImage.setRotate(getRotationValue(tile.getOrientation()));
        return tileImage;
    }

    /**
     * Retrieves the correct arrow 'button' for a specified side and row of
     * the game board.
     * @param direction The direction that tiles should be inserted into to
     *                  board.
     * @param index The row or column index of the arrow on the grid.
     * @param orientation The orientation that the arrow should be facing.
     * @return An ImageView object of the arrow image, with correct on click
     * behaviour.
     */
    private ImageView getArrow(String direction, int index, int orientation) {
        ImageView arrow = new ImageView(RESOURCES_PATH + "left.png");
        arrow.setFitWidth(TILE_SIZE);
        arrow.setFitHeight(TILE_SIZE);
        arrow.setRotate(getRotationValue(orientation));
        arrow.setOnMouseClicked(event -> {
            Tile fallenOff = gameBoard.insertTile(
                    (FloorTile) game.getCurrentTile(), direction, index);
            game.updatePlayerPositions(direction, index);
            updateGameBoard();
            updateArrows(false);
            drawPlayers();
            game.getTileBag().addTile(fallenOff);
            continueButton.setDisable(false);
            selectedTile.setImage(null);
            new AudioPlayer().clickPlay();
            if ((game.getCurrentPlayer().getActionTiles()).size() > 0) {
                actionButton.setDisable(false);
            }
        });
        return arrow;
    }

    /**
     * Gets the value to rotate the object by, depending at which orientation
     * the object is currently set to.
     * @param orientation The integer value representing the orientation of a
     *                    tile.
     * @return The rotation value of the tile (in degrees)
     * (possible values: 0, 90, 180, 270).
     */
    private double getRotationValue(int orientation) {
        switch (orientation) {
            case LEFT_ORIENTATION :
                return QUARTER_TURN;
            case DOWN_ORIENTATION :
                return HALF_TURN;
            case RIGHT_ORIENTATION :
                return THIRD_TURN;
            default:
                return NO_TURN;
        }
    }

    /**
     * Sets the tank sprite of current player to rotate, following the mouse.
     * @param event The event that triggered this method.
     */
    private void setTankSpin(MouseEvent event) {
        Coord currentPosition = game.getCurrentPlayer().getCurrentPosition();
        ImageView playerTank = getPlayer(getTilePane(currentPosition.getX(),
                currentPosition.getY()));
        Point2D tankPosition = playerTank.localToScene(playerTank
                .getFitHeight() / 2, playerTank.getFitWidth() / 2);
        int diffY = (int) (event.getY() - tankPosition.getY());
        int diffX = (int) (event.getX() - tankPosition.getX());
        float rotationValue = ((float) Math.toDegrees(Math
                .atan2(diffY, diffX)) % MAX_ROTATE) - QUARTER_TURN;
        playerTank.setRotate(rotationValue);
    }

    /**
     * For each player, their newly updated stats from this game are saved
     * to their respective record.
     */
    private void handleWinSaves() {
        for (Player player : game.getPlayers()) {
            try {
                boolean isPlayer = player.getProfileName()
                        .equals(game.getCurrentPlayerName());

                if (isPlayer) {
                    player.getProfile().incrementWins();
                } else {
                    player.getProfile().incrementLosses();
                }

                player.getProfile().incrementGamesPlayed();
                player.getProfile().save();
                FileHandler.saveLeaderboard(
                        game.getLevelName(), player.getProfileName());
            } catch (IOException e) {
                Alert leaderboardError = new Alert(Alert.AlertType.ERROR,
                        "An error was encountered while attempting to update"
                                + "the leaderboards. Any updates to the "
                                + "leaderboard status have not been saved.",
                        ButtonType.OK);
                leaderboardError.showAndWait();
            }
        }
    }

    /**
     * Removes arrow buttons from around the game board.
     */
    private void clearArrows() {
        leftButtons.getChildren().clear();
        leftButtons.getRowConstraints().clear();
        rightButtons.getChildren().clear();
        rightButtons.getRowConstraints().clear();
        topButtons.getChildren().clear();
        topButtons.getColumnConstraints().clear();
        bottomButtons.getColumnConstraints().clear();
        bottomButtons.getChildren().clear();
    }

    /**
     * Sets the game up for the next round of the game.
     */
    private void nextRound() {
        selectedActionTile = null;
        window.setOnMouseMoved(this::setTankSpin);
        gameBoard.refreshActionBoard(game.getNumPlayers());
        if (game.checkWin(game.getCurrentPlayer())) {
            setPlayerLabel(game.getCurrentPlayerName() + " wins!");
            game.setOver(true);
            handleWinSaves();
        } else {
            game.nextPlayer();
            selectedTile.setImage(null);
            updateActionTileHand();
            this.setPlayerLabel(game.getCurrentPlayerName() + "'s turn!");

            if (!gameBoard.isBoardFixed()) {
                drawTile.setDisable(false);
                this.updateArrows(false);
                continueButton.setDisable(true);
            } else {
                if ((game.getCurrentPlayer().getActionTiles()).size() > 0) {
                    actionButton.setDisable(false);
                }
                continueButton.setDisable(false);
            }
            updateGameBoard();
            drawPlayers();
        }
    }

    /**
     * Handles a click of the menu button, therefore showing and hiding
     * the menu.
     */
    private void handleMenuClick() {
        menu.setVisible(!menu.isVisible());
        playerConsole.setVisible(!menu.isVisible());
        new AudioPlayer().clickPlay();
    }

    /**
     * Produces a random tile from the silk bag of tiles, and displays on
     * screen.
     */
    private void handleDrawTile() {
        if (game.getCurrentTile() == null) {
            game.setCurrentTile(game.getTileBag().drawTile());
            if (game.getCurrentTile() instanceof FloorTile) {
                updateArrows(true);
                selectedTile.setImage((getTileImage(
                        (FloorTile) game.getCurrentTile())).getImage());
                selectedTile.setRotate(getRotationValue(
                        ((FloorTile) game.getCurrentTile()).getOrientation()));
            } else {
                selectedTile.setImage(new Image(RESOURCES_PATH
                        + game.getCurrentTile()
                        .getClass().getName().substring(PACKAGE_LENGTH)
                        + ".png"));
                selectedTile.setRotate(NO_TURN);
                if ((game.getCurrentPlayer().getActionTiles()).size() > 0) {
                    actionButton.setDisable(false);
                }
            }
            drawTile.setDisable(true);
            if (!(game.getCurrentTile() instanceof FloorTile)) {
                continueButton.setDisable(false);
            }
        }
        new AudioPlayer().clickPlay();
    }

    /**
     * Decrements the players current tile, rotating the tile
     * anti-clockwise on screen.
     */
    private void handleOrientationDecrement() {
        boolean isFloorTile = game.getCurrentTile() != null
                && game.getCurrentTile() instanceof FloorTile;
        if (isFloorTile) {
            ((FloorTile) game.getCurrentTile()).decrementOrientation();
            selectedTile.setRotate(getRotationValue(
                    ((FloorTile) game.getCurrentTile()).getOrientation()));
        }
        new AudioPlayer().clickPlay();
    }

    /**
     * Increments the players current tile, rotating the tile
     * clockwise on screen.
     */
    private void handleOrientationIncrement() {
        boolean isFloorTile = game.getCurrentTile() != null
                && game.getCurrentTile() instanceof FloorTile;
        if (isFloorTile) {
            ((FloorTile) game.getCurrentTile()).incrementOrientation();
            selectedTile.setRotate(getRotationValue(
                    ((FloorTile) game.getCurrentTile()).getOrientation()));
        }
        new AudioPlayer().clickPlay();
    }

    /**
     * Handles the continue button click, progresses the game
     * onto player movement.
     */
    private void handleContinue() {
        if (drawTile.isDisabled()) {
            boolean isActionTile = false;
            if (game.getCurrentTile() != null) {
                isActionTile = ActionTile.class
                        .isAssignableFrom(game
                                .getCurrentTile().getClass());
            }

            if (isActionTile) {
                game.getCurrentPlayer().addActionTile(
                        (ActionTile) game.getCurrentTile());
            }

            ArrayList<Coord> validMoves
                    = gameBoard.getValidMoves(game.getCurrentPlayer());

            if (availableMoves(validMoves).size() == 0) {
                nextRound();
            } else {
                this.continueButton.setDisable(true);
                updateMoves(validMoves);
            }
        }
        new AudioPlayer().clickPlay();
        actionButton.setDisable(true);
        selectedTile.setImage(null);
    }

    /**
     * Handles saving a game to file and the name that the file should be
     * saved as. The player can either enter a string or use the default
     * timestamp.
     */
    private void handleSaveClick() {
        if (!game.isOver()) {
            // Remove timestamp chars
            TextInputDialog input = new TextInputDialog(Instant.now().toString()
                    .replaceAll("[\\\\/:*?\"\"<>|]", ""));
            input.showAndWait();
            String result = input.getResult().trim()
                    .replaceAll("[\\\\/:*?\"\"<>|]", "");
            if (result.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Cannot be empty", ButtonType.CLOSE);
                alert.showAndWait();
            } else {
                saveGame(result);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Game is over.",
                    ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    /**
     * Saves the games current state to file.
     * @param fileName The name to save the game under.
     */
    private void saveGame(String fileName) {
        try {
            FileHandler.saveGameFile(fileName, game);
            new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
        } catch (IOException exception) {
            exception.printStackTrace();
            Alert saveError = new Alert(Alert.AlertType.ERROR,
                    "An error was encountered while attempting to save the "
                            + "game. The game state has not been saved.",
                    ButtonType.OK, ButtonType.CANCEL);
            saveError.showAndWait();
            if (saveError.getResult() == ButtonType.OK) {
                new MenuScene(primaryStage, backgroundMusic.getMediaPlayer());
            }
        }
    }

    /**
     * Plays the currently selected action tile.
     */
    private void handleActionClick() {
        if (selectedActionTile instanceof DoubleMoveTile) {
            updateMoves(gameBoard.getValidMoves(game.getCurrentPlayer()));
            actionButton.setDisable(true);
            selectedTile.setImage(null);
            game.getCurrentPlayer().removeActionTile(selectedActionTile);
            selectedActionTile = null;
            updateActionTileHand();
        } else if (selectedActionTile instanceof BackTrackTile) {
            boolean canBacktrack = false;
            for (Player player : game.getPlayers()) {
                if (player.canBackTrack()) {
                    canBacktrack = true;
                }
            }
            if (canBacktrack) {
                selectBacktrack();
                actionButton.setDisable(true);
                continueButton.setDisable(true);
                selectedTile.setImage(null);
                game.getCurrentPlayer().removeActionTile(selectedActionTile);
                selectedActionTile = null;
                updateActionTileHand();
            }
        } else if (selectedActionTile != null) {
            playActionToBoard(selectedActionTile);
            actionButton.setDisable(true);
            continueButton.setDisable(true);
            selectedTile.setImage(null);
            game.getCurrentPlayer().removeActionTile(selectedActionTile);
            selectedActionTile = null;
            updateActionTileHand();
        }
    }

}
