import java.util.Scanner;

public class Input {

    private Scanner inp;

    Input() {
        inp = new Scanner(System.in);
    }

    public int getInput() {
        return inp.nextInt() - 1;
    }

    public int [] getTwoInputs() {
        return new int [] {inp.nextInt() - 1, inp.nextInt() - 1}.clone();
    }

}
