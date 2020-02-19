import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.util.ArrayList;

public class Player {

    protected String name;
    protected String playerColour;
    protected BuilderPiece builder1;
    protected BuilderPiece builder2;
    protected ArrayList<Tile> validMoveSet;

    // Initialise a player.
    public Player(String name, String playerColour){

        this.name = name;
        this.playerColour = playerColour;
        builder1 = new BuilderPiece(null);
        builder2 = new BuilderPiece(null);
    }



    public Tile placeBuilder(int row, int col, BuilderPiece builder){

        Tile placementTile = new Tile(row,col,0);
        builder.setCurrentTile(placementTile);
        return placementTile;
    }

    public boolean isAbleToMove(){
        if(validMoveSet == null){
            return false;
        }else return true;
    }

    public boolean wonByLevel3() {
        if (this.builder1.checkLevel3() || this.builder2.checkLevel3())
            return true;
        else return false;
    }
}
