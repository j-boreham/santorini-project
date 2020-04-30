public class MinimaxResult {

    private int score;
    private Tile[][][] bestMove;


    public MinimaxResult(int score, Tile[][][] bestMove ){
        this.score = score;
        this.bestMove = bestMove;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Tile[][][] getBestMove() {
        return bestMove;
    }

    public void setBestMove(Tile[][][] bestMove) {
        this.bestMove = bestMove;
    }
}


