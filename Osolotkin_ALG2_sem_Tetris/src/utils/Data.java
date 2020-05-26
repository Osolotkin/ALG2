package utils;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Read form file, write to file, stores and manage data.
 * @author Maxim Osolotkin
 */
public class Data {

    private boolean snd;    //sound
    private boolean msc;    //music
    private boolean cld;    //colored
    private boolean [] ach; //achievements
    private String [] rec;  //records
    private int wd;         //width
    private int hg;         //height
    private String name;    //player name
    private final String dataPath = "src/Files/data.txt";
    private final String recordPath = "src/Files/records.txt";
    private PrintWriter wr;

    /**
     * Creates Data
     * @param achLength detremine length of ach[], basicly determine count of achievement
     * @param recLength detremine length of rec[], basicly determine count of records
     */
    public Data(int achLength, int recLength) {

        ach = new boolean [achLength];
        rec = new String [recLength];

    }

    /**
     * Writes to file data.txt or records.txt in directory src/Files, depends on parameter. If such file does not exist
     * attempts to create new one.
     * @param var determine which file would be written.
     */
    public void writeFile(String var) {

        StringBuilder sb = new StringBuilder();
        PrintWriter clearMe;

        if (var == "data") {
            try {
                clearMe = new PrintWriter(dataPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found! Attempting to create new one!");
                File fl = new File(dataPath);
                try {
                    System.out.println("File created: " + fl.getName());
                    fl.createNewFile();
                    clearMe = new PrintWriter(fl);
                } catch (IOException ex) {
                    e.printStackTrace();
                    System.out.println("Can not create file, sorry!");
                    return;
                }
            }
            clearMe.close();
            try {
                wr = new PrintWriter(new FileWriter(dataPath, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append("sound ").append(snd).append("\n");
            sb.append("music ").append(msc).append("\n");
            sb.append("colored ").append(cld).append("\n");
            sb.append("width ").append(wd).append("\n");
            sb.append("height ").append(hg).append("\n");
            sb.append("name ").append(name).append("\n");
            for (int i = 1; i <= ach.length; i++) {
                sb.append("achvmnt").append(i).append(" ").append(ach[i - 1]).append("\n");
            }
            wr.print(sb);
        } else if (var == "records") {
            try {
                clearMe = new PrintWriter(recordPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found! Attempting to create new one!");
                File fl = new File(recordPath);
                try {
                    System.out.println("File created: " + fl.getName());
                    fl.createNewFile();
                    clearMe = new PrintWriter(fl);
                } catch (IOException ex) {
                    e.printStackTrace();
                    System.out.println("Can not create file, sorry!");
                    return;
                }
            }
            clearMe.close();
            try {
                wr = new PrintWriter(new FileWriter(recordPath, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 1; i <= rec.length; i++) {
                sb.append("rcrd").append(i).append(" ").append(rec[i - 1]).append("\n");
            }
            wr.print(sb);
        }
        wr.close();

    }

    /**
     * Read all default values from file data.txt or records.txt in directory src/Files and setts according variables.
     * @param var determine which file would be read.
     * @param path path to the file.
     */
    public void readAndSet(String var, String path) {

        if (var == "data") {

            snd = Boolean.parseBoolean(readFile("sound", path));
            msc = Boolean.parseBoolean(readFile("music", path));
            cld = Boolean.parseBoolean(readFile("colored", path));
            wd = Integer.parseInt(readFile("width", path));
            hg = Integer.parseInt(readFile("height", path));
            name = readFile("name", path);

            for (int i = 0; i < ach.length; i++) {
                ach[i] = Boolean.parseBoolean(readFile("achvmnt" + (i + 1), path));
            }

        } else if (var == "records") {

            for (int i = 0; i < rec.length; i++) {
                rec[i] = readFile("rcrd" + (i + 1), path);
            }

        }
    }

    /**
     * Read file at given path and searches for values of given parameter that are after space on the same line after.
     * @param var parameter, which value would be seeking for.
     * @param path path to the file.
     */
    public String readFile(String var, String path) {

        try {
            Scanner scanner = new Scanner(new File(path));
            String [] str;
            String ans = "";
            while (scanner.hasNextLine()) {
                str = scanner.nextLine().trim().split("\\s+");
                if (Pattern.matches("(?i)(" + var + "|" + var.replaceAll("[AEIOUaeiou]", "") + ")", str[0])) {
                    for (int i = 1; i < str.length; i++) {
                        ans += str[i].trim() + " ";
                    }
                }
            }
            scanner.close();
            return ans.trim();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something wrong with file at given path");
        }

        System.out.print("Could not find anything!");
        return null;

    }

    public void setSnd(boolean snd) {
        this.snd = snd;
    }

    public void setMsc(boolean msc) {
        this.msc = msc;
    }

    public void setCld(boolean cld) {
        this.cld = cld;
    }

    public void setWd(int wd) {
        this.wd = wd;
    }

    public void setHg(int hg) {
        this.hg = hg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAch(boolean [] ar) {
        this.ach = ar.clone();
    }

    public void setRec(String [] ar) {
        this.rec = ar.clone();
    }

    public boolean getSnd() {
        return snd;
    }

    public boolean getMsc() {
        return msc;
    }

    public boolean getCld() {
        return cld;
    }

    public int getWd() {
        return wd;
    }

    public int getHg() {
        return hg;
    }

    public String getName() {
        return name;
    }

    public boolean[] getAch() {
        return ach.clone();
    }

    public String[] getRec() {
        return rec.clone();
    }

}
