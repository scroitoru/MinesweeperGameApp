import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        //when
        controller.initialize();

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
        //TODO
        //verify cells placed on gridPane
        //verify setOnMouseClicked
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
        //use .getGraphic, cast to ImageView, check it's the right image
        ImageView node = (ImageView) sampleUnClickedCell.getGraphic();
        Image image = node.getImage();
        assertNotNull(image);
        //check that image was set to the flag specifically
        assertEquals(image, controller.flagImage);
    }

    @Test //TODO fails, this.controller is null?
    public void unFlagCell(){
        //given
        MinesweeperCell sampleClickedCell = controller.board[1][0]; //(move to given method?)
        sampleClickedCell.wasClicked = true; // why is cell null?

        //when
        controller.flagCell(sampleClickedCell);

        //then
        assertFalse(sampleClickedCell.wasClicked);
        assertNull(sampleClickedCell.getGraphic());
    }

    @Test //TODO incomplete, fails? IllegalStateException
    public void playMoveMine (){
        //given
        givenMinesweeperController();
        MinesweeperCell clickedCell = controller.board[2][1];
        clickedCell.value = MinesweeperCell.MINE;

        //when
        controller.playMove(clickedCell);

        //then
        //check cells were disabled/ .wasClicked =true?
        //verify gameOver method called by checking cells were revealed
        //repetitive, so skip?
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

    @Test  //TODO
    public void handle(){}

    @Test //TODO
    public void revealCell (){
        //test that images are set properly
        //use .getGraphic, cast to ImageView, check it's the right image
        //repetitive?
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
}
