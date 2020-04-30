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
    public int miniMaxRed(Tile[][][] board, int depth, Player maxiMizer) throws InvalidMoveException {
        //Check terminal state.
        if (gameOver(board, maxiMizer)){
            return 1000; //maxiMizer.getPlayerColour()==Alliance.RED?1000:-1000;
        }
        Tile[][][] copyBoard = deepCopy(board);

        //Evaluate at given depth
        if (depth == 3){
            // Call HSEF
//            int height = heightEvaluationFunction(copyBoard, redPlayer);
//            int oppVert = reduceOppOptions(copyBoard, redPlayer);
//            int vertmob = increaseOptions(copyBoard, redPlayer);
//            System.out.println("State score breakdown for red: Height Diff = "+height+ " opponent mobility = "+oppVert+" Vertical mobilit = "+vertmob);
//            int val = stateEvaluationFunction(copyBoard, redPlayer,1);
//            System.out.println("MiniMax level : "+ depth + " Current board state in terms of red: + : " + val);
            //printBoardState(copyBoard);
            return stateEvaluationFunction(copyBoard, redPlayer,2);
        }

        //Min and max set
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        BuilderPiece maxBuilder1 = maxiMizer.getBuilder1();
        BuilderPiece maxBuilder2 = maxiMizer.getBuilder2();
        List<int[]> builderCoordinates = getPieceCoordinatesFromState(copyBoard, maxiMizer.getPlayerColour());
        maxBuilder1.setCoordinates(builderCoordinates.get(0));
        maxBuilder2.setCoordinates(builderCoordinates.get(1));


        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates1 = getStates(copyBoard,maxiMizer,maxBuilder1);
        List<Tile[][][]> possibleStates2 = getStates(copyBoard,maxiMizer,maxBuilder2);

        possibleStates1.addAll(possibleStates2);

        //Iterate through the list of possible states recursively calling miniMax on the substates.
        Random rand = new Random();
        int bestmove = Integer.MIN_VALUE;
        List<Tile[][][]> bestMoveList = new ArrayList<>();
        for (int i = 0; i < possibleStates1.size(); i++){
            //System.out.println("Move at Depth " + depth);
            //printBoardState(possibleStates1.get(i));
            if (maxiMizer.getPlayerColour()==Alliance.RED){
                int currentScore = miniMaxRed(possibleStates1.get(i),depth +1, bluePlayer);
                max = Math.max(currentScore, max);

                //If the depth is 0 and the best move has been returned also need to update original board to that state so move can be
                // registered in the game.
                if (depth == 0){
                    if (bestmove <= currentScore){
//                        System.out.println("BestMove = " + bestmove);
//                        System.out.println("Max score = " + max);
                        //printBoardState(possibleStates1.get(i));
                        if (bestmove<max){
                            bestmove = max;
                            bestMoveList.clear( );
                            //System.out.println("Score to be added to list: " + max);
                            //printBoardState(possibleStates1.get(i));
                            bestMoveList.add(possibleStates1.get(i));
                        }

                        bestMoveList.add(possibleStates1.get(i));
                        //desiredIndex = i;
                        //System.out.println("Size is "+ possibleStates1.size());
                        //System.out.println("Computer scores the RED position from State list " + i + " as : " + bestmove +" at index :"+ desiredIndex);
                        //printBoardState(possibleStates1.get(i));
                    }
                    if (i == (possibleStates1.size() - 1)){
                        //board = possibleStates1.get(desiredIndex);
                        System.out.println("Size of available moves = " + bestMoveList.size());
                        //System.out.println("time to return there are "+ i +"states");
                        Tile[][][] statetoplay =  bestMoveList.get(rand.nextInt(bestMoveList.size()));
//                        int height = heightEvaluationFunction(statetoplay, redPlayer);
//                        int oppVert = reduceOppOptions(statetoplay, redPlayer);
//                        int vertmob = increaseOptions(statetoplay, redPlayer);
//                        System.out.println("State score breakdown for red: Height Diff = "+height+ " opponent mobility = "+oppVert+" Vertical mobilit = "+vertmob);

                        //this.board = bestMoveList.get(rand.nextInt(bestMoveList.size()));
                        this.board = statetoplay;
                        return 0;
                    }
                }

            }else if (maxiMizer.getPlayerColour()==Alliance.BLUE){
                int currentScore = miniMaxRed(possibleStates1.get(i),depth+1,redPlayer);
                min = Math.min(currentScore,min);

            }
        }
//        if (maxiMizer.getPlayerColour() == Alliance.RED) {
//            System.out.println("MAX value to return = " + max);
//        } else {
//            System.out.println("Min value to return = " + min);
//        }
        return maxiMizer.getPlayerColour()==Alliance.RED? max:min;
    }


    //MiniMax Algorithm for reduced size board santorini;
    public int miniMaxBlue(Tile[][][] board, int depth, Player maxiMizer, int moveCount) throws InvalidMoveException {
        //Check terminal state.
        if (gameOver(board, bluePlayer)){
            return 1000; //maxiMizer.getPlayerColour()==Alliance.RED?1000:-1000;
        }

        // In the first 4 moves can just perform different evaluation as importance varies as the game goes on, gaining height
        //is not yet as relevant
//        if (moveCount<4 && depth == 1){
//            return reduceOppOptions(board, bluePlayer ) + increaseOptions(board,bluePlayer);
//        }

        Tile[][][] copyBoard = deepCopy(board);

        //Evaluate at given depth
        if (depth == 3){
            // Call HSEF

            return stateEvaluationFunction(copyBoard, maxiMizer,1);
        }

        //Min and max set
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        BuilderPiece maxBuilder1 = maxiMizer.getBuilder1();
        BuilderPiece maxBuilder2 = maxiMizer.getBuilder2();
        List<int[]> builderCoordinates = getPieceCoordinatesFromState(copyBoard, maxiMizer.getPlayerColour());
        maxBuilder1.setCoordinates(builderCoordinates.get(0));
        maxBuilder2.setCoordinates(builderCoordinates.get(1));


        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates1 = getStates(copyBoard,maxiMizer,maxBuilder1);
        List<Tile[][][]> possibleStates2 = getStates(copyBoard,maxiMizer,maxBuilder2);

        possibleStates1.addAll(possibleStates2);

        //Iterate through the list of possible states recursively calling miniMax on the substates.
        int bestmove = Integer.MIN_VALUE;
        int desiredIndex =0;
        Random rand = new Random();
        List <Tile[][][]> bestMoveList = new ArrayList<>();
        for (int i = 0; i < possibleStates1.size(); i++){

            if (maxiMizer.getPlayerColour()==Alliance.BLUE){
                int currentScore = miniMaxBlue(possibleStates1.get(i),depth +1, redPlayer, moveCount);
                max = Math.max(currentScore, max);

                //If the depth is 0 and the best move has been returned also need to update original board to that state so move can be
                // registered in the game.
                if (depth == 0){
                    if (bestmove <= currentScore){

                        if (bestmove<max){
                            bestmove = max;
                            bestMoveList.clear( );
                            bestMoveList.add(possibleStates1.get(i));

                        }
                        bestMoveList.add(possibleStates1.get(i));
                        //desiredIndex = i;
                        //System.out.println("Size is "+ possibleStates1.size());
                        //System.out.println("Computer scores the RED position from State list " + i + " as : " + bestmove +" at index :"+ desiredIndex);
                        //printBoardState(possibleStates1.get(i));
                    }
                    if (i == (possibleStates1.size() - 1)){

                        System.out.println("length of final list is: " + bestMoveList.size());
                        this.board = bestMoveList.get(rand.nextInt(bestMoveList.size()));
                        return desiredIndex;
                    }
                }

            }else if (maxiMizer.getPlayerColour()==Alliance.RED){
                int currentScore = miniMaxBlue(possibleStates1.get(i),depth+1,bluePlayer,moveCount);
                min = Math.min(currentScore,min);

            }
        }

        return maxiMizer.getPlayerColour()==Alliance.BLUE? max:min;
    }

    //---------------Heuristic state eval ------------------------------//
    public int stateEvaluationFunction(Tile[][][] currentState, Player activePlayer,int strategy){

        int k1 =0;
        int k2 =0;
        int k3 = 0;
        int k4 = 0;
        if (strategy==1){
            k1 = 1;
            //k3 = 10;
            //k4 = 10;

        }
        if (strategy ==2){
            k1=1;
            //k2=1;
            k3 =2;
            k4 = 2;
        }

        int buildersHeight = k1* heightEvaluationFunction(currentState, activePlayer);
        int verticalMob = k2* (verticalMobility(currentState,activePlayer.getPlayerColour()) - verticalMobility(currentState,getOppAlliance(activePlayer)));
        return buildersHeight + verticalMob;
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
            //printBoardState(copyBoard);
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

    //Value move away from opp
    public int reduceOppOptions(Tile[][][] board, Player activePlayer){
        Alliance playerColour = activePlayer.getPlayerColour();
        List<int[]> oppLocations = getPieceCoordinatesFromState(board, getOppAlliance(activePlayer));


        int score =0;
        //Get opp piece one
        int z1 = oppLocations.get(0)[0];
        int x1 = oppLocations.get(0)[1];
        int y1 = oppLocations.get(0)[2];
        //Get opp piece 2
        int z2 = oppLocations.get(1)[0];
        int x2 = oppLocations.get(1)[1];
        int y2 = oppLocations.get(1)[2];

        //loop through the adjacent squares to each builder checking the height of the buildings surrounding them
        // being adjacent to buildings 1 level above is good, being adjacent to levels 2 buildings above is bad.

        //for (int z = 0; z <4 ; z++) {
        //for piece 1.
        for (int x =-1; x< 2; x++){
            int newXCounter = x+x1;

            for (int y =-1; y<2; y++){
                int z = 0;
                int newYCounter = y+y1;
                //If the tile is on the board evaluate the position
                if ( !(newXCounter > WIDTH-1 || newXCounter < 0 || newYCounter > WIDTH-1 || newYCounter < 0)) {
                    while(board[z][newXCounter][newYCounter].isOccupiedWithBuilding()&&z<4 ){
                        z++;
                        if (z==4 )break;
                    }
                    if (z-z1 == 1) {
                        score -= 10;
                    }else if (z-z1 == 2){
                        score +=15;
                    }else if (z-z1==3){
                        score+=10;
                    }

                }
            }
        }
        //for piece 2
        for (int x =-1; x< 2; x++){
            int newXCounter = x+x2;

            for (int y =-1; y<2; y++){
                int z = 0;
                int newYCounter = y+y2;
                //If the tile is on the board evaluate the position
                if ( !(newXCounter > WIDTH-1 || newXCounter < 0 || newYCounter > WIDTH-1 || newYCounter < 0)) {
                    while(board[z][newXCounter][newYCounter].isOccupiedWithBuilding()&&z<4){
                        z++;
                        if (z==4 )break;
                    }
                    if (z-z1 == 1) {
                        score -= 10;
                    }else if (z-z1 == 2){
                        score +=15;
                    }else if (z-z1==3){
                        score+=10;
                    }

                }
            }
        }
        //}
        return score;
    }


    public int verticalMobility(Tile[][][] board, Alliance playerColour){

        List<int[]> oppLocations = getPieceCoordinatesFromState(board, playerColour);

        //change names opp loocation is actually player location

        int score =0;
        //Get opp piece one
        int z1 = oppLocations.get(0)[0];
        int x1 = oppLocations.get(0)[1];
        int y1 = oppLocations.get(0)[2];
        //Get opp piece 2
        int z2 = oppLocations.get(1)[0];
        int x2 = oppLocations.get(1)[1];
        int y2 = oppLocations.get(1)[2];

        //loop through the adjacent squares to each builder checking the height of the buildings surrounding them
        // being adjacent to buildings 1 level above is good, being adjacent to levels 2 buildings above is bad.

        //for (int z = 0; z <4 ; z++) {
        //for piece 1.
        for (int x =-1; x< 2; x++){
            int newXCounter = x+x1;

            for (int y =-1; y<2; y++){
                int z = 0;
                int newYCounter = y+y1;
                //If the tile is on the board evaluate the position
                if ( !(newXCounter > WIDTH-1 || newXCounter < 0 || newYCounter > WIDTH-1 || newYCounter < 0)) {
                    while(board[z][newXCounter][newYCounter].isOccupiedWithBuilding()&&z<4){
                        z++;
                        if (z==4 )break;
                    }
//                    if (z-z1 == -3 ){
//                        score -= 5;
//                    }else if (z-z1 ==-2){
//                        score -= 5;
//                    } else if (z-z1 == -1){
//                        score -=5;
//                    }
                    //else if (z-z1==0){
                      //  score+=5;}else
                    if (z-z1 == 1) {
                        score += 5;
                    }else if (z-z1 == 2){
                        score -=10;
                    }else if (z-z1==3){
                        score-=15;
                    }else if (z-z1 == 4){
                        score -= 20;
                    }
                }
            }
        }
        //for piece 2
        for (int x =-1; x< 2; x++){
            int newXCounter = x+x2;

            for (int y =-1; y<2; y++){
                int z = 0;
                int newYCounter = y+y2;
                //If the tile is on the board evaluate the position
                if ( !(newXCounter > WIDTH-1 || newXCounter < 0 || newYCounter > WIDTH-1 || newYCounter < 0)) {
                    while(board[z][newXCounter][newYCounter].isOccupiedWithBuilding()&&z<4){
                        z++;
                        if (z==4 )break;
                    }
                    if (z-z2 == 1) {
                        score += 5;
                    }else if (z-z2 == 2){
                        score -=10;
                    }else if (z-z2==3){
                        score-=10;
                    }else if (z-z2 == 4){
                        score -= 20;
                    }

                }
            }
        }
        //}
        return score;
    }


    public Alliance getOppAlliance(Player activePlayer){
        if (activePlayer.getPlayerColour() == Alliance.RED){
            return Alliance.BLUE;
        }else return Alliance.RED;
    }
    //Initial rudimentary State evaluation function.
    public int heightEvaluationFunction(Tile[][][] board, Player activePlayer){
        Alliance playerColour = activePlayer.getPlayerColour();
        int score = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[0][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE){
                    score += 10;
                }if (board[1][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE){
                    score += 40;
                }if (board[2][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE){
                    score += 60;
                }if (board[3][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE) {
                    score += 1000;
                }if (board[0][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.BLUE) {
                    score -= 10;
                }if (board[1][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score -=40;
                }if (board[2][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score -=60;
                }if (board[3][x][y].isOccupiedWithRedBuilder()&& playerColour == Alliance.BLUE){
                    score -=1000;
                }
                if (board[0][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.RED){
                    score += 10;
                }if (board[1][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.RED){
                    score += 40;
                }if (board[2][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.RED){
                    score += 60;
                }if (board[3][x][y].isOccupiedWithRedBuilder() && playerColour == Alliance.RED) {
                    score += 1000;
                }if (board[0][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.RED) {
                    score -= 10;
                }if (board[1][x][y].isOccupiedWithBlueBuilder()&& playerColour == Alliance.RED){
                    score -=40;
                }if (board[2][x][y].isOccupiedWithBlueBuilder()&& playerColour == Alliance.RED){
                    score -=60;
                }if (board[3][x][y].isOccupiedWithBlueBuilder()&& playerColour == Alliance.RED){
                    score -=1000;
                }
            }
        }
    //int winner = redScore + blueScore;
    return score;
    }

    public Tile[][][] randomMove(Tile[][][] board, Player currentPlayer) throws InvalidMoveException {

        Tile[][][] copyBoard = deepCopy(board);
        Random rand = new Random();

        BuilderPiece maxBuilder1 = currentPlayer.getBuilder1();
        BuilderPiece maxBuilder2 = currentPlayer.getBuilder2();
        List<int[]> builderCoordinates = getPieceCoordinatesFromState(copyBoard, currentPlayer.getPlayerColour());
        maxBuilder1.setCoordinates(builderCoordinates.get(0));
        maxBuilder2.setCoordinates(builderCoordinates.get(1));


        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates1 = getStates(copyBoard,currentPlayer,maxBuilder1);
        List<Tile[][][]> possibleStates2 = getStates(copyBoard,currentPlayer,maxBuilder2);
        possibleStates1.addAll(possibleStates2);

        return possibleStates1.get(rand.nextInt(possibleStates1.size()));

    }

    public Tile[][][] greedyMove(Tile[][][] board, Player currentPlayer) throws InvalidMoveException {

        Tile[][][] copyBoard = deepCopy(board);
        Random rand = new Random();

        BuilderPiece maxBuilder1 = currentPlayer.getBuilder1();
        BuilderPiece maxBuilder2 = currentPlayer.getBuilder2();
        List<int[]> builderCoordinates = getPieceCoordinatesFromState(copyBoard, currentPlayer.getPlayerColour());
        maxBuilder1.setCoordinates(builderCoordinates.get(0));
        maxBuilder2.setCoordinates(builderCoordinates.get(1));


        //List of possible states after move and build from calling state.
        List<Tile[][][]> possibleStates1 = getStates(copyBoard,currentPlayer,maxBuilder1);
        List<Tile[][][]> possibleStates2 = getStates(copyBoard,currentPlayer,maxBuilder2);
        possibleStates1.addAll(possibleStates2);
        int max = Integer.MIN_VALUE;
        int bestmove=0;
        List<Tile[][][]> bestMoveList = new ArrayList<>();

        for (int i = 0; i < possibleStates1.size(); i++) {
            int currentScore = greedyEvaluationFunction(possibleStates1.get(i), currentPlayer);
            //bestmove = Math.max(currentScore, max);
            if (bestmove <= currentScore){

                if (bestmove<currentScore){
                    bestMoveList.clear( );
                    bestMoveList.add(possibleStates1.get(i));

                }

                bestmove = currentScore;
                bestMoveList.add(possibleStates1.get(i));
            }
        }
        return bestMoveList.get(rand.nextInt(bestMoveList.size()));
    }

    public int greedyEvaluationFunction(Tile[][][] board, Player activePlayer) {
        Alliance playerColour = activePlayer.getPlayerColour();
        int score = 0;
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < WIDTH; y++) {
                if (board[0][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE) {
                    score += 10;
                } if (board[1][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE) {
                    score += 20;
                } if (board[2][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE) {
                    score += 40;
                } if (board[3][x][y].isOccupiedWithBlueBuilder() && playerColour == Alliance.BLUE) {
                    score += 1000;
                }
            }
        }
        return score;
    }

    public List<Move> getLocationMoves(Tile[][][] state,Player player)throws InvalidMoveException{
        BuilderPiece builder1 = player.getBuilder1();
        BuilderPiece builder2 = player.getBuilder2();
        List<Move> locationMoves1 = player.getValidMoveList(state,builder1);
        List<Move> locationMoves2 = player.getValidMoveList(state,builder2);
        locationMoves1.addAll(locationMoves2);
        return locationMoves1;
    }



}
