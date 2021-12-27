import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


public class MinesweeperCell extends Button {
    public final int x;
    public final int y;
    public boolean wasClicked;
    public int value; //rename? value = -1: mine , value = 0-8 : nrOfAdjacent mines


//when is each MinesweeperCell instantiated
    public MinesweeperCell(int x, int y) {
        //idk if this is important... need x and y to access buttons later on
        this.x = GridPane.getColumnIndex(this);
        this.y = GridPane.getRowIndex(this);

        //this.x = x;
        //this.y = y;
        wasClicked = false;
        value = 0;

    }

}
