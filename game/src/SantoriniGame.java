
public class SantoriniGame {



    private static Player bluePlayer;
    private static Player redPlayer;
    Tile[][][] board = new Tile[3][2][2];

    public SantoriniGame( Player bluePlayer, Player redPlayer) {
        this.bluePlayer = bluePlayer;
        this.redPlayer = redPlayer;
    }

    public Tile[][][]getBoard(){
        return board;
    }

    public void initialiseBoard(){
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    board[z][x][y] = new Tile(false);
                }
            }
        }
    }



    public static boolean checkLoss(Player playerA, Player playerB){
        if (playerA.isAbleToMove() && !playerB.wonByLevel3()){
            return false;
        }else return true;
    }



    public static void playSantorini(){
        //Blue player Always plays first, this will be randomly assigned.
        //bluePlayer.placeBuilder();
        //bluePlayer.placeBuilder();
        while (checkLoss(bluePlayer, redPlayer) != true ){
            break;
        }
    }

}
