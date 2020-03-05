
public class SantoriniGame {



    private static Player bluePlayer;
    private static Player redPlayer;
    Tile[][][] board = new Tile[4][2][2];

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
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
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
            for (int y = 1; y >-1 ; y--) {
                for (int x = 0; x < 2; x++) {
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

    public static boolean gameOver(Player activePlayer, Player otherPlayer){
        if (activePlayer.unableToMove() || otherPlayer.wonByLevel3()){
            return true;
        }else return false;
    }

    public void miniMax(){

    }

    public boolean isEndState(){
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    if (board[3][x][y].isOccupiedWithRedBuilder() || board[3][x][y].isOccupiedWithBlueBuilder()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return false;
    }

    public int evaluationFunction(){

    }



}
