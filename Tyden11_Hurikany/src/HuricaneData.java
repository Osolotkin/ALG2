import javax.imageio.IIOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HuricaneData {

    private List<Integer> year;
    private List<String> month;
    private List<Integer> pressures;
    private List<Integer> windSpeed;
    private List<String> name;

    public HuricaneData() {
        year = new ArrayList<>();
        month = new ArrayList<>();
        pressures = new ArrayList<>();
        windSpeed = new ArrayList<>();
        name = new ArrayList<>();

    }

    public void readFile(String path) {
        File file = new File(path);
        Scanner inp;
        try {
            inp = new Scanner(file);
            while (inp.hasNextLine()) {
                inp.nextLine();
                String [] tmp = inp.nextLine().trim().split("\\s+");
                year.add(Integer.parseInt(tmp[0]));
                month.add(tmp[1]);
                pressures.add(Integer.parseInt(tmp[2]));
                windSpeed.add(Integer.parseInt(tmp[3]));
                name.add(tmp[4]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found!");
        }

    }

    public void writeFile(String path) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            FileWriter wr = new FileWriter(path);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < getYear().length; i++) {
                sb.append(year.get(i) + " ").append(month.get(i) + " ").append(pressures.get(i) + " ").append(windSpeed.get(i) + " ").append(name.get(i));
                wr.write(sb.toString() + "“\n”");
            }
            wr.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int [] getYear() {
        return year.stream().mapToInt(i -> i).toArray().clone();
    }

    public String [] getMonth() {
        return month.stream().toArray(String[]::new).clone();
    }

    public int [] getPressures() {
        return pressures.stream().mapToInt(i -> i).toArray().clone();
    }

    public int [] getWindSpeed() {
        return windSpeed.stream().mapToInt(i -> i).toArray().clone();
    }

    public String [] getName() {
        return name.stream().toArray(String[]::new).clone();
    }

    public void setYear(int [] year) {
        List tmp = new ArrayList<Integer>();
        for (int i : year) tmp.add(i);
        this.year = tmp;
    }

    public void setMonth(String [] month) {
        this.month = new ArrayList<String>(Arrays.asList(month));
    }

    public void setPressures(int [] pressures) {
        List tmp = new ArrayList<Integer>();
        for (int i : pressures) tmp.add(i);
        this.pressures = tmp;
    }

    public void setWindSpeed(int [] windSpeed) {
        List tmp = new ArrayList<Integer>();
        for (int i : windSpeed) tmp.add(i);
        this.windSpeed = tmp;
    }

    public void setName(String [] name) {
        this.name = new ArrayList<String>(Arrays.asList(name));
    }

}
