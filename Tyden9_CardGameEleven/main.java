import java.util.Scanner;

public class main {

    private static final String green = "\u001B[32m";
    private static Scanner inp;

    public static void main(String [] args) {

        inp = new Scanner(System.in);

        Game game = new Game();
        while(true) {
            game.play();
            System.out.println(green + "to play again input 1");
            if (inp.nextInt() != 1) break;
        }
    }

}
