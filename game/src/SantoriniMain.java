import java.util.*;

public class SantoriniMain {
    public static void main(String[] args) {
        // Initialise players with a single builder
        String playerOne = "Jack", playerTwo = "Brandon", pOneColour = "Blue", pTwoColour = "Red";
        Player bluePlayer = new Player(playerOne,pOneColour,new BuilderPiece());
        Player redPlayer = new Player(playerTwo, pTwoColour,new BuilderPiece());

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
        gameInstance.print();
        System.out.println("Blank board printed");

        Tile[][][] gameBoard = gameInstance.getBoard();


        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder1,1,1);
        redPlayer.placeBuilder(gameBoard,redPlayer.builder1,0,0);
        gameInstance.print();
        System.out.println("initial placement printed");
        System.out.println("Welcome to Santorini, to make moves Enter the compass coordinates of the move" +
                "you would like to make, ie N, NE etc.");
//        //try move blue player E.
//        try {
//            bluePlayer.moveBuilder(gameBoard,bluePlayer.builder1,moveHashMap.get("SW"));
//        }catch ( InvalidMoveException invalidMoveException){
//            invalidMoveException.printStackTrace();
//        }
//        gameInstance.print();

        Scanner scanner = new Scanner(System.in);
        Player activePlayer = bluePlayer;
        Player otherPlayer = redPlayer;
        Player tmp = null;
        while (!gameInstance.gameOver(activePlayer,otherPlayer)){
            try {

                //Initial human playable game with no building
                System.out.println(activePlayer.getName() + " Player make your move");
                String input = scanner.nextLine();
                activePlayer.moveBuilder(gameBoard,activePlayer.builder1,moveHashMap.get(input.toUpperCase()));
                gameInstance.print();
                tmp = otherPlayer;
                otherPlayer = activePlayer;
                activePlayer = tmp;


            }catch (InvalidMoveException invalidMoveException){
                invalidMoveException.printStackTrace();
            }
        }
    }
}
