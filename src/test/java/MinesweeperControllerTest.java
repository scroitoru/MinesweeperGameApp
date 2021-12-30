import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import static org.mockito.Mockito.mock;

public class MinesweeperControllerTest {
    private MinesweeperController controller;
    private final int boardSize = 20;
    private MinesweeperCell[][] board = new MinesweeperCell[boardSize][boardSize];
    private GridPane gridPane;


    // how to test constructor? especially since constructor calls placeMines() method
    @Test
    public void initialize(){
        //given
        givenMinesweeperController();

        //when
        controller.initialize();

        //then
        //verify cells placed on gridPane
        //verify addOnClick method called, and works

    }

    @Test
    public void getAdjacentCells(){
        //given
        givenMinesweeperController();
        ArrayList<MinesweeperCell> expectedAdjacentCells = new ArrayList<>();
        MinesweeperCell sampleCell = new MinesweeperCell(1,0);
        expectedAdjacentCells.add(new MinesweeperCell(0,1)); //bottom left
        expectedAdjacentCells.add(new MinesweeperCell(1,1)); //bottom middle;
        expectedAdjacentCells.add(new MinesweeperCell(2,1)); //bottom right
        expectedAdjacentCells.add(new MinesweeperCell(0,0)); //left
        expectedAdjacentCells.add(new MinesweeperCell(2,0)); //right
        // top left off grid
        //top right off grid

        //when
        ArrayList<MinesweeperCell> adjacentCells = controller.getAdjacentCells(sampleCell);

        //then
        //bottom left
        assertEquals(adjacentCells, expectedAdjacentCells);

    }

    private void givenMinesweeperController() {
        controller = new MinesweeperController();
        // should this code go here?
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = mock(MinesweeperCell.class);
            }
        }
       //test placeMines method?
        gridPane = mock(GridPane.class);
    }
}
