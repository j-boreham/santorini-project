public class Tile {

    private int row;
    private int col;
    private int tileLevel;

    public Tile(int row, int col, int tileLevel){
        this.row = row;
        this.col = col;
        this.tileLevel = tileLevel;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTileLevel() {
        return tileLevel;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }



}

