import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner myScanner=new Scanner(System.in);
        // Created the board for my gomoku.
        Design design = new Design(19);
        // Create the Play for our game.
        final Play play = new Play(design);

        System.out.println("Black will make the first move. Enter 1 to choose the black stones and 2 to choose the white stone:");
        int temp=myScanner.nextInt();
        boolean aiStart=(temp==1)?false:true;
        System.out.println("OK! You will play as \'x\' from now on! Be careful for \'o\'");
        System.out.println("Enter the time limit(second) for Iterative Deepening Alpha Beta Search: ");
        int maxtime=myScanner.nextInt();
        play.setTime(maxtime);

        //set our game
        play.setAIStarts(aiStart);
        play.start(design);
    }
}