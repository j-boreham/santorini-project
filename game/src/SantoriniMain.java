import java.util.*;

public class SantoriniMain {
    public static void main(String[] args) throws InvalidMoveException {

        // Initialise players with a single builder
        String playerOne = "Jack", playerTwo = "Brandon";
        Alliance blueTeam = Alliance.BLUE;
        Alliance redTeam = Alliance.RED;

        //Hashmap for move selection
        Map<String,Move> moveHashMap = new HashMap<String,Move>(){{
           put("N", new Move(0,1));
           put("NE",new Move(1,1));
           put("E", new Move(1,0));
           put("SE", new Move(1,-1));
           put("S", new Move(0,-1));
           put("SW", new Move(-1,-1));
           put("W", new Move(-1,0));
           put("NW", new Move(-1,1));

        }};


        int redwins = 0;
        int bluewins = 0;
        int moveCount = 0;
        for (int i = 0; i < 1; i++) { //Loop for game testing
            Player bluePlayer = new Player(playerOne,blueTeam,new BuilderPiece(blueTeam), new BuilderPiece(blueTeam));
            Player redPlayer = new Player(playerTwo, redTeam,new BuilderPiece(redTeam), new BuilderPiece(redTeam));

            //create an instance of the game
            SantoriniGame gameInstance = new SantoriniGame(bluePlayer,redPlayer);
            gameInstance.initialiseBoard();

            Tile[][][] gameBoard = gameInstance.getBoard();


            // Place each of the two builder for a a team
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter X and Y co-ordinates for Blue piece 1");
            bluePlayer.placeBuilder(gameBoard,bluePlayer.builder1,1,3);//scanner.nextInt(),scanner.nextInt());
            gameInstance.printBoardState(gameBoard);

            System.out.println("Enter X and Y co-ordinates for Blue piece 2");
            bluePlayer.placeBuilder(gameBoard,bluePlayer.builder2,3,2);//scanner.nextInt(),scanner.nextInt());
            gameInstance.printBoardState(gameBoard);

            System.out.println("Enter X and Y co-ordinates for Red piece 1");
            redPlayer.placeBuilder(gameBoard,redPlayer.builder1,2,2);//scanner.nextInt(),scanner.nextInt());
            gameInstance.printBoardState(gameBoard);

            System.out.println("Enter X and Y co-ordinates for Red piece 2");
            redPlayer.placeBuilder(gameBoard,redPlayer.builder2,1,1);//scanner.nextInt(),scanner.nextInt());
            gameInstance.printBoardState(gameBoard);

            System.out.println("initial placement printed");
            //System.out.println("Welcome to Santorini, to make moves Enter the compass coordinates of the move" +
            //      "you would like to make, ie N, NE etc.");


            BuilderPiece redBuilder1 = redPlayer.getBuilder1();
            BuilderPiece blueBuilder1 = bluePlayer.getBuilder1();

            BuilderPiece redBuilder2 = redPlayer.getBuilder2();
            BuilderPiece blueBuilder2 = bluePlayer.getBuilder2();



            //-------------------------------------AI Playing vs AI -------------------------------------------------------//
            while (!gameInstance.gameOver(gameBoard, bluePlayer)) {
                try {

                    //Random rand= new Random();
                    //--------------------------Strategy 2 Climb as high as possible- Greedy-----------//
                    //gameBoard = gameInstance.greedyMove(gameBoard,bluePlayer);


//                    //----------------Blue MiniMax -------------------------//
                    long blueStart = System.currentTimeMillis();
                    int blueState = gameInstance.miniMaxBlue(gameBoard,0,bluePlayer,moveCount);
                    long blueEnd = System.currentTimeMillis();
                    System.out.println("Blue Time taken to compute: " + (blueEnd-blueStart));
                    moveCount +=1;
                    gameBoard = gameInstance.getBoard();

                                // In AI version blue plays first.
                    boolean ableToMove=false;

                    //Initial human playable game with no building
//                    System.out.println(bluePlayer.getName() + " Player make your move, enter b1 or b2 for the desired builder, then followed by the compass point move");
//                    String input = scanner.nextLine();
//                    if (input.equals("b1")){
//                        input = scanner.nextLine();
//                        ableToMove = bluePlayer.moveBuilder(gameBoard,bluePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//                        gameInstance.print();
//                        input = scanner.nextLine();
//                        bluePlayer.buildLevel(gameBoard,bluePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//                        gameInstance.print();
//
//
//                    }else if (input.equals("b2")){
//                        input = scanner.nextLine();
//                        ableToMove = bluePlayer.moveBuilder(gameBoard,bluePlayer.builder2,moveHashMap.get(input.toUpperCase()));
//                        gameInstance.print();
//                        input = scanner.nextLine();
//                        bluePlayer.buildLevel(gameBoard,bluePlayer.builder2,moveHashMap.get(input.toUpperCase()));
//                        gameInstance.print();
//                    }



//                    System.out.println("Blue player:" + bluePlayer.getName()+ " moved to: ");
//                    gameInstance.printBoardState(gameBoard);
                    //Check if move won the game for blue
                    if (gameInstance.gameOver(gameBoard, redPlayer)) {
                        bluewins +=1;
                        System.out.println("Blue wins the game!");
                        break;
                    }


                    //ComputerMove for RED
                    //--------------------------Strategy Random------------//

                    //gameBoard = gameInstance.randomMove(gameBoard,redPlayer);


//                    //------------------------ MiniMax Red
                    long startTime = System.currentTimeMillis();
                    int stateValue = gameInstance.miniMaxRed(gameBoard,0,redPlayer);
                    long endTime = System.currentTimeMillis();

                    gameBoard = gameInstance.getBoard();
                    System.out.println("Time taken to compute: " + (endTime-startTime));
                    moveCount+=1;
//                    Tile[][][] newStateRed = gameInstance.getBoard();// possibleMovesRed1.get(stateValue);
//
//                    //System.out.println("This is what it thinks the board state is:");
//                    //gameInstance.printBoardState(newStateRed);
//
//
//                    List<int[]> newPositionsRed = gameInstance.getPieceCoordinatesFromState(newStateRed, Alliance.RED);
//                    redBuilder1.setCoordinates(newPositionsRed.get(0));
//                    redBuilder2.setCoordinates(newPositionsRed.get(1));
//
//                    gameBoard = newStateRed;





                    System.out.println("Red Player moved to: ");
                    gameInstance.printBoardState(gameBoard);

                    if (gameInstance.gameOver(gameBoard,bluePlayer)){
//                        int score = gameInstance.reduceOppOptions(gameBoard,redPlayer);
//                        int scoreb = gameInstance.reduceOppOptions(gameBoard,bluePlayer);
//                System.out.println("Score by adjacent buildings is for blue: "+ score);
//                System.out.println("Score by adjacent buildings is for red: "+ scoreb);


                        System.out.println("Red player Wins ");
                        redwins +=1;
                        break;
                    }



                }catch (InvalidMoveException invalidMoveException){
                    invalidMoveException.printStackTrace();
                }
            }



        }//end of for loop
        System.out.println("Average moves per game = " + moveCount/50);
        System.out.println("Red wins / 100: " + redwins );
        System.out.println("Blue wins /100: " + bluewins);


//        Player activePlayer = bluePlayer;
//        Player otherPlayer = redPlayer;
//
//
//        Boolean ableToMove = false;
//        Player tmp = null;
//    //-------------------------------------AI Playing vs Human -------------------------------------------------------//
//    while (!gameInstance.gameOver(gameBoard, bluePlayer)) {
//        try {
//
//            // In AI version blue plays first.
//            String input = scanner.nextLine();
//            ableToMove = bluePlayer.moveBuilder(gameBoard, bluePlayer.builder1, moveHashMap.get(input.toUpperCase()));
//            gameInstance.printBoardState(gameBoard);
//            input = scanner.nextLine();
//            activePlayer.buildLevel(gameBoard, activePlayer.builder1, moveHashMap.get(input.toUpperCase()));
//            gameInstance.printBoardState(gameBoard);
//
//            //Check if move won the game for blue
//            if (gameInstance.gameOver(gameBoard, redPlayer)) {
//                System.out.println("Blue wins the game!");
//                break;
//            }
//            //ComputerMove for RED
//            //--------------------------Strategy 1 Random------------//
//            List<Tile[][][]> possibleMoves = gameInstance.getStates(gameBoard, redPlayer);
//            Random rand= new Random();
//            Tile[][][] newState = possibleMoves.get(rand.nextInt(possibleMoves.size()));
//            int[] newPosition = gameInstance.getPieceCoordinatesFromState(newState, Alliance.RED);
//
//            redBuilder.setzCoordinate(newPosition[0]);
//            redBuilder.setxCoordinate(newPosition[1]);
//            redBuilder.setzCoordinate(newPosition[2]);
//            gameBoard = newState;
//
//            //---------------------- Heuristic evaluation function ---------------//
//            //gameInstance.miniMax(gameBoard, 0, redPlayer);
//
//
//
//
//
//
//            System.out.println("Computer moved to: ");
//            gameInstance.printBoardState(gameBoard);
//
//            if (gameInstance.gameOver(gameBoard,bluePlayer)){
//                System.out.println("Red player Wins ");
//                break;
//            }
//
//
//
//
//        }catch (InvalidMoveException invalidMoveException){
//            invalidMoveException.printStackTrace();
//        }
//    }


















//
//        //-----------------------------------------Two Person Human game----------------------------------------------//
//        Player activePlayer = bluePlayer;
//        Player otherPlayer = redPlayer;
//        Player tmp = null;
//        Boolean ableToMove = false;
//        while (!gameInstance.gameOver(gameBoard,activePlayer)){
//            try {
//
//                //Initial human playable game with no building
//                System.out.println(activePlayer.getName() + " Player make your move, enter b1 or b2 for the desired builder, then followed by the compass point move");
//                String input = scanner.nextLine();
//                if (input.equals("b1")){
//                    input = scanner.nextLine();
//                    ableToMove = activePlayer.moveBuilder(gameBoard,activePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//                    gameInstance.print();
//                    input = scanner.nextLine();
//                    activePlayer.buildLevel(gameBoard,activePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//                    gameInstance.print();
//
//
//                }else if (input.equals("b2")){
//                    input = scanner.nextLine();
//                    ableToMove = activePlayer.moveBuilder(gameBoard,activePlayer.builder2,moveHashMap.get(input.toUpperCase()));
//                    gameInstance.print();
//                    input = scanner.nextLine();
//                    activePlayer.buildLevel(gameBoard,activePlayer.builder2,moveHashMap.get(input.toUpperCase()));
//                    gameInstance.print();
//                }
//
//                tmp = otherPlayer;
//                otherPlayer = activePlayer;
//                activePlayer = tmp;
//
//
//            }catch (InvalidMoveException invalidMoveException){
//                invalidMoveException.printStackTrace();
//            }
//        } System.out.println(activePlayer.getName()+" has Won the game");
//

    }//End of main


}
