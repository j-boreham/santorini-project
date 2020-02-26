import java.util.ArrayList;


// Class for representing a builder from Santorini
public class BuilderPiece {

    //Required fields will include, the current tile they occupy and the set of next Valid moves.
    protected int xCoordinate;
    protected int yCoordinate;
    protected int zCoordinate;
    //protected ArrayList<Tile> validMoveSet;


    public BuilderPiece(int xCoordinate, int yCoordinate, int zCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        //this.validMoveSet = calculateValidMoves();
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setzCoordinate(int zCoordinate) {
        this.zCoordinate = zCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public int getzCoordinate() {
        return zCoordinate;
    }


    //    //Calculate a set of tiles that are valid moves
//    public ArrayList<Tile> calculateValidMoves() {
//        ArrayList<Tile> possibleMoves = null;
//
//        return possibleMoves;
//    }

//    public boolean checkLevel3() {
//        if (this.==3)
//            return true;
//        else return false;
//    }
}
