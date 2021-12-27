import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class MinesweeperCell extends Button {
    public final int x;
    public final int y;
    public boolean wasClicked;
    public int value; // value = MINE or 0-8: nrOfAdjacent mines
    public static final int MINE = -1;


    public MinesweeperCell(int x, int y) {
        //use these to determine row and column indexes when adding them to the gridPane
        this.x = x;
        this.y = y;
    }

}
