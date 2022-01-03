import javafx.event.Event;
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

public class MinesweeperController  {
    @FXML
    GridPane gridPane;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    private final Image flagImage = new Image("flag.svg.png");
    private final Image mineImage = new Image("mine.png");
    private final Image blankImage = new Image("blank.svg.png");
    private final Image oneImage = new Image("1.svg.png");
    private final Image twoImage = new Image("2.svg.png");
    private final Image threeImage = new Image("3.svg.png");
    private final Image fourImage = new Image("4.svg.png");
    private final Image fiveImage = new Image("5.svg.png");
    private final Image sixImage = new Image("6.svg.png");
    private final Image sevenImage = new Image("7.svg.png");
    private final Image eightImage = new Image("8.svg.png");


    public MinesweeperCell[][] getBoard(){
        return this.board;
    }

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
                addOnClick(board[x][y]);
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

    public void addOnClick(MinesweeperCell minesweeperCell) {
        //minesweeperCell.setOnMouseClicked(handle(mouseEvent));
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

    public void flagCell(MinesweeperCell clickedCell) {
        if (!clickedCell.wasClicked){
            clickedCell.wasClicked = true;
            ImageView flag = new ImageView(flagImage);
            setImageSize(flag);
            clickedCell.setGraphic(flag);
        }
        else {
            clickedCell.wasClicked = false; //un-click cell
            clickedCell.setGraphic(null); //remove flag
        }
    }

    public void playMove(MinesweeperCell clickedCell) {
        int cellValue = clickedCell.value;
        if (cellValue == MinesweeperCell.MINE) {
            // revealAll(); call from inside gameOver()
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
                if(ix == x && iy == y){
                    //don't add cell with original x,y
                }
                else if(isOnGrid(ix,iy)){
                    adjacentCells.add(board[ix][iy]);
                }
            }
        }

        return adjacentCells;
    }

    public void checkAdjacentCells(MinesweeperCell cell){
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

    /*
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


     */


}