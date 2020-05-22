import java.util.ArrayList;

public class AlphaBeta {

    public static int count = 0;
    private Design design;

    public AlphaBeta(Design design) {
        this.design = design;
    }
    public static double scoreEvaluate(Design design, boolean blacksTurn) {
        count++;

        double first = calculateScore(design, true, blacksTurn);
        double second = calculateScore(design, false, blacksTurn);
        if(first == 0)
            first = 1.0;
        return second / first;

    }
    //Score in horizontal/vertical/diagnoal directions.
    public static int calculateScore(Design design, boolean forBlack, boolean blacksTurn) {
        int[][] boardMatrix = design.getBoardMatrix();
        return horizontal(boardMatrix, forBlack, blacksTurn) + vertical(boardMatrix, forBlack, blacksTurn) + diagonal(boardMatrix, forBlack, blacksTurn);
    }
    //Optimal choice for next AI move
    public int[] calculateNextMove(int maxtime) {
        int[] move = new int[2];
        int depth=1;
        long startTime = System.currentTimeMillis();
        Object[] bestMove = searchForMove(design);
        if(bestMove != null ) {
            move[0] = (Integer)(bestMove[1]);
            move[1] = (Integer)(bestMove[2]);

        } else {
            bestMove = alphaBetaSearch(depth++, design, true, -1.0, 100000000,startTime,maxtime);
            while(maxtime<=((double)(System.currentTimeMillis()-startTime)/(double)1000)){
                Object[] whileMove=alphaBetaSearch(depth++,design,true,-1.0,100000000,startTime,maxtime);
                if(whileMove[1]!=null&&(Integer)whileMove[0]>(Integer)bestMove[0]){
                    bestMove[0]=whileMove[0];
                    bestMove[1]=whileMove[1];
                }
            }
            if(bestMove[1] == null) {
                move = null;
            } else {
                move[0] = (Integer)(bestMove[1]);
                move[1] = (Integer)(bestMove[2]);
            }
        }
        System.out.println("Time used: " + (System.currentTimeMillis() - startTime) + "(ms)"+"/" + count+" Cases searched for");
        count=0;
        return move;
    }

    //alpha for max AI move, beta for min human move
    //The AlphaBeta search that we use in IDS
    private static Object[] alphaBetaSearch(int depth, Design design, boolean max, double alpha, double beta, long startTime, int maxtime) {
        if(depth == 0||maxtime<=((double)(System.currentTimeMillis()-startTime)/(double)1000)) {
            Object[] x = {scoreEvaluate(design, !max), null, null};
            return x;
        }
        ArrayList<int[]> nonzero = design.actions();
        if(nonzero.size() == 0) {
            Object[] x = {scoreEvaluate(design, !max), null, null};
            return x;
        }
        Object[] bestMove = new Object[3];

        if(max) {
            bestMove[0] = -1.0;
            for(int[] move : nonzero) {
                Design tempDesign = new Design(design);
                tempDesign.addStoneforAI(move[0], move[1], false);
                Object[] tempMove = alphaBetaSearch(depth-1, tempDesign, !max, alpha, beta,startTime,maxtime);

                if((Double)(tempMove[0]) > alpha) {
                    alpha = (Double)(tempMove[0]);
                }
                if((Double)(tempMove[0]) >= beta) {
                    return tempMove;
                }
                if((Double)tempMove[0] > (Double)bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }
        }
        else {
            bestMove[0] = 100000000.0;
            bestMove[1] = nonzero.get(0)[0];
            bestMove[2] = nonzero.get(0)[1];
            for(int[] move : nonzero) {
                Design tempDesign = new Design(design);
                tempDesign.addStoneforAI(move[0], move[1], true);

                Object[] tempMove = alphaBetaSearch(depth-1, tempDesign, !max, alpha, beta,startTime,maxtime);

                if(((Double)tempMove[0]) < beta) {
                    beta = (Double)(tempMove[0]);
                }
                if((Double)(tempMove[0]) <= alpha) {
                    return tempMove;
                }
                if((Double)tempMove[0] < (Double)bestMove[0]) {
                    bestMove = tempMove;
                    bestMove[1] = move[0];
                    bestMove[2] = move[1];
                }
            }
        }
        return bestMove;
    }
    //If the score exceeds one point, it will be immediately returned
    private static Object[] searchForMove(Design design) {
        ArrayList<int[]> nonzero = design.actions();
        Object[] finalMove = new Object[3];

        for(int[] move : nonzero) {
            count++;
            Design tempDesign = new Design(design);
            tempDesign.addStoneforAI(move[0], move[1], false);

            if(calculateScore(tempDesign,false,false) >= 100000000) {
                finalMove[1] = move[0];
                finalMove[2] = move[1];
                return finalMove;
            }
        }
        return null;
    }
    //Our heuristic evaluation function
    public static  int getScores(int count, int blocks, boolean currentTurn) {
        final int highProbability = 1000000;
        if(blocks == 2 && count < 5) return 0;
        switch(count) {
            case 1: {
                return 1;
            }
            case 2: {
                if(blocks == 0) {
                    if(currentTurn) return 10;
                    else return 7;
                }
                else {
                    return 5;
                }
            }
            case 3: {
                if(blocks == 0) {
                    if(currentTurn) return 50000;
                    else return 300;
                }
                else {
                    if(currentTurn) return 15;
                    else return 7;
                }
            }
            case 4: {
                if(currentTurn) return highProbability;
                else {
                    if(blocks == 0) return highProbability/4;
                    else return 250;
                }
            }
            case 5: {
                return 100000000;
            }
        }
        return 100000000*2;
    }

    //Examine scores in every way
    public static int horizontal(int[][] boardMatrix, boolean black, boolean whoseTurn ) {
        int fiveInaRow = 0;
        int blocks = 2;
        int score = 0;

        for(int i=0; i<boardMatrix.length; i++) {
            for(int j=0; j<boardMatrix[0].length; j++) {
                if(boardMatrix[i][j] == (black ? 2 : 1)) {
                    fiveInaRow++;
                }
                else if(boardMatrix[i][j] == 0) {
                    if(fiveInaRow > 0) {
                        blocks--;
                        score += getScores(fiveInaRow, blocks, black == whoseTurn);
                        fiveInaRow = 0;
                        blocks = 1;
                    }
                    else {
                        blocks = 1;
                    }
                }
                else if(fiveInaRow > 0) {
                    score += getScores(fiveInaRow, blocks, black == whoseTurn);
                    fiveInaRow = 0;
                    blocks = 2;
                }
                else {
                    blocks = 2;
                }
            }
            if(fiveInaRow > 0) {
                score += getScores(fiveInaRow, blocks, black == whoseTurn);
            }
            fiveInaRow = 0;
            blocks = 2;
        }
        return score;
    }

    public static  int vertical(int[][] boardMatrix, boolean black, boolean whoseTurn ) {

        int fiveInaRow = 0;
        int blocks = 2;
        int score = 0;

        for(int j=0; j<boardMatrix[0].length; j++) {
            for(int i=0; i<boardMatrix.length; i++) {
                if(boardMatrix[i][j] == (black ? 2 : 1)) {
                    fiveInaRow++;
                }
                else if(boardMatrix[i][j] == 0) {
                    if(fiveInaRow > 0) {
                        blocks--;
                        score += getScores(fiveInaRow, blocks, black == whoseTurn);
                        fiveInaRow = 0;
                        blocks = 1;
                    }
                    else {
                        blocks = 1;
                    }
                }
                else if(fiveInaRow > 0) {
                    score += getScores(fiveInaRow, blocks, black == whoseTurn);
                    fiveInaRow = 0;
                    blocks = 2;
                }
                else {
                    blocks = 2;
                }
            }
            if(fiveInaRow> 0) {
                score += getScores(fiveInaRow, blocks, black == whoseTurn);
            }
            fiveInaRow = 0;
            blocks = 2;
        }
        return score;
    }
    public static int diagonal(int[][] boardMatrix, boolean black, boolean whoseTurn ) {

        int fiveInaRow = 0;
        int blocks = 2;
        int score = 0;
        for (int k = 0; k <= 2 * (boardMatrix.length - 1); k++) {
            int startAt = Math.max(0, k - boardMatrix.length + 1);
            int endAt = Math.min(boardMatrix.length - 1, k);
            for (int i = startAt; i <= endAt; ++i) {
                int j = k - i;

                if(boardMatrix[i][j] == (black ? 2 : 1)) {
                    fiveInaRow++;
                }
                else if(boardMatrix[i][j] == 0) {
                    if(fiveInaRow > 0) {
                        blocks--;
                        score += getScores(fiveInaRow, blocks, black == whoseTurn);
                        fiveInaRow = 0;
                        blocks = 1;
                    }
                    else {
                        blocks = 1;
                    }
                }
                else if(fiveInaRow > 0) {
                    score += getScores(fiveInaRow, blocks, black == whoseTurn);
                    fiveInaRow = 0;
                    blocks = 2;
                }
                else {
                    blocks = 2;
                }
            }
            if(fiveInaRow > 0) {
                score += getScores(fiveInaRow, blocks, black == whoseTurn);
            }
            fiveInaRow = 0;
            blocks = 2;
        }
        for (int k = 1-boardMatrix.length; k < boardMatrix.length; k++) {
            int startAt = Math.max(0, k);
            int endAt = Math.min(boardMatrix.length + k - 1, boardMatrix.length-1);
            for (int i = startAt; i <= endAt; ++i) {
                int j = i - k;

                if(boardMatrix[i][j] == (black ? 2 : 1)) {
                    fiveInaRow++;
                }
                else if(boardMatrix[i][j] == 0) {
                    if(fiveInaRow > 0) {
                        blocks--;
                        score += getScores(fiveInaRow, blocks, black == whoseTurn);
                        fiveInaRow = 0;
                        blocks = 1;
                    }
                    else {
                        blocks = 1;
                    }
                }
                else if(fiveInaRow > 0) {
                    score += getScores(fiveInaRow, blocks, black == whoseTurn);
                    fiveInaRow = 0;
                    blocks = 2;
                }
                else {
                    blocks = 2;
                }
            }
            if(fiveInaRow > 0) {
                score += getScores(fiveInaRow, blocks, black == whoseTurn);
            }
            fiveInaRow = 0;
            blocks = 2;
        }
        return score;
    }
}