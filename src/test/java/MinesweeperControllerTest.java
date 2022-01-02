import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
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
        //MinesweeperCell sampleCell = new MinesweeperCell(1,0);
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

    private void givenMinesweeperController() {
        controller = new MinesweeperController();
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
