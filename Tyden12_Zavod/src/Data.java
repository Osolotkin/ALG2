import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

    ArrayList <Racer> racers;

    public Data() {
        racers = new ArrayList<Racer>();
    }

    private String toBinary (String string) {

        byte[] bytes = string.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }

        return binary.toString();

    }

    public boolean save(String path) {

        File file = new File(path);

        if (file.getName().split("\\.")[1].equals("txt")) {

            FileWriter wr;
            try {
                wr = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong!");
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created at given path: " + path);
                        wr = new FileWriter(file);
                    } else {
                        System.out.println("Something went wrong!");
                        return false;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }

            }
            PrintWriter prWr = new PrintWriter(wr);

            for (int i = 0; i < racers.size(); i++) {
                prWr.println(racers.get(i).getFullName() + " " + racers.get(i).getNumber() + " " + racers.get(i).getStartTime() + " " + racers.get(i).getFinishTime());
            }
            prWr.close();
            return true;

        } else {

            OutputStream outStream;

            try {
                outStream = new FileOutputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Something went wrong!");
                try {
                    if (file.createNewFile()) {
                        System.out.println("File created at given path: " + path);
                        outStream = new FileOutputStream(file);
                    } else {
                        System.out.println("Something went wrong!");
                        return false;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < racers.size(); i++) {
                sb.append(racers.get(i).getFullName() + " " + racers.get(i).getNumber() + " " + racers.get(i).getStartTime() + " " + racers.get(i).getFinishTime() + "\n");
            }
            byte [] bytes = sb.toString().getBytes();

            try {
                outStream.write(bytes);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return true;

        }

    }

    public boolean readFile(String path) {

        File file = new File(path);
        if (file.getName().replaceFirst("[.][^.]+$", "").equals("start")) {

            if (file.getName().split("\\.")[1].equals("txt")) {

                FileReader rd;

                try {
                    rd = new FileReader(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("Could not to find any file at given path");
                    return false;
                }

                BufferedReader bfRd = new BufferedReader(rd);
                String line;
                try {
                    while ((line = bfRd.readLine()) != null) {
                        line = line.replace(":", " ");
                        String[] par = line.trim().split("\\s+");
                        Racer racer = new Racer(par[0], par[1], par[2], par[3], par[4], par[5]);
                        racers.add(racer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong!");
                    return false;
                }

            } else {

                InputStream inpStream;
                byte [] bytes;

                try {
                    inpStream = new FileInputStream(file);
                    bytes = inpStream.readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong!");
                    return false;
                }

                int last = 0;

                while (last <  bytes.length) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = last; i < bytes.length; i++) {
                        last = i + 1;
                        if ('\n' == bytes[i]) break;
                        sb.append(Character.toChars(bytes[i]));
                    }
                    String line = sb.toString().trim().replace(":", " ");
                    String par[] = line.split(" ");
                    Racer racer = new Racer(par[0], par[1], par[2], par[3], par[4], par[5]);
                    racers.add(racer);
                }

            }

        } else {

            if (file.getName().split("\\.")[1].equals("txt")) {

                Scanner sc;
                try {
                    sc = new Scanner(file);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong!");
                    return false;
                }

                while (sc.hasNext()) {
                    String[] time = new String[3];
                    String number = "";
                    String token;
                    int i = 0;
                    while (i < 5) {
                        token = sc.next();
                        if (i == 0) number = token.trim();
                        else if (token.indexOf(":") >= 0) {
                            time = token.trim().split(":");
                            break;
                        } else time[i - 1] = token;
                        i++;
                    }

                    for (int j = 0; j < racers.size(); j++) {
                        if (Integer.parseInt(number) == racers.get(j).getNumber()) {
                            racers.get(j).setFinishTime(time[0], time[1], time[2]);
                        }
                    }

                }

            } else {

                InputStream inpStream;
                byte [] bytes;

                try {
                    inpStream = new FileInputStream(file);
                    bytes = inpStream.readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Something went wrong!");
                    return false;
                }

                int last = 0;

                while (last <  bytes.length) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = last; i < bytes.length; i++) {
                        last = i + 1;
                        if ('\n' == bytes[i]) break;
                        sb.append(Character.toChars(bytes[i]));
                    }
                    String line = sb.toString().trim().replace(":", " ");
                    String par[] = line.split(" ");
                    for (int j = 0; j < racers.size(); j++) {
                        if (Integer.parseInt(par[0]) == racers.get(j).getNumber()) {
                            racers.get(j).setFinishTime(par[1], par[2], par[3]);
                        }
                    }
                }

            }

        }

        return true;

    }

    public ArrayList<Racer> getRacers() {
        return (ArrayList <Racer>) racers.clone();
    }

    public void setRacers(ArrayList<Racer> racers) {
        this.racers = (ArrayList <Racer>) racers.clone();
    }

}
