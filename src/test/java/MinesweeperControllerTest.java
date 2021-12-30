import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doReturn;
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
