
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    final int WIDTH = 5;
    private String name;
    private Alliance playerColour;
    public BuilderPiece builder1;
    public BuilderPiece builder2;

    // Initialise a player.
    public Player(String name, Alliance playerColour, BuilderPiece builder1, BuilderPiece builder2){

        this.name = name;
        this.playerColour = playerColour;
        this.builder1 = builder1;
        this.builder2 = builder2;
    }

    public String getName() {
        return name;
    }

    public Alliance getPlayerColour() {
        return playerColour;
    }

    //Placing the builders at the beginning of the game.
    public void placeBuilder(Tile[][][] board, BuilderPiece builder, int xCoordinate, int yCoordinate){
        if (playerColour.equals(Alliance.RED)){
            board[0][xCoordinate][yCoordinate].setOccupiedWithRedBuilder(true);
        }else{
            board[0][xCoordinate][yCoordinate].setOccupiedWithBlueBuilder(true);
        }
        builder.setxCoordinate(xCoordinate);
        builder.setyCoordinate(yCoordinate);
        builder.setzCoordinate(0);
    }

    //filter a particular builders valid moves.
    public List<Move> getValidMoveList(Tile[][][] board,BuilderPiece builder) throws InvalidMoveException{
        List<Move> possibleMoves = builder.getPossibleMoves();
        List<Move> validMoveList = null;

        validMoveList = possibleMoves.stream().filter(move -> isValidMove(board,builder,move,true)).collect(Collectors.toList());

        return validMoveList;

    }



    //return Valid build list
    public List<Move> getValidBuildList( Tile[][][] board, BuilderPiece builder)throws InvalidMoveException{
        List<Move> possibleMoves = builder.getPossibleMoves();
        List<Move> validBuildList = null;
        validBuildList = possibleMoves.stream().filter(move -> isValidMove(board,builder,move,false)).collect(Collectors.toList());
        if (validBuildList.isEmpty()){
            System.out.println("This piece cannot build");
            return null;
        }else return validBuildList;

    }

    //Will need to handle builders 1 & 2 at some point get builders at location or something?

    //Method handling the build part of a players turn.
    public void buildLevel(Tile[][][] board,BuilderPiece builder, Move move) throws InvalidMoveException{

        List<Move> validBuilds = getValidBuildList(board, builder);
        builder.printBuilderStats();
        int zCoordinate = 0;
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        boolean inList = false;
        for (Move play :validBuilds) {
            if (play.equals(move)){
                inList = true;
            }
        }
        if (inList){
            builder.printBuilderStats();
            xCoordinate += move.getX();
            yCoordinate += move.getY();

            //build from the builders new location incrementing the current build height by 1.
            while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
                zCoordinate++;
                System.out.println(zCoordinate);
            }
            if (zCoordinate < 4 && !(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBlueBuilder()||board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithRedBuilder())){
                builder.printBuilderStats();
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilding(true);
            }else throw new InvalidMoveException("oops Cant build there, try again");
        }else throw new InvalidMoveException("oops Cant build there, try again");
    }

    public boolean moveBuilder(Tile[][][] board, BuilderPiece builder,Move move) throws InvalidMoveException {

        //builder current coordinates set to unoccupied
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

        List<Move> validMoves = getValidMoveList(board,builder);

        if (validMoves.isEmpty()){
            return false;
            //throw new InvalidMoveException("No Valid Moves");
        }

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
            if (playerColour.equals(Alliance.RED)){
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithRedBuilder(false);
            }else{
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBlueBuilder(false);
            }


            // move the builder in accordance with the move resetting z to 0 for build check
            xCoordinate += move.getX();
            yCoordinate += move.getY();
            zCoordinate = 0;
            while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
                zCoordinate++;
            }

            //Set the new tile to contain the builder and update its fields.
            if (playerColour.equals(Alliance.RED)){
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithRedBuilder(true);
            }else{
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBlueBuilder(true);
            }
            builder.setxCoordinate(xCoordinate);
            builder.setyCoordinate(yCoordinate);
            builder.setzCoordinate(zCoordinate);
        }else throw new InvalidMoveException("Invalid move check and try again");

        return true;

    }

    //Computer move doest need to check build list validity.
    public void moveComputerBuilder(Tile[][][] board, BuilderPiece builder,Move move) throws InvalidMoveException {

        //builder current coordinates set to unoccupied
        int zCoordinate = builder.getzCoordinate();
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

            // remove the builder from the board
            if (playerColour.equals(Alliance.RED)){
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithRedBuilder(false);
            }else{
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBlueBuilder(false);
            }


            // move the builder in accordance with the move resetting z to 0 for build check
            xCoordinate += move.getX();
            yCoordinate += move.getY();
            zCoordinate = 0;
            while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
                zCoordinate++;
            }

            //Set the new tile to contain the builder and update its fields.
            if (playerColour.equals(Alliance.RED)){
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithRedBuilder(true);
            }else{
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBlueBuilder(true);
            }
            builder.setxCoordinate(xCoordinate);
            builder.setyCoordinate(yCoordinate);
            builder.setzCoordinate(zCoordinate);
    }

    //Computer build shouldn't need checking.
    public void buildComputerLevel(Tile[][][] board,BuilderPiece builder, Move move) throws InvalidMoveException{

        int zCoordinate = 0;
        int xCoordinate = builder.getxCoordinate();
        int yCoordinate = builder.getyCoordinate();

            //builder.printBuilderStats();
            xCoordinate += move.getX();
            yCoordinate += move.getY();

            //build from the builders new location incrementing the current build height by 1.
            while(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBuilding()){
                zCoordinate++;
            }
            if (zCoordinate < 4 && !(board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithBlueBuilder()||board[zCoordinate][xCoordinate][yCoordinate].isOccupiedWithRedBuilder())) {
                board[zCoordinate][xCoordinate][yCoordinate].setOccupiedWithBuilding(true);
            }
    }


    public BuilderPiece getBuilder1() {
        return builder1;
    }

    public BuilderPiece getBuilder2(){return builder2;}

    //Method that checks if a move is Legal.
    public boolean isValidMove(Tile[][][] board, BuilderPiece builder, Move move,Boolean isLocationMove){
        int z = builder.getzCoordinate();
        int x = builder.getxCoordinate();
        int y = builder.getyCoordinate();

        //update values for proposed move
        int newZCounter = 0;
        int newXCounter = x+move.getX();
        int newYCounter = y+move.getY();

        //These NEED to be changed in terms of BOARD WIDTH which should be set as a global constant
        //Check array bounds first so doesnt throw out of bounds errors, i.e move is on the board.
        if (newXCounter > WIDTH-1 || newXCounter < 0 || newYCounter > WIDTH-1 || newYCounter < 0){
            return false;
        }
        while(board[newZCounter][newXCounter][newYCounter].isOccupiedWithBuilding()){
            if (newZCounter == 3 ){
                return false;
            }else {
                newZCounter++;
            }
            //System.out.println( "This is building level count"+newZCounter);
        }
        //Check the hop in Z isnt greater than 1 level if a geographical move.
        if ((newZCounter - z > 1) && isLocationMove){
            //System.out.println(z + "inside false move");
            return false;
        }

        //If tile is on board and jump isnt too high then finally check if occupied with builder.
        //Update x and y values
        //System.out.println("this shouldnt be reached");
        x+=move.getX();
        y+=move.getY();
        Tile prospectiveLocation = board[newZCounter][x][y];

        //Check the move is within the bounds of the board and that the prospective tile is not occupied with a builder
        if (!(prospectiveLocation.isOccupiedWithRedBuilder()||prospectiveLocation.isOccupiedWithBlueBuilder())){

            return true;
        }
        return false;
    }

}
