 public class Cell {
   //change access modifiers
    public final int x;
    public final int y;
    public boolean wasClicked;
    public int value; //rename? value = -1: mine , value = 0-8 : nrOfAdjacent mines

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        wasClicked = false;
        value = 0;
    }
}
