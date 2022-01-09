
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;

public class MinesweeperController implements EventHandler<MouseEvent> {
    @FXML
    public GridPane gridPane;
    private boolean firstMove = true;
    private final int boardSize = 20;
    public MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    public final Image flagImage = new Image("flag.svg.png");
    public final Image mineImage = new Image("mine.png");
    public final Image blankImage = new Image("blank.svg.png");
    public final Image oneImage = new Image("1.svg.png");
    public final Image twoImage = new Image("2.svg.png");
    public final Image threeImage = new Image("3.svg.png");
    public final Image fourImage = new Image("4.svg.png");
    public final Image fiveImage = new Image("5.svg.png");
    public final Image sixImage = new Image("6.svg.png");
    public final Image sevenImage = new Image("7.svg.png");
    public final Image eightImage = new Image("8.svg.png");

    public MinesweeperController(){
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new MinesweeperCell(x,y);
            }
        }
    }

    public MinesweeperCell[][] getBoard(){
        return this.board;
    }

    public void initialize (){
        //loop through the 2d array of buttons and place them on the board
        for (int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                board[x][y].setMinSize(24, 20);
                board[x][y].setMaxSize(24,20);
                gridPane.add(board[x][y],x, y);
                board[x][y].setOnMouseClicked(this);
                //set button image sizes
            }
        }
    }

    public void placeMines(MinesweeperCell clickedCell) {
        int nrMines = 50;
        Random randomInt = new Random();
        int chosenX = clickedCell.x;
        int chosenY = clickedCell.y;
        int x, y;
        while (nrMines > 0) {
            x = randomInt.nextInt(boardSize);
            y = randomInt.nextInt(boardSize);
            MinesweeperCell randomCell = board[x][y];
            if (randomCell.x != chosenX && randomCell.y != chosenY //don't place mine in clickedCell
                    && randomCell.value != MinesweeperCell.MINE) {
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

    public void flagCell(MinesweeperCell clickedCell) {
        if (!isFlagged(clickedCell)){ //!clickedCell.wasClicked
            clickedCell.wasClicked = true; //still needed?
            ImageView flag = new ImageView(flagImage);
            setImageSize(flag);
            clickedCell.setGraphic(flag);
        }
        else {
            clickedCell.wasClicked = false; //un-click cell , still needed?
            clickedCell.setGraphic(null); //remove flag
        }
    }

    public void playMove(MinesweeperCell clickedCell) {
        if(firstMove){
            placeMines(clickedCell);
            firstMove = false;
        }
        int cellValue = clickedCell.value;
        if (cellValue == MinesweeperCell.MINE) {
            gameOver();
        } else {
            revealCell(clickedCell);
            checkAdjacentCells(clickedCell);
        }
    }

    private boolean isOnGrid (int x, int y){
        return x >= 0 && x < boardSize && y >= 0 && y < boardSize;
    }

    public ArrayList<MinesweeperCell> getAdjacentCells (MinesweeperCell cell){
        int x = cell.x;
        int y = cell.y;
        ArrayList<MinesweeperCell> adjacentCells = new ArrayList<>();
        for(int ix = x-1; ix <= x+1; ix++){
            for(int iy = y-1; iy <= y+1; iy++){
                if(ix != x && iy != y && isOnGrid(ix,iy)){ //don't add cell with original x,y
                    adjacentCells.add(board[ix][iy]);
                }
            }
        }
        return adjacentCells;
    }

    private void checkAdjacentCells(MinesweeperCell cell){
        ArrayList<MinesweeperCell> adjacentCells = getAdjacentCells(cell);
        for (MinesweeperCell adjacentCell : adjacentCells){
            //if adjacent cell was not checked yet
            // (if a cell was checked, it has been disabled.)
            //if it's a mine, don't reveal, nor check adjacent
            if(!adjacentCell.isDisabled() && adjacentCell.value != MinesweeperCell.MINE){
                if (adjacentCell.value == 0){
                    revealCell(adjacentCell);
                    checkAdjacentCells(adjacentCell);
                }
                else if (adjacentCell.value > 0) {
                    revealCell(adjacentCell);
                }
            }
        }
    }

    public void revealCell(MinesweeperCell cell) {
        cell.wasClicked = true;
        cell.setDisable(true);
        switch (cell.value) {
            case MinesweeperCell.MINE:
                ImageView mine = new ImageView(mineImage);
                setImageSize(mine);
                cell.setGraphic(mine);
                break;
            case 0:
                ImageView blank = new ImageView(blankImage);
                setImageSize(blank);
                cell.setGraphic(blank);

                break;
            case 1:
                ImageView one = new ImageView(oneImage);
                setImageSize(one);
                cell.setGraphic(one);
                break;
            case 2:
                ImageView two = new ImageView(twoImage);
                setImageSize(two);
                cell.setGraphic(two);
                break;
            case 3:
                ImageView three = new ImageView(threeImage);
                setImageSize(three);
                cell.setGraphic(three);
                break;
            case 4:
                ImageView four = new ImageView(fourImage);
                setImageSize(four);
                cell.setGraphic(four);
                break;
            case 5:
                ImageView five = new ImageView(fiveImage);
                setImageSize(five);
                cell.setGraphic(five);
                break;
            case 6:
                ImageView six = new ImageView(sixImage);
                setImageSize(six);
                cell.setGraphic(six);
                break;
            case 7:
                ImageView seven = new ImageView(sevenImage);
                setImageSize(seven);
                cell.setGraphic(seven);
                break;
            case 8:
                ImageView eight = new ImageView(eightImage);
                setImageSize(eight);
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

    public void gameOver() {
        revealAll();
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

    private void setImageSize(ImageView image){
        image.setFitHeight(20);
        image.setPreserveRatio(true);
    }

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

    public void playBestStrategy(){
        for (int x = 0; x < boardSize; x++){
            for (int y = 0; y < boardSize; y++){
                //only relevant if current cell is uncovered
                MinesweeperCell currentCell = board[x][y];
                if (isRevealed(currentCell)) {
                    ArrayList<MinesweeperCell> adjacentCells = getAdjacentCells(currentCell);
                    flagMines(currentCell, adjacentCells);
                    clickAdjSafeCells(currentCell,adjacentCells);
                }
                //TO DO: add logic for if get stuck -> choose randomly
                // (but not within loop, if no action for this cell, go through board again etc.
                //only if went through full board and no other option)
            }
        }
    }

    private void clickAdjSafeCells(MinesweeperCell currentCell, ArrayList<MinesweeperCell> adjacentCells) {
        // if cell's conditions are satisfied,(nrAdjFlagged cells = value)
        // click every adjacent unRevealed and unFlagged cell
        int nrAdjFlaggedCells = 0;
        for (MinesweeperCell adjacentCell : adjacentCells) {
            if (isFlagged(adjacentCell)) {
                nrAdjFlaggedCells++;
            }
        }
        if (nrAdjFlaggedCells == currentCell.value){
            for (MinesweeperCell adjacentCell : adjacentCells) {
                if (!isFlagged(adjacentCell) && !isRevealed(adjacentCell)){
                    playMove(adjacentCell);
                }
            }
        }
    }

    private void flagMines(MinesweeperCell currentCell, ArrayList<MinesweeperCell> adjacentCells) {
        // if cell's nr of available (aka not revealed) adjacent cells == cell value,
        // place flags in all those cells
        ArrayList<MinesweeperCell> coveredAdjCells = new ArrayList<>();
        for (MinesweeperCell adjacentCell : adjacentCells) {
            if (!isRevealed(adjacentCell)) {
                coveredAdjCells.add(adjacentCell);
            }
        }
        int nrCoveredAdjacentCells = coveredAdjCells.size();
        if (nrCoveredAdjacentCells > 0 && nrCoveredAdjacentCells == currentCell.value) {
            for (MinesweeperCell cell : coveredAdjCells) {
                if (!isFlagged(cell)) {
                    flagCell(cell);
                }
            }
        }

    }

    private boolean isFlagged(MinesweeperCell cell){
        Image image = ((ImageView) cell.getGraphic()).getImage();
        return image == flagImage;

    }
    private boolean isRevealed(MinesweeperCell cell){
        Image image = ((ImageView) cell.getGraphic()).getImage();
        return image != blankImage;
    }
}