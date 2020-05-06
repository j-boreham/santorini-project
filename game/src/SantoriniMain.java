import java.util.*;


    //Main java file creating the game Instance for Santorini and the necessary components
public class SantoriniMain {
    public static void main(String[] args) throws InvalidMoveException {

        // Initialise players with a single builder
        String playerOne = "Brandon", playerTwo = "Jack";
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

        //Create player instances
        Player bluePlayer = new Player(playerOne,blueTeam,new BuilderPiece(blueTeam), new BuilderPiece(blueTeam));
        Player redPlayer = new Player(playerTwo, redTeam,new BuilderPiece(redTeam), new BuilderPiece(redTeam));

        //create an instance of the game and board and initialise it
        SantoriniGame gameInstance = new SantoriniGame(bluePlayer,redPlayer);
        gameInstance.initialiseBoard();

        Tile[][][] gameBoard = gameInstance.getBoard();

        // Place each of the two builder for a a team
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter X and Y co-ordinates for Blue piece 1 this location note that this is builder 1");
        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder1,1,3);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Blue piece 2 note that this is builder 2");
        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder2,3,2);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Red piece 1");
        redPlayer.placeBuilder(gameBoard,redPlayer.builder1,2,2);//scanner.nextInt(),scanner.nextInt());
        gameInstance.printBoardState(gameBoard);

        System.out.println("Enter X and Y co-ordinates for Red piece 2");
        redPlayer.placeBuilder(gameBoard,redPlayer.builder2,1,2);//scanner.nextInt(),scanner.nextInt());

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


                //In AI version blue plays first.
               boolean ableToMove=false;
                //Initial human playable game with no building
                System.out.println(bluePlayer.getName() + "  make your move, enter b1 or b2 for the desired builder, " +
                        "\nthen followed by the compass point move, then the compass point for the building location." +
                        "\nEach command must be submitted individually with enter.");
                System.out.println("Builder 1: ");
                blueBuilder1.printBuilderStats();
                System.out.println("Builder 2: ");
                blueBuilder2.printBuilderStats();

                String input = scanner.nextLine();
                if (input.equals("b1")){
                    input = scanner.nextLine();
                    ableToMove = bluePlayer.moveBuilder(gameBoard,bluePlayer.builder1,moveHashMap.get(input.toUpperCase()));
                    input = scanner.nextLine();
                    bluePlayer.buildLevel(gameBoard,bluePlayer.builder1,moveHashMap.get(input.toUpperCase()));
                    gameInstance.print();


                }else if (input.equals("b2")){
                    input = scanner.nextLine();
                    ableToMove = bluePlayer.moveBuilder(gameBoard,bluePlayer.builder2,moveHashMap.get(input.toUpperCase()));
                    input = scanner.nextLine();
                    bluePlayer.buildLevel(gameBoard,bluePlayer.builder2,moveHashMap.get(input.toUpperCase()));
                    gameInstance.print();
                }
                moveCount+=1;

                System.out.println("Blue player:" + bluePlayer.getName()+ " moved to: ");
                gameInstance.printBoardState(gameBoard);


                //Check if move won the game for blue
                if (gameInstance.gameOver(gameBoard, redPlayer)) {
                    bluewins +=1;
                    System.out.println("Blue wins the game!");
                    break;
                }



             //------------------------ MiniMax Red----------------------//
                //Final game implementation using the best game AI Created during the project
                long startTime = System.currentTimeMillis();
                int stateValue = gameInstance.miniMaxRed(gameBoard,0,redPlayer);
                long endTime = System.currentTimeMillis();

                gameBoard = gameInstance.getBoard();
                System.out.println();
                System.out.println("Time taken to compute: " + (endTime-startTime));
                moveCount+=1;



                System.out.println("Red Player moved to: ");
                gameInstance.printBoardState(gameBoard);

                if (gameInstance.gameOver(gameBoard,bluePlayer)){
                    System.out.println("Red player Wins ");
                    redwins +=1;
                    break;
                }



            }catch (InvalidMoveException invalidMoveException){
                invalidMoveException.printStackTrace();
            }
        }



//        //----------------Blue MiniMax -------------------------//
//        long blueStart = System.currentTimeMillis();
//        //long beforeUsedMem = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
//        int blueState = gameInstance.miniMaxBlue(gameBoard,0,bluePlayer,moveCount);
//        long blueEnd = System.currentTimeMillis();
//        System.out.println("Blue Time taken to compute: " + (blueEnd-blueStart));
//        moveCount +=1;
//        gameBoard = gameInstance.getBoard();
//        gameInstance.print();

        //--------------------------Strategy 2 Climb as high as possible- Greedy-----------//
//                    gameBoard = gameInstance.greedyMove(gameBoard,bluePlayer);





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
