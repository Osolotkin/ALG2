import java.io.File;

public class Rename extends Command{

    public String execute(File actualDir) {
        if (parameters.length == 3) {
            rename(actualDir, parameters[1], parameters[2]);
        }
        return null;
    }

    private void rename(File actDir, String actName, String ftrName) {
        File flA = new File(actDir.getAbsolutePath() + "\\\\" + actName);
        File flF = new File(actDir.getAbsolutePath() + "\\\\" + ftrName);
        flA.renameTo(flF);
    }

}
