import java.util.ArrayList;


// Class for representing a builder from Santorini
public class BuilderPiece {

    //Required fields will include, the current tile they occupy and the set of next Valid moves.
    protected Tile currentTile;
    protected ArrayList<Tile> validMoveSet;


    public BuilderPiece(Tile currentTile) {
        this.currentTile = currentTile;
        this.validMoveSet = calculateValidMoves();
    }


    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    //Returns the current location of the piece
    public Tile getCurrentTile() {
        return currentTile;
    }


    //Calculate a set of tiles that are valid moves
    public ArrayList<Tile> calculateValidMoves() {
        ArrayList<Tile> possibleMoves = null;

        return possibleMoves;
    }

    public boolean checkLevel3() {
        if (getCurrentTile().getTileLevel()==3)
            return true;
        else return false;
    }
}
