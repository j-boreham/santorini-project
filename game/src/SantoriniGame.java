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


    //MiniMax Algorithm for reduced size board santorini;
    public int miniMax(Tile[][][] board, int depth, Player maxiMizer) throws InvalidMoveException {

        //Check terminal state.
        if (gameOver(board, maxiMizer)){
            return maxiMizer.getPlayerColour()==Alliance.RED?1000:-1000;
        }

        Tile[][][] copyBoard = deepCopy(board);

        //System.out.println("MiniMax level : "+ depth + " Current board state :");
        //printBoardState(copyBoard);
        //Evaluate at given depth
        if (depth == 3){
            // Call HSEF

            return stateEvaluationFunction(copyBoard, maxiMizer);
        }

        //Min and max set
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        BuilderPiece maxBuilder1 = maxiMizer.getBuilder1();
        BuilderPiece maxBuilder2 = maxiMizer.getBuilder2();
        int[] maxBuilder1coordinates = maxBuilder1.getCoordinates();
        int[] maxBuilder2coordinates = maxBuilder2.getCoordinates();


        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates1 = getStates(copyBoard,maxiMizer,maxBuilder1);
        List<Tile[][][]> possibleStates2 = getStates(copyBoard,maxiMizer,maxBuilder2);

        possibleStates1.addAll(possibleStates2);

        //Iterate through the list of possible states recursively calling miniMax on the substates.
        for (int i = 0; i < possibleStates1.size(); i++){

            //Update the builder coordinates from the list of states so they are correct for the next level of MiniMax
            List<int[]> builderCoordinates = getPieceCoordinatesFromState(possibleStates1.get(i), maxiMizer.getPlayerColour());
            if (Arrays.equals(builderCoordinates.get(0), maxBuilder1coordinates)&& !Arrays.equals(builderCoordinates.get(1),maxBuilder2coordinates)){
                System.out.println("Success they are equal!!!!!");
                maxBuilder2.setCoordinates(builderCoordinates.get(1));
            }else if (Arrays.equals(builderCoordinates.get(1), maxBuilder1coordinates)&&!Arrays.equals(builderCoordinates.get(0),maxBuilder2coordinates)){
                maxBuilder2.setCoordinates(builderCoordinates.get(0));
            }else if (Arrays.equals(builderCoordinates.get(0), maxBuilder2coordinates) && !Arrays.equals(builderCoordinates.get(1),maxBuilder1coordinates)){
                maxBuilder1.setCoordinates(builderCoordinates.get(1));
            }else if (Arrays.equals(builderCoordinates.get(1), maxBuilder2coordinates) &&!Arrays.equals(builderCoordinates.get(0),maxBuilder1coordinates)){
                maxBuilder1.setCoordinates(builderCoordinates.get(0));
            }
//            System.out.println("Max Builder coordinates from state: " + builderCoordinates[0]+builderCoordinates[1]+builderCoordinates[2]);
//            System.out.print("Coordinates from object :");
//            maxBuilder.printBuilderStats();


            if (maxiMizer.getPlayerColour()==Alliance.RED){
                int currentScore = miniMax(possibleStates1.get(i),depth +1, bluePlayer);
                max = Math.max(currentScore, max);

                //If the depth is 0 and the best move has been returned also need to update original board to that state so move can be
                // registered in the game.
                if (depth == 0){
                    System.out.println("Computer scores the position from State list " + i + " as : " + currentScore);
                    printBoardState(possibleStates1.get(i));
                }

//                if (depth == 0) {
//                    System.out.println("optimal state to move to is:");
//                    printBoardState(possibleStates.get(i));
//                    this.board = possibleStates.get(i);
//                }

            }else if (maxiMizer.getPlayerColour()==Alliance.BLUE){
                int currentScore = miniMax(possibleStates1.get(i),depth+1,redPlayer);
                min = Math.min(currentScore,min);

            }
        }
//        System.out.println("Red score@ depth " + depth+ " = "+max);
//        System.out.println("Blue score@ depth" + depth+ " = "+min);
//        System.out.println(" "+maxiMizer.getPlayerColour());
        return maxiMizer.getPlayerColour()==Alliance.RED? max:min;
    }


    //---------------Heuristic state eval ------------------------------//
    public int stateEvaluationFunction(Tile[][][] currentState, Player activePlayer){
        int k1 = 1;
        int k2 = 1;
        int buildersHeight = k1* heightEvaluationFunction(currentState, activePlayer);
        int keepOppDown = k2* keepOppDown(currentState, activePlayer);

        return buildersHeight + keepOppDown;
    }

    // need a function to reverse the 2 step process of state to move.
    public List<Tile[][][]> getStates(Tile[][][] board, Player currentPlayer, BuilderPiece builder) throws InvalidMoveException{

        List<Tile[][][]> stateList = new ArrayList<>();

        //int[] pieceLocation = getPieceCoordinatesFromState(board, currentPlayer.getPlayerColour());
        //BuilderPiece builderInState = new BuilderPiece(pieceLocation[1],pieceLocation[2],pieceLocation[0], currentPlayer.getPlayerColour());

        BuilderPiece builderInState = new BuilderPiece(builder.getxCoordinate(),builder.getyCoordinate(),
                builder.getzCoordinate(),currentPlayer.getPlayerColour());

        List<Move> locationMoves = currentPlayer.getValidMoveList(board,builderInState);

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
                tempPlayer.buildComputerLevel(buildCopyBoard,currentBuilder,build);
                stateList.add(buildCopyBoard);

            }
        }
        int i = 1;
//        for (Tile[][][] state : stateList) {
//            System.out.println("State in list "+ i);
//            i+=1;
//            //printBoardState(state);
//        }
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
    public int heightEvaluationFunction(Tile[][][] board, Player activePlayer){
        Alliance playerColour = activePlayer.getPlayerColour();
        int score = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[0][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 10;
                }else if (board[0][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.BLUE) {
                    score += 10;
                }else if (board[1][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 20;
                }else if (board[1][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score +=20;
                }else if (board[2][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 40;
                }else if (board[2][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score +=40;
                }else if (board[3][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score -= 1000;
                }else if (board[3][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score -=1000;
                }

            }
        }
    //int winner = redScore + blueScore;
    return score;
    }

    public int selectBestMove(List<Tile[][][]> possibleStates, Player player){
        int bestScore = 0;
        int index = 0;
        int desiredIndex = 0;

        // Look through all possible next states and get the best value returning the state index
        for (Tile[][][] state: possibleStates) {
            int score = heightEvaluationFunction(state, player);
            if (bestScore<score){
                bestScore = Math.max(score,bestScore);
                desiredIndex = index;
            }
            index+=1;
        }
        return desiredIndex;
    }


    public int reduceOppPossMoves( List<Tile[][][]> possibleStates, Player opponent)throws InvalidMoveException{
        int index = 0;
        int desiredMove = 0;
        int minMoves = Integer.MAX_VALUE;
        for (Tile[][][] state : possibleStates) {
            List<Move> moves = getLocationMoves(state, opponent);
            if (moves.size()<minMoves) {
                minMoves = Math.min(minMoves, moves.size());
                desiredMove = index;
            }
            index+=1;
        }
    return desiredMove;
    }

    public List<Move> getLocationMoves(Tile[][][] state,Player player)throws InvalidMoveException{
        BuilderPiece builder1 = player.getBuilder1();
        BuilderPiece builder2 = player.getBuilder2();
        List<Move> locationMoves1 = player.getValidMoveList(state,builder1);
        List<Move> locationMoves2 = player.getValidMoveList(state,builder2);
        locationMoves1.addAll(locationMoves2);
        return locationMoves1;
    }

    //Initial rudimentary State evaluation function.
    public int keepOppDown(Tile[][][] board, Player activePlayer){
        Alliance playerColour = activePlayer.getPlayerColour();
        int score = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[0][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 40;
                }else if (board[0][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.BLUE) {
                    score += 40;
                }else if (board[1][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 20;
                }else if (board[1][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score +=20;
                }else if (board[2][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score += 10;
                }else if (board[2][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score +=10;
                }else if (board[3][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED){
                    score -= 1000;
                }else if (board[3][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score -=1000;
                }

            }
        }
        return score;

    }

//    public int secondLevelBuild(){
//
//    }



}
