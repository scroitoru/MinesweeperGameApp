import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class MinesweeperControllerTest {

    private MinesweeperController controller;
    private final int boardSize = 20;

    @Test
    public void constructController(){
        //given
        givenMinesweeperController();

        //when

        //then
        Assertions.assertNotNull(controller.getBoard());
        assertEquals(0, controller.board[0][0].x);
        assertEquals(0, controller.board[0][0].y);
        assertEquals(19,controller.board[19][19].x);
        assertEquals(19,controller.board[19][19].y);
    }

    @Test //incomplete
    public void initialize(){
        //given
        givenMinesweeperController();
        GridPane gridPane = controller.gridPane;

        //when
        controller.initialize();

        //then

        //TODO
        //verify cells placed on gridPane
//        assertEquals(20, gridPane.getColumnCount());//fails
//        assertEquals(20, gridPane.getRowCount());//fails
        int gridSize = gridPane.getRowConstraints().size(); //why is it null?
        Assertions.assertEquals(20, gridSize);

    }

    @Test
    public void flagCell(){
        //given
        givenMinesweeperController();
        MinesweeperCell sampleUnClickedCell = controller.board[1][0]; //(move to given method?)

        //when
        controller.flagCell(sampleUnClickedCell);

        //then
        assertTrue(sampleUnClickedCell.wasClicked);
        ImageView node = (ImageView) sampleUnClickedCell.getGraphic();
        Image image = node.getImage();
        assertNotNull(image);
        //check that image was set to the flag specifically
        assertEquals(image, controller.flagImage);
    }

    @Test
    public void unFlagCell(){
        //given
        givenMinesweeperController();
        MinesweeperCell sampleClickedCell = controller.board[1][0]; //(move to given method?)
        sampleClickedCell.wasClicked = true;

        //when
        controller.flagCell(sampleClickedCell);

        //then
        assertFalse(sampleClickedCell.wasClicked);
        assertNull(sampleClickedCell.getGraphic());
    }

    @Test
    public void placeMines(){
        //given
        givenMinesweeperController();
        MinesweeperCell clickedCell = controller.board[5][5];

        //when
        controller.placeMines(clickedCell);

        //then
        int nrMinesPlaced = 0;
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                if(controller.board[x][y].value == MinesweeperCell.MINE){
                    nrMinesPlaced++;
                }
            }
        }
        assertEquals(50,nrMinesPlaced);
        assertNotEquals(MinesweeperCell.MINE, controller.board[5][5].value);


    }
    @Test
    public void getAdjacentCells(){
        //given
        givenMinesweeperController();

        ArrayList<MinesweeperCell> expectedAdjacentCells = new ArrayList<>();
        MinesweeperCell sampleCell = controller.board[1][0];


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


    @Test
    public void revealCell (){
        //given
        givenMinesweeperController();
        MinesweeperCell cell = controller.board[1][0];
        cell.wasClicked = true;
        cell.setDisable(true);
        cell.value = 2;

        //when
        controller.revealCell(cell);

        //then
        ImageView node = (ImageView) cell.getGraphic();
        Image image = node.getImage();
        assertNotNull(image);
        //check that image was set to the twoImage since value = 2
        assertEquals(image, controller.twoImage);

    }

    private void givenMinesweeperController() {
        controller = new MinesweeperController();
        for (int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                controller.board[x][y] = new MinesweeperCell(x,y);
            }
        }
        controller.gridPane = mock(GridPane.class);
    }

    //no need for handle() test, repetitive, already tested through flagCell and playMove
}
