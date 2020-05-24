import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    final static String startPath = "src/Files/start.txt";
    final static String finishPath = "src/Files/finish.txt";
    final static String resultPath = "src/Files/result.txt";
    final static String startPathBin = "src/Files/start.dat";
    final static String finishPathBin = "src/Files/finish.dat";
    final static String resultPathBin = "src/Files/result.dat";
    static Data data;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            data = new Data();

            System.out.println("1 - binary");
            System.out.println("2 - text");
            System.out.println("-----------");
            int inp = sc.nextInt();
            if (inp == 1) {
                if (data.readFile(startPathBin) | data.readFile(finishPathBin));
                else {
                    System.out.println("Somethong went wrong!");
                    continue;
                }
            } else if (inp == 2) {
                if (data.readFile(startPath) | data.readFile(finishPath));
                else {
                    System.out.println("Somethong went wrong!");
                    continue;
                }
            }

            ArrayList <Racer> racers = data.getRacers();
            compare(racers);
            data.setRacers(racers);

            System.out.println("1 - write to the terminal");
            System.out.println("2 - write to the binary file");
            System.out.println("3 - write to the text file");
            System.out.println("-------------------------");
            inp = sc.nextInt();
            if (inp == 1) {
                print(racers);
                continue;
            } else if (inp == 2) {
                data.save(resultPathBin);
                continue;
            } else if (inp == 3) {
                data.save(resultPath);
                continue;
            }

        }

    }

    private static void compare (ArrayList <Racer> array) {

        for (int i = 0; i < array.size(); i++) {
            for (int j = 1; j < array.size(); j++) {
                if (array.get(j).isFaster(array.get(j-1))) {
                    Racer tmp = array.get(j);
                    array.set(j, array.get(j - 1));
                    array.set(j - 1, tmp);
                }
            }
        }

    }

    private static void print(ArrayList <Racer> racers) {

        System.out.println("Name                 Number Start Time Finish Time");

        for (int i = 0; i < racers.size(); i++) {
            System.out.print(racers.get(i).getFullName());
            int c = 20 - racers.get(i).getFullName().length();
            if (c < 0) c = 0;
            for (int j = 0; j < c; j++) System.out.print(" ");
            System.out.print(" ");

            System.out.print(racers.get(i).getNumber());
            c = 6 - ("" + racers.get(i).getNumber()).length();
            for (int j = 0; j < c; j++) System.out.print(" ");
            System.out.print(" ");

            System.out.print(racers.get(i).getStartTime());
            c = 10 - racers.get(i).getStartTime().length();
            for (int j = 0; j < c; j++) System.out.print(" ");
            System.out.print(" ");

            System.out.print(racers.get(i).getFinishTime());

            System.out.println();
        }

    }

}
