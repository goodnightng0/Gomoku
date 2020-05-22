import java.util.ArrayList;

public class Design {
    private int[][] boardMatrix; // 0: Empty 1: White 2: Black

    public Design(int length) {
        boardMatrix = new int[length][length];
    }
    //Doesn't actually add the stones on board
    public void addStoneforAI(int posX, int posY, boolean black) {
        boardMatrix[posX][posY] = black ? 2 : 1;
    }
    //Add the stones on the board
    public boolean addStone(int posX, int posY, boolean black) {
        if(boardMatrix[posX][posY] != 0) return false;
        boardMatrix[posX][posY] = black ? 2 : 1;
        showBoard();//draws the board for us to see
        return true;
    }
    //Copy matrix to use in AI calculations
    public Design(Design design) {
        int[][] matrixToCopy = design.getBoardMatrix();
        boardMatrix = new int[matrixToCopy.length][matrixToCopy.length];
        for(int i=0;i<matrixToCopy.length; i++) {
            for(int j=0; j<matrixToCopy.length; j++) {
                boardMatrix[i][j] = matrixToCopy[i][j];
            }
        }
    }
    //returns list of possible moves
    public ArrayList<int[]> actions() {
        ArrayList<int[]> moveList = new ArrayList<int[]>();
        int boardSize = boardMatrix.length;
        for(int i=0; i<boardSize; i++) {
            for(int j=0; j<boardSize; j++) {
                if(boardMatrix[i][j] > 0) continue;
                if(i > 0) {
                    if(j > 0) {
                        if(boardMatrix[i-1][j-1] > 0 ||
                                boardMatrix[i][j-1] > 0) {
                            int[] move = {i,j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if(j < boardSize-1) {
                        if(boardMatrix[i-1][j+1] > 0 ||
                                boardMatrix[i][j+1] > 0) {
                            int[] move = {i,j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if(boardMatrix[i-1][j] > 0) {
                        int[] move = {i,j};
                        moveList.add(move);
                        continue;
                    }
                }
                if( i < boardSize-1) {
                    if(j > 0) {
                        if(boardMatrix[i+1][j-1] > 0 ||
                                boardMatrix[i][j-1] > 0) {
                            int[] move = {i,j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if(j < boardSize-1) {
                        if(boardMatrix[i+1][j+1] > 0 ||
                                boardMatrix[i][j+1] > 0) {
                            int[] move = {i,j};
                            moveList.add(move);
                            continue;
                        }
                    }
                    if(boardMatrix[i+1][j] > 0) {
                        int[] move = {i,j};
                        moveList.add(move);
                        continue;
                    }
                }
            }
        }
        return moveList;
    }
    public int[][] getBoardMatrix() {
        return boardMatrix;
    }
    //Draw the board
    public void showBoard(){
        for(int i=0;i<19;i++){
            for(int j=0;j<19;j++){
                if(boardMatrix[i][j]==0){
                    System.out.print('.');
                }
                else if(boardMatrix[i][j]==1)
                    System.out.print('o');
                else
                    System.out.print('x');
            }
            System.out.println();
        }
    }
}