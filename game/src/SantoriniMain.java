public class SantoriniMain {
    public static void main(String[] args) {

        String playerOne = "Jack", playerTwo = "Brandon", pOneColour = "Blue", pTwoColour = "Red";
        Player bluePlayer = new Player(playerOne,pOneColour);
        Player redPlayer = new Player(playerTwo, pTwoColour);
        Board gameBoard =  new Board();
        SantoriniGame gameInstance = new SantoriniGame(gameBoard,bluePlayer,redPlayer);
        gameInstance.printBoard();

    }
}
