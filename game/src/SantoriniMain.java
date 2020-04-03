import java.util.*;

public class SantoriniMain {
    public static void main(String[] args) throws InvalidMoveException {

        // Initialise players with a single builder
        String playerOne = "Jack", playerTwo = "Brandon";
        Alliance blueTeam = Alliance.BLUE;
        Alliance redTeam = Alliance.RED;
        Player bluePlayer = new Player(playerOne,blueTeam,new BuilderPiece(blueTeam), new BuilderPiece(blueTeam));
        Player redPlayer = new Player(playerTwo, redTeam,new BuilderPiece(redTeam), new BuilderPiece(redTeam));

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

        //create an instance of the game
        SantoriniGame gameInstance = new SantoriniGame(bluePlayer,redPlayer);
        gameInstance.initialiseBoard();

        Tile[][][] gameBoard = gameInstance.getBoard();


        // Place each of the two builder for a a team
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter X and Y co-ordinates for Blue piece 1");
        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder1,2,2);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Blue piece 2");
        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder2,3,3);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Red piece 1");
        redPlayer.placeBuilder(gameBoard,redPlayer.builder1,1,1);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Red piece 2");
        redPlayer.placeBuilder(gameBoard,redPlayer.builder2,1,3);//scanner.nextInt(),scanner.nextInt());
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

            Random rand= new Random();
            //--------------------------Strategy 1 Random------------//
            //Blue plays first
            List<Tile[][][]> possibleMovesBlue1 = gameInstance.getStates(gameBoard, bluePlayer,blueBuilder1);
            List<Tile[][][]> possibleMovesBlue2 = gameInstance.getStates(gameBoard, bluePlayer, blueBuilder2);
            possibleMovesBlue1.addAll(possibleMovesBlue2);


            Tile[][][] newStateBlue = possibleMovesBlue1.get(rand.nextInt(possibleMovesBlue1.size()));
            List<int[]> newPositionsBlue = gameInstance.getPieceCoordinatesFromState(newStateBlue, Alliance.BLUE);
//            System.out.println(" blue builder before: " + " size of new builder positions" + newPositionsBlue.size());
//            blueBuilder1.printBuilderStats();
            blueBuilder1.setzCoordinate(newPositionsBlue.get(0)[0]);
            blueBuilder1.setxCoordinate(newPositionsBlue.get(0)[1]);
            blueBuilder1.setyCoordinate(newPositionsBlue.get(0)[2]);


            blueBuilder2.setzCoordinate(newPositionsBlue.get(1)[0]);
            blueBuilder2.setxCoordinate(newPositionsBlue.get(1)[1]);
            blueBuilder2.setyCoordinate(newPositionsBlue.get(1)[2]);

            gameBoard = newStateBlue;



            System.out.println("Blue player:" + bluePlayer.getName()+ " moved to: ");
            gameInstance.printBoardState(gameBoard);


            //Check if move won the game for blue
            if (gameInstance.gameOver(gameBoard, redPlayer)) {
                System.out.println("Blue wins the game!");
                break;
            }


            //ComputerMove for RED
            //--------------------------Strategy 1 Random------------//
            List<Tile[][][]> possibleMovesRed1 = gameInstance.getStates(gameBoard, redPlayer,redBuilder1);
            List<Tile[][][]> possibleMovesRed2 = gameInstance.getStates(gameBoard, redPlayer, redBuilder2);
            possibleMovesRed1.addAll(possibleMovesRed2);


            Tile[][][] newStateRed = possibleMovesRed1.get(rand.nextInt(possibleMovesRed1.size()));
            List<int[]> newPositionsRed = gameInstance.getPieceCoordinatesFromState(newStateRed, Alliance.RED);

            redBuilder1.setzCoordinate(newPositionsRed.get(0)[0]);
            redBuilder1.setxCoordinate(newPositionsRed.get(0)[1]);
            redBuilder1.setyCoordinate(newPositionsRed.get(0)[2]);

            redBuilder2.setzCoordinate(newPositionsRed.get(1)[0]);
            redBuilder2.setxCoordinate(newPositionsRed.get(1)[1]);
            redBuilder2.setyCoordinate(newPositionsRed.get(1)[2]);

            gameBoard = newStateRed;

//            System.out.println("Red builder: ");
//            redBuilder1.printBuilderStats();
//
//            System.out.println("Blue builder: ");
//            blueBuilder1.printBuilderStats();

            //---------------------- Heuristic evaluation function ---------------//
            //gameInstance.miniMax(gameBoard, 0, redPlayer);



            System.out.println("Red Player moved to: ");
            gameInstance.printBoardState(gameBoard);

            if (gameInstance.gameOver(gameBoard,bluePlayer)){
                System.out.println("Red player Wins ");
                break;
            }




        }catch (InvalidMoveException invalidMoveException){
            invalidMoveException.printStackTrace();
        }
    }






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



















        //-----------------------------------------Two Person Human game----------------------------------------------//

//        while (!gameInstance.gameOver()){
//            try {
//
//                //Initial human playable game with no building
//                System.out.println(activePlayer.getName() + " Player make your move");
//                String input = scanner.nextLine();
//                ableToMove = activePlayer.moveBuilder(gameBoard,activePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//
//                //Check situation where player loses by
//                if (!ableToMove){
//                    tmp = otherPlayer;
//                    otherPlayer = activePlayer;
//                    activePlayer = tmp;
//                    break;
//                }
//                gameInstance.print();
//                input = scanner.nextLine();
//                activePlayer.buildLevel(gameBoard,activePlayer.builder1,moveHashMap.get(input.toUpperCase()));
//                gameInstance.print();
//                tmp = otherPlayer;
//                otherPlayer = activePlayer;
//                activePlayer = tmp;
//
//
//            }catch (InvalidMoveException invalidMoveException){
//                invalidMoveException.printStackTrace();
//            }
//        } System.out.println(activePlayer.getName()+" has Won the game");


    }//End of main


}
