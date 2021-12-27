import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;

public class MinesweeperController {

    @FXML
    GridPane gridPane;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];

    public MinesweeperController(){
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x,y);
            }
        }
        placeMines();


    }

    public void initialize (){
        // add buttons to gridpane
        // gridPane.add
        //loop through the 2d array of buttons, and place them on the board
    }

    //add method; on right click, place flag, make cell unclickable

    //add method; on regular click, call playMove function

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
        if ( x < minVal || x > maxVal || y < minVal ||  y > maxVal){
            return false;
        }
        return true;
    }
    public void playMove(MinesweeperCell clickedCell) {
        int cellValue = clickedCell.value;
        if (cellValue == -1) { // mine
            //reveal all cells
            //game over
        } else {
            //reveal cell
            clickedCell.wasClicked = true; // make unclickable
            checkAdjacentCells(clickedCell);
        }
    }

    public void checkAdjacentCells( MinesweeperCell cell){
        ArrayList<MinesweeperCell> adjacentCells = getAdjacentCells(cell);
        for (MinesweeperCell adjacentCell : adjacentCells){
            if (cell.value == 0){
                //uncover cell
                cell.wasClicked = true; //make unclickable
                checkAdjacentCells(adjacentCell);
            }
            if (cell.value > 0) {
                //uncover cell
                cell.wasClicked = true; //make unclickable
            }
        }
    }
}

