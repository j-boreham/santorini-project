import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Class for representing a builder from Santorini
public class BuilderPiece {

    //Required fields will include, the current tile they occupy and the set of next Valid moves.
    protected int xCoordinate;
    protected int yCoordinate;
    protected int zCoordinate;
    protected List<Move> possibleMoves;

    public BuilderPiece(){

    }

    public BuilderPiece(int xCoordinate, int yCoordinate, int zCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.possibleMoves = getPossibleMoves();
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

    public List<Move> getPossibleMoves(){
        Move moveNorth = new Move(0,1);
        Move moveNorthEast = new Move(1,1);
        Move moveEast = new Move(1,0);
        Move moveSouthEast = new Move(-1,1);
        Move moveSouth = new Move(0,-1);
        Move moveSouthWest = new Move(-1,-1);
        Move moveWest = new Move(-1,0);
        Move moveNorthWest = new Move(-1,1);
        return Arrays.asList(moveNorth,moveNorthEast,moveEast,moveSouthEast,moveSouth,moveSouthWest,moveWest,moveNorthWest);
    }

    public boolean checkLevel3() {
        if (this.getzCoordinate()==3){
            return true;
        }else return false;
    }


    //    //Calculate a set of tiles that are valid moves
//    public ArrayList<Tile> calculateValidMoves() {
//        ArrayList<Tile> possibleMoves = null;
//
//        return possibleMoves;
//    }


}
