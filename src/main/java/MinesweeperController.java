import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private final ImageView flag = new ImageView(Objects.requireNonNull(getClass().getResource("flag.png")).toExternalForm());
    private final ImageView bomb = new ImageView(Objects.requireNonNull(getClass().getResource("bomb.png")).toExternalForm());

    public MinesweeperController(){
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x,y);
            }
        }
        placeMines();
    }

    public void initialize (){
        //loop through the 2d array of buttons, and place them on the board
        for (int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x, y);
                board[x][y].setPrefSize(27, 22);
                gridPane.add(board[x][y],x, y);
                //add on click to each button
                onclick(board[x][y]);
            }
        }
    }

    private void onclick(MinesweeperCell minesweeperCell) {
        minesweeperCell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MinesweeperCell btn = (MinesweeperCell) mouseEvent.getSource();
                //right click - set flag image to button, disable click
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    btn.setGraphic(flag);
                    btn.setDisable(true);
                }else{ //left click call playMove();
                    playMove(btn);
                }
            }
        });
    }

    private void placeMines() {
        int nrMines = 50;
        Random randomInt = new Random();
        int x, y;
        while (nrMines > 0) {
            x = randomInt.nextInt(boardSize);
            y = randomInt.nextInt(boardSize);
            MinesweeperCell randomCell = board[x][y];
            if(randomCell.value != MinesweeperCell.MINE) {
                randomCell.value = MinesweeperCell.MINE;
                //increment adjacent cells
                ArrayList<MinesweeperCell> adjacentCells  = getAdjacentCells(randomCell);
                for (MinesweeperCell adjacentCell : adjacentCells){
                    //if adjacent is a mine, then don't increment
                    if (adjacentCell.value != MinesweeperCell.MINE){
                        adjacentCell.value++;
                    }
                }
                //decrement mines left to place
                nrMines--;
            }
        }
    }

    public ArrayList<MinesweeperCell> getAdjacentCells (MinesweeperCell cell){
        int x = cell.x;
        int y = cell.y;
        ArrayList<MinesweeperCell> adjacentCells = new ArrayList<>();

        // top left
        if (isOnGrid(x-1,y+1)){
            adjacentCells.add(board[x - 1][y + 1]);
        }
        //top middle
        if (isOnGrid(x,y+1)){
            adjacentCells.add(board[x][y + 1]);
        }
        //top right
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
        //bottom left
        if(isOnGrid(x+1,y)){
            adjacentCells.add(board[x-1][y]);
        }
        //bottom middle
        if(isOnGrid(x,y-1)){
            adjacentCells.add(board[x][y-1]);
        }
        //bottom right
        if(isOnGrid(x + 1,y-1)){
            adjacentCells.add(board[x][y-1]);
        }
        return adjacentCells;
    }

    public boolean isOnGrid (int x, int y){
        int minVal = 0;
        int maxVal = boardSize - 1;
        return x >= minVal && x <= maxVal && y >= minVal && y <= maxVal;
    }
    public void playMove(MinesweeperCell clickedCell) {
        int cellValue = clickedCell.value;
        if (cellValue == -1) {
            // mine
            clickedCell.setGraphic(bomb);
            //reveal all cells
            //game over
        } else {
            //reveal cell
            clickedCell.wasClicked = true;
            // make unclickable
            clickedCell.setDisable(true);
            checkAdjacentCells(clickedCell);
        }
    }

    public void checkAdjacentCells( MinesweeperCell cell){
        ArrayList<MinesweeperCell> adjacentCells = getAdjacentCells(cell);
        for (MinesweeperCell adjacentCell : adjacentCells){
            if (cell.value == 0){
                //uncover cell
                cell.wasClicked = true;
                //make unclickable
                cell.setDisable(true);
                checkAdjacentCells(adjacentCell);
            }
            if (cell.value > 0) {
                //uncover cell
                cell.wasClicked = true;
                //make unclickable
                cell.setDisable(true);
            }
        }
    }
}

