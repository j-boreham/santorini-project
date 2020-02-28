
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

        board[0][xCoordinate][yCoordinate].setOccupiedWithBuilder(true);
        builder.setxCoordinate(xCoordinate);
        builder.setyCoordinate(yCoordinate);
        builder.setzCoordinate(0);
    }

    //filter a particular builders valid moves.
    public List<Move> getValidMoveList(BuilderPiece builder,Tile[][][] board) throws InvalidMoveException{
        List<Move> possibleMoves = builder.getPossibleMoves();
        List<Move> validMoveList = null;
        int zCoordinateCounter = builder.getzCoordinate();
        //for each of the possible moves check its validity and add it to teh set if legal
        L1:for (Move move :possibleMoves) {

            //Use temp board each time so not to affect the actual game board
            Tile[][][] tempBoard = board;
            this.moveBuilder(tempBoard, builder, move);

            //Check the move is within the bounds of the board and that the prospective tile is not occupied with a builder
            if (!(tempBoard[builder.getzCoordinate()][builder.getxCoordinate()][builder.getyCoordinate()].isOccupiedWithBuilder()
                    ||builder.getxCoordinate()<0 || builder.getxCoordinate()>1
                    ||builder.getyCoordinate()<0 || builder.getyCoordinate()>1
                    ||builder.getzCoordinate()<0 || builder.getzCoordinate()>3)) {

                //if (tempBoard[builder.getzCoordinate()][builder.getxCoordinate()][builder.getyCoordinate()].isOccupiedWithBuilding())

                //obtain the buildheight of the prospective move, if it is a hop of more than one level do not add to list.
                while(board[zCoordinateCounter][builder.getxCoordinate()][builder.getyCoordinate()].isOccupiedWithBuilding()){
                    zCoordinateCounter++;
                }
                if (zCoordinateCounter-builder.getzCoordinate()>1){
                    continue L1;
                }
                else {
                    validMoveList.add(move);
                }

            }else throw new InvalidMoveException();
        }

        return validMoveList;

    }

    public List<Move> getValidBuildList(List<Move> validMoveList, BuilderPiece builder, Tile[][][] board)throws InvalidMoveException{
        List<Move> validBuildList = null;

        for (Move move :validMoveList) {
            Tile[][][] tempBoard = board;
            this.moveBuilder(tempBoard, builder, move);

            //Check the move is within the bounds of the board and that the prospective tile is not occupied with a builder
            if (tempBoard[builder.getzCoordinate()][builder.getxCoordinate()][builder.getyCoordinate()].isOccupiedWithBuilder()
                    ||builder.getxCoordinate()<0 || builder.getxCoordinate()>1
                    ||builder.getyCoordinate()<0 || builder.getyCoordinate()>1
                    ||builder.getzCoordinate()<0 || builder.getzCoordinate()>3) {

                validBuildList.add(move);
            }else {
            }
        }
        if (validBuildList.isEmpty()){
            System.out.println("This piece is unable to move");
            return null;
        }else return validBuildList;

    }

    public void buildLevel(Tile[][][] board,BuilderPiece builder, Move move){

        int zCoordinate = 0;
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        xCoordinate += move.getX();
        yCoordinate += move.getY();

        while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
            zCoordinate++;
        }
        board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilding(true);

    }

    public void moveBuilder(Tile[][][] board, BuilderPiece builder,Move move) throws InvalidMoveException {

        //builder current coordinates set to unoccupied
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        List<Move> validMoves = getValidMoveList(builder,board);

        //If the move is a valid one make the move else throw exception.
        if (validMoves.contains(move)){
            // remove the builder from the board
            board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilder(false);

            // move the builder in accordance with the move
            xCoordinate += move.getX();
            yCoordinate += move.getY();
            while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
                zCoordinate++;
            }

            //Set the new tile to contain the builder and update its fields.
            board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilder(true);
            builder.setxCoordinate(xCoordinate);
            builder.setyCoordinate(yCoordinate);
            builder.setzCoordinate(zCoordinate);
        }else throw new InvalidMoveException();


        //OLD Move method.
//        // if the proposed new position is not already occupied, move the builder and update its position
//        if (!board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilder) {
//            board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilder(true);
//            builder.setxCoordinate(xCoordinate);
//            builder.setyCoordinate(yCoordinate);
//        } else throw new InvalidMoveException();

    }





    public boolean wonByLevel3() {
        if (this.builder1.checkLevel3()) //|| this.builder2.checkLevel3()
            return true;
        else return false;
    }
}
