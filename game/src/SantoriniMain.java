import java.util.Scanner;

public class SantoriniMain {
    public static void main(String[] args) {
        // Initialise players with a single builder
        String playerOne = "Jack", playerTwo = "Brandon", pOneColour = "Blue", pTwoColour = "Red";
        Player bluePlayer = new Player(playerOne,pOneColour,new BuilderPiece());
        Player redPlayer = new Player(playerTwo, pTwoColour,new BuilderPiece());

        //create an instance of the game
        SantoriniGame gameInstance = new SantoriniGame(bluePlayer,redPlayer);
        gameInstance.initialiseBoard();
        gameInstance.print();
        System.out.println("Blank board printed");

        Tile[][][] gameBoard = gameInstance.getBoard();

        Scanner scanner = new Scanner(System.in);

        bluePlayer.placeBuilder(gameBoard,bluePlayer.builder1,0,0);
        //redPlayer.placeBuilder(gameBoard,redPlayer.builder1,1,0);

        gameInstance.print();
        System.out.println("initial placement printed");

        //try move blue player E.
        try {
            bluePlayer.moveBuilder(gameBoard,bluePlayer.builder1,new Move(1,1));
        }catch ( InvalidMoveException invalidMoveException){
            invalidMoveException.printStackTrace();
        }
        gameInstance.print();

//        while (true){
//            System.out.println("R");
//        }
    }
}
