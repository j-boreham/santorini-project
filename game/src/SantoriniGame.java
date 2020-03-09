import java.util.*;

public class SantoriniGame {



    private static Player bluePlayer;
    private static Player redPlayer;
    public static final int WIDTH = 2;
    Tile[][][] board = new Tile[4][WIDTH][WIDTH];

    public SantoriniGame( Player bluePlayer, Player redPlayer) {
        this.bluePlayer = bluePlayer;
        this.redPlayer = redPlayer;
    }

    public Tile[][][]getBoard(){
        return board;
    }

    // Initial Board.
    public void initialiseBoard(){
        for (int z = 0; z < 4; z++) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    board[z][x][y] = new Tile(false,false,false);
                }
            }
        }
    }


    //Print the initialBoard
    public void print(){
        for (int z = 0; z < 4;z++) {
            System.out.println("Level: " + z);

            //Printing the X Y from the top right left corner to make it readable.
            for (int y = WIDTH-1; y >-1 ; y--) {
                for (int x = 0; x < WIDTH; x++) {
                    if (board[z][x][y].isOccupiedWithRedBuilder) {
                        System.out.print("[B]");
                    }else if (board[z][x][y].isOccupiedWithBlueBuilder){
                        System.out.print("[J]");
                    }
                    else if (board[z][x][y].isOccupiedWithBuilding) {
                        System.out.print("[+]");
                    }else System.out.print("[ ]");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("===========================================");
    }

    public void printBoardState(Tile[][][] boardState){
        for (int z = 0; z < 4;z++) {
            System.out.println("Level: " + z);

            //Printing the X Y from the top right left corner to make it readable.
            for (int y = WIDTH-1; y >-1 ; y--) {
                for (int x = 0; x < WIDTH; x++) {
                    if (boardState[z][x][y].isOccupiedWithRedBuilder) {
                        System.out.print("[B]");
                    }else if (boardState[z][x][y].isOccupiedWithBlueBuilder){
                        System.out.print("[J]");
                    }
                    else if (boardState[z][x][y].isOccupiedWithBuilding) {
                        System.out.print("[+]");
                    }else System.out.print("[ ]");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("===========================================");
    }

    public static boolean gameOver(){
        if (bluePlayer.wonByLevel3() || redPlayer.wonByLevel3()){
            return true;
        }else return false;
    }

    //MiniMax Algorithm for reduced size board santorini;

    public int miniMax(Tile[][][] board, int depth, Player maxiMizer) throws InvalidMoveException {
        Tile[][][] copyBoard = board;
        //Check terminal state.
        if (isEndState(copyBoard) == -1){
            return - 1;
        }else if (isEndState(copyBoard)== 1){
            return 1;
        }

        //Min and max set
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;

        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates = getStates(copyBoard,maxiMizer);

        //Iterate through the list of possible states recursively calling miniMax on the substates.
        for (int i = 0; i < possibleStates.size(); i++){
            if (maxiMizer.getPlayerColour()==Alliance.RED){
                int currentScore = miniMax(possibleStates.get(i),depth +1, bluePlayer);
                max = Math.max(currentScore, min);

                if (currentScore>=0) {
                    if (depth == 0) {
                        System.out.println("optimal state to move to is:");
                        printBoardState(possibleStates.get(i));
                        this.board = possibleStates.get(i);
                    }
                }
            }else if (maxiMizer.getPlayerColour()==Alliance.BLUE){
                int currentScore = miniMax(possibleStates.get(i),depth+1,redPlayer);
                min = Math.min(currentScore,max);

            }
        }
        return maxiMizer.getPlayerColour()==Alliance.RED? max:min;
    }

    // need a function to reverse the 2 step process of state to move.
    public List<Tile[][][]> getStates(Tile[][][] board, Player currentPlayer) throws InvalidMoveException{


        List<Tile[][][]> stateList = new ArrayList<>();
        BuilderPiece builderCopy = new BuilderPiece(currentPlayer.getBuilder1().getzCoordinate(),currentPlayer.getBuilder1().getxCoordinate(),
                                                    currentPlayer.getBuilder1().getzCoordinate(),currentPlayer.getPlayerColour());
        List<Move> locationMoves = currentPlayer.getValidMoveList(board,builderCopy);


        //Add all possible end states to the list of final states.
        for (Move move: locationMoves) {
            //Re set board and builder to be from initial state.
            BuilderPiece currentBuilder = builderCopy;
            Tile[][][] copyBoard = new Tile[][][];

            //Execute move and get build list from new location
            currentPlayer.moveComputerBuilder(copyBoard,currentBuilder,move);
            List<Move> buildMoves = currentPlayer.getValidBuildList(copyBoard,currentBuilder);

            //Create the final state of the move and the build, re
            for (Move build: buildMoves) {
                Tile[][][] buildCopyBoard = copyBoard;
                currentPlayer.buildComputerLevel(buildCopyBoard,currentBuilder,build);
                stateList.add(buildCopyBoard);
            }
        }
        return stateList;
    }

    public int isEndState(Tile[][][] board){
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    if (board[3][x][y].isOccupiedWithRedBuilder()){
                        return 1;
                    }else if(board[3][x][y].isOccupiedWithBlueBuilder()){
                        return -1;
                    }
                }
            }
            return 0;
    }

//    //Need to write an iterable collection of move pairs
//    public void getListOfWholeMoves(){
//
//    }

    //Initial rudimentary State evaluation function.
    public int evaluationFunction(){
        int redScore=0, blueScore=0,z = 0;
        for (z = 0; z < 4; z++) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    if (board[1][x][y].isOccupiedWithBlueBuilder()){
                            blueScore -= 10;
                    }else if (board[1][x][y].isOccupiedWithRedBuilder()){
                        redScore +=10;
                    }else if (board[2][x][y].isOccupiedWithBlueBuilder()){
                        blueScore -= 20;
                    }else if (board[2][x][y].isOccupiedWithRedBuilder()){
                        redScore +=20;
                    }else if (board[3][x][y].isOccupiedWithBlueBuilder()){
                        blueScore -= 1000;
                    }else if (board[3][x][y].isOccupiedWithRedBuilder()){
                        redScore +=1000;
                    }

                }
            }
        }
        int winner = redScore + blueScore;
        if (winner < 0 ){
            return blueScore;
        }else return redScore;
    }



}
