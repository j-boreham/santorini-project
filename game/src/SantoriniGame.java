import static java.lang.Boolean.TRUE;

public class SantoriniGame {


    private Board santoriniBoard;
    private Player bluePlayer;
    private Player redPlayer;

    public SantoriniGame(Board santoriniBoard, Player bluePlayer, Player redPlayer) {
        santoriniBoard = new Board();
        this.bluePlayer = bluePlayer;
        this.redPlayer = redPlayer;
    }

    public static boolean checkLoss(Player playerA, Player playerB){
        if (playerA.isAbleToMove() && !playerB.wonByLevel3())
    }

    

    public static void playSantorini(){
        while (checkLoss(bluePlayer, redPlayer) =! true ){

        }
    }

}
