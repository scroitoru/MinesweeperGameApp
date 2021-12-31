import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MinesweeperController {
    @FXML
    GridPane gridPane;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    private final ImageView flag = new ImageView(Objects.requireNonNull(getClass().getResource("flag.svg.png")).toExternalForm());
    private final ImageView mine = new ImageView(Objects.requireNonNull(getClass().getResource("mine.png")).toExternalForm());
    private final ImageView blank = new ImageView(Objects.requireNonNull(getClass().getResource("blank.svg.png")).toExternalForm());
    private final ImageView one = new ImageView(Objects.requireNonNull(getClass().getResource("1.svg.png")).toExternalForm());
    private final ImageView two = new ImageView(Objects.requireNonNull(getClass().getResource("2.svg.png")).toExternalForm());
    private final ImageView three = new ImageView(Objects.requireNonNull(getClass().getResource("3.svg.png")).toExternalForm());
    private final ImageView four = new ImageView(Objects.requireNonNull(getClass().getResource("4.svg.png")).toExternalForm());
    private final ImageView five = new ImageView(Objects.requireNonNull(getClass().getResource("5.svg.png")).toExternalForm());
    private final ImageView six = new ImageView(Objects.requireNonNull(getClass().getResource("6.svg.png")).toExternalForm());
    private final ImageView seven = new ImageView(Objects.requireNonNull(getClass().getResource("7.svg.png")).toExternalForm());
    private final ImageView eight = new ImageView(Objects.requireNonNull(getClass().getResource("8.svg.png")).toExternalForm());


    public MinesweeperController(){
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x,y);
            }
        }
        placeMines();
    }

    public void initialize (){
        //loop through the 2d array of buttons and place them on the board
        for (int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                board[x][y].setPrefSize(27, 22);
                gridPane.add(board[x][y],x, y);
                //add on click to each button
                addOnClick(board[x][y]);
            }
        }
    }

    private void placeMines() {
        int nrMines = 50;
        Random randomInt = new Random();
        int x, y;
        while (nrMines > 0) {
            x = randomInt.nextInt(boardSize);
            y = randomInt.nextInt(boardSize);
            MinesweeperCell randomCell = board[x][y];
            if (randomCell.value != MinesweeperCell.MINE) {
                randomCell.value = MinesweeperCell.MINE;
                //increment adjacent cells
                ArrayList<MinesweeperCell> adjacentCells  = getAdjacentCells(randomCell);
                for (MinesweeperCell adjacentCell : adjacentCells){
                    //if adjacent is a mine, don't increment
                    if (adjacentCell.value != MinesweeperCell.MINE){
                        adjacentCell.value++;
                    }
                }
                //decrement mines left to place
                nrMines--;
            }
        }
    }

    private void addOnClick(MinesweeperCell minesweeperCell) {
        minesweeperCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MinesweeperCell clickedCell = (MinesweeperCell) mouseEvent.getSource();
                //right click - set flag image to button
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    flagCell(clickedCell);
                }
                else {   //regular click
                    playMove(clickedCell);
                }
            }
        });
    }

    private void flagCell(MinesweeperCell clickedCell) {
        if (!clickedCell.wasClicked){
            clickedCell.wasClicked = true;
            clickedCell.setGraphic(flag);
        }
        else {
            clickedCell.wasClicked = false; //un-click cell
            clickedCell.setGraphic(null); //remove flag
        }
    }

    private void playMove(MinesweeperCell clickedCell) {
        int cellValue = clickedCell.value;
        if (cellValue == MinesweeperCell.MINE) {
            revealAll();
            gameOver();
        } else {
            revealCell(clickedCell);
            checkAdjacentCells(clickedCell);
        }
    }

    private boolean isOnGrid (int x, int y){
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }

    //changed to public for now to test
    public ArrayList<MinesweeperCell> getAdjacentCells (MinesweeperCell cell){
        int x = cell.x;
        int y = cell.y;
        ArrayList<MinesweeperCell> adjacentCells = new ArrayList<>();
        // bottom left
        if (isOnGrid(x-1,y+1)){
            adjacentCells.add(board[x - 1][y + 1]);
        }
        //bottom middle
        if (isOnGrid(x,y+1)){
            adjacentCells.add(board[x][y + 1]);
        }
        //bottom right
        if (isOnGrid(x+1,y+1)){
            adjacentCells.add(board[x + 1][y + 1]);
        }
        //left
        if (isOnGrid(x-1,y)){
            adjacentCells.add(board[x - 1][y]);
        }
        //right
        if(isOnGrid(x+1,y)){
            adjacentCells.add(board[x+1][y]);
        }
        //top left
        if(isOnGrid(x+1,y -1)){
            adjacentCells.add(board[x-1][y-1]);
        }
        //top middle
        if(isOnGrid(x,y-1)){
            adjacentCells.add(board[x][y-1]);
        }
        //top right
        if(isOnGrid(x + 1,y-1)){
            adjacentCells.add(board[x][y-1]);
        }
        return adjacentCells;
    }

    private void checkAdjacentCells( MinesweeperCell cell){
        ArrayList<MinesweeperCell> adjacentCells = getAdjacentCells(cell);
        for (MinesweeperCell adjacentCell : adjacentCells){
            if (adjacentCell.value == 0){
                revealCell(adjacentCell);
                checkAdjacentCells(adjacentCell);
            }
            if (adjacentCell.value > 0) {
                revealCell(adjacentCell);
            }
        }
    }

    private void revealCell(MinesweeperCell cell) {
        cell.wasClicked = true;
        cell.setDisable(true);
        switch (cell.value) {
            case MinesweeperCell.MINE:
                cell.setGraphic(mine);
                break;
            case 0:
                cell.setGraphic(blank);
                break;
            case 1:
                cell.setGraphic(one);
                break;
            case 2:
                cell.setGraphic(two);
                break;
            case 3:
                cell.setGraphic(three);
                break;
            case 4:
                cell.setGraphic(four);
                break;
            case 5:
                cell.setGraphic(five);
                break;
            case 6:
                cell.setGraphic(six);
                break;
            case 7:
                cell.setGraphic(seven);
                break;
            case 8:
                cell.setGraphic(eight);
                break;
            default:
                cell.setGraphic(null);
        }
    }

    private void revealAll() {
        for (MinesweeperCell[]row: board) {
            for (MinesweeperCell cell: row) {
                revealCell(cell);
            }
        }

    }

    private void gameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("GAME OVER!");
        alert.setContentText("GAME OVER");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.exit(1);
            }
        });
    }
}