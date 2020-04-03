import java.util.*;

public class SantoriniGame {



    private static Player bluePlayer;
    private static Player redPlayer;
    public static final int WIDTH = 5;
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

    //Check if the active Player has lost the game
    public boolean gameOver(Tile[][][] board, Player activePlayer)throws InvalidMoveException{
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[3][x][y].isOccupiedWithRedBuilder()||board[3][x][y].isOccupiedWithBlueBuilder()) {
                    return true;
                }
            }
        }
        List<Move> validMoves1 = activePlayer.getValidMoveList(board, activePlayer.getBuilder1());
        List<Move> validMoves2 = activePlayer.getValidMoveList(board, activePlayer.getBuilder2());
        validMoves1.addAll(validMoves2);
        if (validMoves1.isEmpty()){
            return true;
        }
        return false;
    }


//    //MiniMax Algorithm for reduced size board santorini;
//    public int miniMax(Tile[][][] board, int depth, Player maxiMizer) throws InvalidMoveException {
//
//        //Check terminal state.
//        if (gameOver(board, maxiMizer)){
//            return maxiMizer.getPlayerColour()==Alliance.RED?1000:-1000;
//        }
//
//        Tile[][][] copyBoard = deepCopy(board);
//
//        //System.out.println("MiniMax level : "+ depth + " Current board state :");
//        //printBoardState(copyBoard);
//        //Evaluate at given depth
//        if (depth == 6){
//
//            System.out.println("Depth 6 score : " + Math.abs(heightEvaluationFunction(copyBoard)) + maxiMizer.getPlayerColour());
//            //printBoardState(copyBoard);
//            return Math.abs(heightEvaluationFunction(copyBoard));
//        }
//
//        //Min and max set
//        int max = Integer.MIN_VALUE;
//        int min = Integer.MAX_VALUE;
//
//        //List of possible states after move and build from calling state.
//        List<Tile[][][]> possibleStates = getStates(copyBoard,maxiMizer);
//
//        //Iterate through the list of possible states recursively calling miniMax on the substates.
//        for (int i = 0; i < possibleStates.size(); i++){
//
//            //Update the builder coordinates from the list of states so they are correct for the next level of MiniMax
//            List<int[]> builderCoordinates = getPieceCoordinatesFromState(possibleStates.get(i), maxiMizer.getPlayerColour());
//            BuilderPiece maxBuilder = maxiMizer.getBuilder1();
//            maxBuilder.setzCoordinate(builderCoordinates[0]);
//            maxBuilder.setxCoordinate(builderCoordinates[1]);
//            maxBuilder.setyCoordinate(builderCoordinates[2]);
//
////            System.out.println("Max Builder coordinates from state: " + builderCoordinates[0]+builderCoordinates[1]+builderCoordinates[2]);
////            System.out.print("Coordinates from object :");
////            maxBuilder.printBuilderStats();
//
//
//            if (maxiMizer.getPlayerColour()==Alliance.RED){
//                int currentScore = miniMax(possibleStates.get(i),depth +1, bluePlayer);
//                max = Math.max(currentScore, max);
//
//                //If the depth is 0 and the best move has been returned also need to update original board to that state so move can be
//                // registered in the game.
//                if (depth == 0){
//                    System.out.println("Computer scores the position from State list " + i + " as : " + currentScore);
//                    printBoardState(possibleStates.get(i));
//                }
//
////                if (depth == 0) {
////                    System.out.println("optimal state to move to is:");
////                    printBoardState(possibleStates.get(i));
////                    this.board = possibleStates.get(i);
////                }
//
//            }else if (maxiMizer.getPlayerColour()==Alliance.BLUE){
//                int currentScore = miniMax(possibleStates.get(i),depth+1,redPlayer);
//                min = Math.min(currentScore,min);
//
//            }
//        }
//        System.out.println("Red score@ depth " + depth+ " = "+max);
//        System.out.println("Blue score@ depth" + depth+ " = "+min);
//        System.out.println(" "+maxiMizer.getPlayerColour());
//        return maxiMizer.getPlayerColour()==Alliance.RED? max:min;
//    }

    // need a function to reverse the 2 step process of state to move.
    public List<Tile[][][]> getStates(Tile[][][] board, Player currentPlayer, BuilderPiece builder) throws InvalidMoveException{


        List<Tile[][][]> stateList = new ArrayList<>();

        //int[] pieceLocation = getPieceCoordinatesFromState(board, currentPlayer.getPlayerColour());
        //BuilderPiece builderInState = new BuilderPiece(pieceLocation[1],pieceLocation[2],pieceLocation[0], currentPlayer.getPlayerColour());

        BuilderPiece builderInState = new BuilderPiece(builder.getxCoordinate(),builder.getyCoordinate(),
                builder.getzCoordinate(),currentPlayer.getPlayerColour());



        List<Move> locationMoves = currentPlayer.getValidMoveList(board,builderInState);
        //List<Move> locationMoves = currentPlayer.getValidMoveList(board,currentPlayer.getBuilder1());


        //Add all possible end states to the list of final states.
        for (Move move: locationMoves) {

            BuilderPiece currentBuilder = new BuilderPiece(builderInState.getxCoordinate(),builderInState.getyCoordinate(),builderInState.getzCoordinate(),currentPlayer.getPlayerColour());
            Player tempPlayer = new Player("temp",currentPlayer.getPlayerColour(),currentBuilder, null);

            Tile[][][] copyBoard = deepCopy(board);

            //Execute move and get build list from new location

            tempPlayer.moveComputerBuilder(copyBoard,currentBuilder,move);
            List<Move> buildMoves = tempPlayer.getValidBuildList(copyBoard,currentBuilder);

            //Get end states of all possible builds from all possible states
            for (Move build: buildMoves) {

                Tile[][][] buildCopyBoard = deepCopy(copyBoard);

                //System.out.println("Deep copy build board: "+ y);
                //printBoardState(buildCopyBoard);

                tempPlayer.buildComputerLevel(buildCopyBoard,currentBuilder,build);
                //printBoardState(buildCopyBoard);

                stateList.add(buildCopyBoard);

            }
        }
        int i = 1;
        for (Tile[][][] state : stateList) {
            System.out.println("State in list "+ i);
            i+=1;
            //printBoardState(state);
        }
        return stateList;
    }

    public List<int[]> getPieceCoordinatesFromState(Tile[][][] board, Alliance playerColour){
        List<int[]> bothBuilderPositions = new ArrayList<>();

        for (int z = 0; z<4; z++) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    if (board[z][x][y].isOccupiedWithRedBuilder()&& (playerColour==Alliance.RED)) {
                        int[] piecePosition = new int[]{z, x, y};
                        bothBuilderPositions.add(piecePosition);

                    } else if (board[z][x][y].isOccupiedWithBlueBuilder() && (playerColour ==Alliance.BLUE)) {
                        int[] piecePosition = new int[]{z, x, y};
                        bothBuilderPositions.add(piecePosition);
                    }
                }
            }
        }
        return bothBuilderPositions;
    }


//    //DeepCopy for board
//    <T> T[][][] deepCopy(T[][][] matrix) {
//        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
//    }

    //Deep copy board state
    public Tile[][][] deepCopy(Tile[][][] oldBoard){
        Tile [][][] newBoard = new Tile[4][WIDTH][WIDTH];
        for (int z = 0; z < 4;z++) {
            //Printing the X Y from the top right left corner to make it readable.
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    if (oldBoard[z][x][y].isOccupiedWithRedBuilder) {
                        newBoard[z][x][y] = new Tile(true, false, false);
                    } else if (oldBoard[z][x][y].isOccupiedWithBlueBuilder) {
                        newBoard[z][x][y] = new Tile(false, true, false);
                    } else if (oldBoard[z][x][y].isOccupiedWithBuilding) {
                        newBoard[z][x][y] = new Tile(false, false, true);
                    } else newBoard[z][x][y] = new Tile(false, false, false);
                }
            }
        }
        return newBoard;
    }

    //Initial rudimentary State evaluation function.
    public int heightEvaluationFunction(Tile[][][] board){
        int redScore=0;
        int blueScore=0;
        for (int z = 0; z < 4; z++) {
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
        return winner;
        //        if (winner < 0 ){
//            return blueScore;
//        }else return redScore;
    }

    //Initial rudimentary State evaluation function.
public int heightDifferenceEvaluationFunction(Tile[][][] board){
    int redScore=0;
    int blueScore=0;
    for (int z = 0; z < 4; z++) {
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
    return winner;

}



}
