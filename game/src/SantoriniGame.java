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

    //Check if the active Player has lost the game
    public static boolean gameOver(Tile[][][] board, Player activePlayer){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[3][x][y].isOccupiedWithRedBuilder()||board[3][x][y].isOccupiedWithBlueBuilder()) {
                    return true;
                }
            }
        }
        if (activePlayer.getBuilder1().getPossibleMoves().isEmpty()){
            return true;
        }
        return false;
    }


    //MiniMax Algorithm for reduced size board santorini;
    public int miniMax(Tile[][][] board, int depth, Player maxiMizer) throws InvalidMoveException {

        //Check terminal state.
        if (gameOver(board, maxiMizer)){
            return maxiMizer.getPlayerColour()==Alliance.RED?1000:-1000;
        }

        Tile[][][] copyBoard = deepCopy(board);

        System.out.println("MiniMax level : "+ depth + " Current board state :");
        printBoardState(copyBoard);
        //Evaluate at given depth
        if (depth == 3){

            System.out.println("Depth 3 score : " + heightEvaluationFunction(copyBoard) + maxiMizer.getPlayerColour());
            return heightEvaluationFunction(copyBoard);
        }

        //Min and max set
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates = getStates(copyBoard,maxiMizer);

        //Iterate through the list of possible states recursively calling miniMax on the substates.
        for (int i = 0; i < possibleStates.size(); i++){

            //Update the builder coordinates from the list of states so they are correct for the next level of MiniMax
            int [] builderCoordinates = getPieceCoordinatesFromState(possibleStates.get(i), maxiMizer.getPlayerColour());
            BuilderPiece maxBuilder = maxiMizer.getBuilder1();
            maxBuilder.setzCoordinate(builderCoordinates[0]);
            maxBuilder.setxCoordinate(builderCoordinates[1]);
            maxBuilder.setyCoordinate(builderCoordinates[2]);
            System.out.println("Max Builder coordinates from state: " + builderCoordinates[0]+builderCoordinates[1]+builderCoordinates[2]);
            System.out.print("Coordinates from object :");
            maxBuilder.printBuilderStats();


            if (maxiMizer.getPlayerColour()==Alliance.RED){
                int currentScore = miniMax(possibleStates.get(i),depth +1, bluePlayer);
                max = Math.max(currentScore, max);

                if (depth == 0){
                    //System.out.println("computer scores the position as :" + currentScore);
                    //printBoardState(possibleStates.get(i));
                }

//                if (depth == 0) {
//                    System.out.println("optimal state to move to is:");
//                    printBoardState(possibleStates.get(i));
//                    this.board = possibleStates.get(i);
//                }
//
//                if (currentScore == 0){break;}
            }else if (maxiMizer.getPlayerColour()==Alliance.BLUE){
                int currentScore = miniMax(possibleStates.get(i),depth+1,redPlayer);
                min = Math.min(currentScore,min);

            }
        }
        //return 0;
        return maxiMizer.getPlayerColour()==Alliance.RED? max:min;
    }

    // need a function to reverse the 2 step process of state to move.
    public List<Tile[][][]> getStates(Tile[][][] board, Player currentPlayer) throws InvalidMoveException{


        List<Tile[][][]> stateList = new ArrayList<>();
        List<Move> locationMoves = currentPlayer.getValidMoveList(board,currentPlayer.getBuilder1());


        //Add all possible end states to the list of final states.
        for (Move move: locationMoves) {
            //Print to see if original board state is maintained.
            //System.out.println("Original board state");
            //printBoardState(board);


            //Re set board and builder to be from initial state.
            BuilderPiece currentBuilder = new BuilderPiece(currentPlayer.getBuilder1().getxCoordinate(),currentPlayer.getBuilder1().getyCoordinate(),
                    currentPlayer.getBuilder1().getzCoordinate(),currentPlayer.getPlayerColour());
            Player tempPlayer = new Player("temp",currentPlayer.getPlayerColour(),currentBuilder);

            //Do we need new builder as using copy board
            //System.out.println("Deep copy board: ");

            Tile[][][] copyBoard = deepCopy(board);

            //Execute move and get build list from new location
            tempPlayer.moveComputerBuilder(copyBoard,currentBuilder,move);

            List<Move> buildMoves = tempPlayer.getValidBuildList(copyBoard,currentBuilder);


            //Create the final state of the move and the build, re
            //DEEP COPY BUG HERE some how updating the copyBoard when making build move on build copy board
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
            printBoardState(state);
        }
        return stateList;
    }

    public int[] getPieceCoordinatesFromState(Tile[][][] board, Alliance playerColour){
        for (int z = 0; z<4; z++) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < WIDTH; y++) {
                    if (board[z][x][y].isOccupiedWithRedBuilder()&& (playerColour==Alliance.RED)) {
                        return new int[]{z, x, y};
                    } else if (board[z][x][y].isOccupiedWithBlueBuilder() && (playerColour ==Alliance.BLUE)) {
                        return new int[]{z, x, y};
                    }
                }
            }
        }
        return null;
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
