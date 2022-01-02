import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MinesweeperController {

    @FXML
    GridPane gridPane;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    Image flagImage = new Image("flag.svg.png");
    private final ImageView flag = new ImageView(flagImage);
    Image mineImage = new Image("mine.png");
    private  final ImageView mine = new ImageView(mineImage);
    Image blankImage = new Image("blank.svg.png");
    private final ImageView blank = new ImageView(blankImage);
    Image oneImage = new Image("1.svg.png");
    private final ImageView one = new ImageView(oneImage);
    Image twoImage = new Image("2.svg.png");
    private final ImageView two = new ImageView(twoImage);
    Image threeImage = new Image("3.svg.png");
    private final ImageView three = new ImageView(threeImage);
    Image fourImage = new Image("4.svg.png");
    private final ImageView four = new ImageView(fourImage);
    Image fiveImage = new Image("5.svg.png");
    private final ImageView five = new ImageView(fiveImage);
    Image sixImage = new Image("6.svg.png");
    private final ImageView six = new ImageView(sixImage);
    Image sevenImage = new Image("7.svg.png");
    private final ImageView seven = new ImageView(sevenImage);
    Image eightImage = new Image("8.svg.png");
    private final ImageView eight = new ImageView(eightImage);

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
                board[x][y].setMinSize(24, 20);
                board[x][y].setMaxSize(24,20);
                gridPane.add(board[x][y],x, y);
                //add on click to each button
                addOnclick(board[x][y]);
                //set button image sizes

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

    private void addOnclick(MinesweeperCell minesweeperCell) {
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
            setImageSize(flag);
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

    private ArrayList<MinesweeperCell> getAdjacentCells (MinesweeperCell cell){
        int x = cell.x;
        int y = cell.y;
        ArrayList<MinesweeperCell> adjacentCells = new ArrayList<>();
        //fix labels, y - axis in descending order
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
        if(isOnGrid(x+1,y-1)){
            adjacentCells.add(board[x+1][y-1]);
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
                setImageSize(mine);
                cell.setGraphic(mine);
                break;
            case 0:
                setImageSize(blank);
                cell.setGraphic(blank);
                break;
            case 1:
                setImageSize(one);
                cell.setGraphic(one);
                break;
            case 2:
                setImageSize(two);
                cell.setGraphic(two);
                break;
            case 3:
                setImageSize(three);
                cell.setGraphic(three);
                break;
            case 4:
                setImageSize(four);
                cell.setGraphic(four);
                break;
            case 5:
                setImageSize(five);
                cell.setGraphic(five);
                break;
            case 6:
                setImageSize(six);
                cell.setGraphic(six);
                break;
            case 7:
                setImageSize(seven);
                cell.setGraphic(seven);
                break;
            case 8:
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

    private void setImageSize(ImageView image){
        image.setFitHeight(20);
        image.setPreserveRatio(true);
    }
}