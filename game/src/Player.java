
import java.util.Arrays;
import java.util.List;

public class Player {

    private String name;
    private String playerColour;
    public BuilderPiece builder1;
    //protected BuilderPiece builder2;

    // Initialise a player.
    public Player(String name, String playerColour, BuilderPiece builder1){

        this.name = name;
        this.playerColour = playerColour;
        this.builder1 = builder1;
        //builder2 = new BuilderPiece(4,4,4);
    }

    //Placing the builders at the beginning of the game.
    public void placeBuilder(Tile[][][] board, BuilderPiece builder, int xCoordinate, int yCoordinate){

        board[0][xCoordinate][yCoordinate].setOccupied(true);
        builder.setxCoordinate(xCoordinate);
        builder.setyCoordinate(yCoordinate);
        builder.setzCoordinate(0);
    }


    public void moveBuilder(Tile[][][] board, BuilderPiece builder,Move move) throws InvalidMoveException {

        //builder current coordinates set to unoccupied
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        // remove the builder from the board
        board[zCoordinate][xCoordinate][yCoordinate].setOccupied(false);

        // move the builder in accordance with the move
        xCoordinate += move.getX();
        yCoordinate += move.getY();

        // if the proposed new position is not already occupied, move the builder and update its position
        if (!board[zCoordinate][xCoordinate][yCoordinate].isOccupied) {
            board[zCoordinate][xCoordinate][yCoordinate].setOccupied(true);
            builder.setyCoordinate(yCoordinate);
        } else throw new InvalidMoveException();

    }




//    public boolean isAbleToMove(){
//        if(validMoveSet == null){
//            return false;
//        }else return true;
//    }

    public boolean wonByLevel3() {
        if (this.builder1.checkLevel3()) //|| this.builder2.checkLevel3()
            return true;
        else return false;
    }
}
