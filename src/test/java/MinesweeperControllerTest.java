import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MinesweeperControllerTest {
    private MinesweeperController controller;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    private GridPane gridPane;

    @Test //passes, incomplete
    public void constructController(){
        //given
        givenMinesweeperController();

        //when

        //then
        assertNotNull(controller.getBoard());
        //assert placeMines method was called
    }

    @Test //fails, gridPane
    public void initialize(){ //TO DO
        //given
        givenMinesweeperController();

        //when
        controller.initialize();

        //then
        //verify cells placed on gridPane
        //verify addOnClick method called, and works
    }

    @Test //TO DO
    public void addOnClick(){

    }

    @Test //passes, incomplete
    public void flagCell(){
        //given
        givenMinesweeperController();
        MinesweeperCell sampleUnClickedCell = board[1][0]; //(move to given method?)

        //when
        controller.flagCell(sampleUnClickedCell);

        //then
        assertTrue(sampleUnClickedCell.wasClicked);
        assertNotNull(sampleUnClickedCell.getGraphic());
        //check that image was set to the flag specifically



    }

    @Test //fails, cell is null, incomplete
    public void unFlagCell(){
        //given
        MinesweeperCell sampleClickedCell = board[1][0]; //(move to given method?)
        sampleClickedCell.wasClicked = true; // why is cell null?

        //when
        controller.flagCell(sampleClickedCell);

        //then
        assertFalse(sampleClickedCell.wasClicked);
        assertNull(sampleClickedCell.getGraphic());



    }

    @Test //fails,incomplete
    public void playMoveMine (){
        //given
        givenMinesweeperController();
        MinesweeperCell clickedCell = board[2][1];
        clickedCell.value = MinesweeperCell.MINE;

        //when
        controller.playMove(clickedCell);

        //then
        //this is only with mocks though?
        verify(controller).gameOver();
        //verify gameOver method called
    }

    @Test //passes (tests isOnGrid automatically)
    public void getAdjacentCells(){
        //given
        givenMinesweeperController();

        ArrayList<MinesweeperCell> expectedAdjacentCells = new ArrayList<>();
        MinesweeperCell sampleCell = board[1][0];


        //top left off grid
        expectedAdjacentCells.add(new MinesweeperCell(0,0)); //left
        expectedAdjacentCells.add(new MinesweeperCell(0,1)); //bottom left
        //top right off grid
        expectedAdjacentCells.add(new MinesweeperCell(1,1)); //bottom middle;
        expectedAdjacentCells.add(new MinesweeperCell(2,0)); //right
        expectedAdjacentCells.add(new MinesweeperCell(2,1)); //bottom right


        //when
        ArrayList<MinesweeperCell> adjacentCells = controller.getAdjacentCells(sampleCell);

        //then
        assertEquals(adjacentCells,expectedAdjacentCells);
    }

    @Test // TO DO (how to test recursion?)
    public void checkAdjacentCells(){
        //given
        givenMinesweeperController();
        MinesweeperCell clickedCell = board [1][3];

        //when
        controller.checkAdjacentCells(clickedCell);

        //then

    }

    @Test //TO DO (will test setImageSize() method)
    public void revealCell (){

    }

    @Test // TO DO ( will test revealAll() method )
    public void gameOver(){

    }

    private void givenMinesweeperController() {
        controller = new MinesweeperController();
        //controller = mock(MinesweeperController.class);
        // should this code go here?
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                //board[x][y] = mock(MinesweeperCell.class);
                board[x][y] = new MinesweeperCell(x,y);
            }
        }
       //test placeMines method?
        gridPane = mock(GridPane.class);
    }
}
