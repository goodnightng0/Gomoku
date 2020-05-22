import java.util.Scanner;

public class Play {

    private Design design;
    private boolean whoseTurn = true;
    private boolean first=true;//true: black false: white
    private int maxtime;
    private AlphaBeta ab;
    private int winner; // 0: No winner now 1: AI Wins, 2: Human Wins
    private boolean gameFinished = false;
    private boolean aiStarts = true; // AI starts the game
    private boolean aiTurn;


    public Play(Design design) {
        ab = new AlphaBeta(design);
        winner = 0;
    }
    public void setAIStarts(boolean aiStarts) {
        this.aiStarts = aiStarts;
    }
    public void setTime(int maxtime){
        this.maxtime=maxtime;
    }
    public void start(Design design) {
        aiTurn=aiStarts;
        this.design = design;

        if (aiStarts)
            realMove(10, 10, false);
        if (whoseTurn) {
            whoseTurn = false;
        }
        Scanner myScanner = new Scanner(System.in);
        while (!gameFinished) {
            if(first) {
                System.out.println("Please enter your first move.(range: A-S, 1-19) e.g. B 2; K 6; C 5");
                first=false;
            }
            else
                System.out.println("Please enter your next move.(range: A-S, 1-19) e.g. B 2; K 6; C 5");
            String move = myScanner.nextLine();
            int posY = Integer.parseInt(move.substring(2, move.length()))-1;
            int posX = move.charAt(0) - 'A';

            if (!realMove(posX, posY, true)) {
                whoseTurn = true;
                return;
            }

            winner = checkWinner();
            if (winner == 2) {
                System.out.println("Congratulations you WON!");
                gameFinished = true;
                return;
            }

            // We calculate the next move for AI by using alphabeta search.
            int[] aiMove = ab.calculateNextMove(maxtime);
            aiTurn=true;

            if (aiMove == null) {
                System.out.println("Oh no. No possible moves left. Play is Over.");
                gameFinished = true;
                return;
            }

            realMove(aiMove[0], aiMove[1], false);

            if(aiTurn)
                System.out.println("<Score up to now>\nBlack: " + AlphaBeta.calculateScore(design, true, true) + " White: " + AlphaBeta.calculateScore(design, false, true));
            else
                System.out.println("<Score up to now>\nWhite: " + AlphaBeta.calculateScore(design, true, true) + " Black: " + AlphaBeta.calculateScore(design, false, true));

            winner = checkWinner();
            if (winner == 1) {
                System.out.println("AI  wins! Seems like you lost to a computer :(");
                gameFinished = true;
                return;
            }

            if (design.actions().size() == 0) {
                System.out.println("Oh no. No possible moves left. Play is Over.");
                gameFinished = true;
                return;
            }
            whoseTurn = true;
            aiTurn=false;
        }
    }
    private int checkWinner () {
        if (AlphaBeta.calculateScore(design, true, false) >= 100000000) return 2;
        if (AlphaBeta.calculateScore(design, false, true) >= 100000000) return 1;
        return 0;
    }

    private boolean realMove ( int posX, int posY, boolean black){
        return design.addStone(posX, posY, black);
    }
}