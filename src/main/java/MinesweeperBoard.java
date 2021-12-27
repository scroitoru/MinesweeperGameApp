import java.util.Random;

public class MinesweeperBoard {

    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    //private Cell[][] board = new Cell[boardSize][boardSize];
/*
    // pass board size to constructor?
    public MinesweeperBoard() {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                board[x][y] = new Cell(x, y);
            }
        }
        placeMines();
    }
*/
    public MinesweeperBoard(){
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x,y);
                //shouldn't be instantiating minesweeper cells here?
                // instead, should access the ones on the gui board?
            }
        }
        placeMines();

    }
    private void placeMines() {
        int nrMines = 50;
        Random randomInt = new Random();
        int x, y;
        while (nrMines > 0) {
            x = randomInt.nextInt(boardSize);
            y = randomInt.nextInt(boardSize);
            MinesweeperCell randomCell = board[x][y];
            if (randomCell.value != -1) {
                randomCell.value = -1; // place mine (-1 = mine)
                //increment adjacent cells
                MinesweeperCell [] adjacentCells  = getAdjacentCells(randomCell);
                for (MinesweeperCell adjacentCell : adjacentCells){
                    //if adjacent is a mine, then don't increment
                    if ( adjacentCell.value != -1){
                        adjacentCell.value++;
                    }
                }
                //decrement mines left to place
                nrMines--;
            }
        }
    }

    // is this needed? taken care of by controller? make it not clickable once revealed
    public boolean isMoveLegal(Cell clickedCell) {
        return !clickedCell.wasClicked;
    }

    // right click places flag, right click again removes flag (switch? can add another boolean)
    //regular click calls the following function

    public void playMove(MinesweeperCell clickedCell) {
        int cellValue = clickedCell.value;
        if (cellValue == -1) { // mine
            //reveal all cells
            //game over
        } else {
            //reveal cell
           checkAdjacentCells(clickedCell);
        }
    }

    public MinesweeperCell [] getAdjacentCells (MinesweeperCell cell){
        int x = cell.x;
        int y = cell.y;
        //TO DO: don't add cells with coordinates off the grid
        MinesweeperCell [] adjacentCells = {
                board[x - 1][y + 1],  //top left
                board[x][y + 1], //top middle
                board[x + 1][y + 1], // top right
                board[x - 1][y], //left
                board[x + 1][y], //right
                board[x - 1][y - 1], //bottom left
                board[x][y - 1], //bottom middle
                board[x + 1][y - 1] //bottom right
        };
        return adjacentCells;
    }

    public void checkAdjacentCells( MinesweeperCell cell){
        MinesweeperCell [] adjacentCells = getAdjacentCells(cell);
        for (MinesweeperCell adjacentCell : adjacentCells){
            if (cell.value == 0){
                //uncover cell
                checkAdjacentCells(adjacentCell);
            }
            if (cell.value > 0) {
                //uncover cell
            }
        }
    }
}
