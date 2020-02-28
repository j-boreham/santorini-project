
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public String getName() {
        return name;
    }

    public String getPlayerColour() {
        return playerColour;
    }

    //Placing the builders at the beginning of the game.
    public void placeBuilder(Tile[][][] board, BuilderPiece builder, int xCoordinate, int yCoordinate){

        board[0][xCoordinate][yCoordinate].setOccupiedWithBuilder(true);
        builder.setxCoordinate(xCoordinate);
        builder.setyCoordinate(yCoordinate);
        builder.setzCoordinate(0);
    }

    //filter a particular builders valid moves.
    public List<Move> getValidMoveList(Tile[][][] board,BuilderPiece builder) throws InvalidMoveException{
        List<Move> possibleMoves = builder.getPossibleMoves();
        List<Move> validMoveList = null;

        validMoveList = possibleMoves.stream().filter(move -> isValidMove(board,builder,move)).collect(Collectors.toList());

        if (validMoveList.isEmpty()) {
            System.out.println("This piece is unable to move");
            return null;
        }else return validMoveList;

    }



    //return Valid build list
    public List<Move> getValidBuildList(List<Move> validMoveList, BuilderPiece builder, Tile[][][] board)throws InvalidMoveException{
        List<Move> validBuildList = getValidMoveList(board,builder);
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();



        for (Move move :validMoveList) {
            Tile[][][] tempBoard = board;

            this.moveBuilder(tempBoard, builder, move);

            //Check the move is within the bounds of the board and that the prospective tile is not occupied with a builder
            if (!(tempBoard[builder.getzCoordinate()][builder.getxCoordinate()][builder.getyCoordinate()].isOccupiedWithBuilder()
                    ||builder.getxCoordinate()<0 || builder.getxCoordinate()>1
                    ||builder.getyCoordinate()<0 || builder.getyCoordinate()>1
                    ||builder.getzCoordinate()<0 || builder.getzCoordinate()>3)) {

                validBuildList.add(move);
            }
        }
        return validBuildList;

    }

    //Method handling the build part of a players turn.
    public void buildLevel(Tile[][][] board,BuilderPiece builder, Move move) throws InvalidMoveException{

        int zCoordinate = 0;
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        xCoordinate += move.getX();
        yCoordinate += move.getY();

        //build from the builders new location incrementing the current build height by 1.
        while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
            zCoordinate++;
        }
        if (zCoordinate < 4) {
            board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilding(true);
        }else throw new InvalidMoveException("POO");
    }

    public void moveBuilder(Tile[][][] board, BuilderPiece builder,Move move) throws InvalidMoveException {

        //builder current coordinates set to unoccupied
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        List<Move> validMoves = getValidMoveList(board,builder);

        //Check move is in valid movelist.
        boolean inList = false;
        for (Move play :validMoves) {
            if (play.equals(move)){
                inList = true;
            }
        }
        //If the move is a valid one make the move else throw exception.
        if (inList){
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
        }else throw new InvalidMoveException("Invalid move check and try again");


    }

    //Method that checks if a move is Legal.
    public boolean isValidMove(Tile[][][] board, BuilderPiece builder, Move move){
        int z = builder.getzCoordinate();
        int x = builder.getxCoordinate();
        int y = builder.getyCoordinate();
        //Tile currentLocation = board[z][x][y];

        //update values for proposed move
        int newZCounter = 0;
        int newXCounter = x+move.getX();
        int newYCounter = y+move.getY();

        //Check array bounds first so doesnt throw out of bounds errors, i.e move is on the board.
        if (newXCounter > 1 || newXCounter < 0 || newYCounter > 1 || newYCounter < 0){
            return false;
        }
        //Check the hop in Z isnt greater than 1 level.
        while(board[newZCounter][x][y].isOccupiedWithBuilding()){
            newZCounter++;
        }
        if (newZCounter-z>1){
            return false;
        }

        //If tile is on board and jump isnt too high then finally check if occupied with builder.
        //Update x and y values
        x+=move.getX();
        y+=move.getY();
        Tile prospectiveLocation = board[newZCounter][x][y];

        //Check the move is within the bounds of the board and that the prospective tile is not occupied with a builder
        if (!(prospectiveLocation.isOccupiedWithBuilder())){
            return true;
        }
        return false;
    }



    public boolean unableToMove(){
        if (this.builder1.getPossibleMoves().isEmpty())
            return true;
        else return false;
    }

    public boolean wonByLevel3() {
        if (this.builder1.getzCoordinate()==3) //|| this.builder2.checkLevel3()
            return true;
        else return false;
    }
}
