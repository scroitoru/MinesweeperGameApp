import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Objects;


public class MinesweeperCell extends Button {
    public final int x;
    public final int y;
    public int value; // value = MINE or 0-8: nrOfAdjacent mines
    public static final int MINE = -1;

    public MinesweeperCell(int x, int y) {
        //use these to determine row and column indexes when adding cells to the gridPane
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinesweeperCell that = (MinesweeperCell) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y,value);
    }
}
