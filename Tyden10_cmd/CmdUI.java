import java.util.Scanner;

public class CmdUI {

    public static Scanner inp = new Scanner((System.in));

    public static void main(String[] args) {
        CmdInterface cmd = new CmdEditor();

        String line;
        while (cmd.isRunning()) {
            System.out.println(cmd.getActualDir() + "$");
            line = inp.nextLine();
            String tmp = cmd.parseAndExecute(line);
            if (tmp.contains("C:" + "\\\\")) System.out.println(cmd.parseAndExecute(line));
        }
    }

}
