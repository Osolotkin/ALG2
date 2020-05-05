import java.io.File;

public class CmdEditor implements CmdInterface {

    private boolean isRunning;
    private File actualDir;
    private Command command;

    public CmdEditor() {
        isRunning = true;
        actualDir = new File(System.getProperty("user.dir"));
    }

    @Override
    public String getActualDir() {
        return actualDir.getAbsolutePath();
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public String parseAndExecute(String line) {
        command = Parser.parse(line);
        String tmp = command.execute(actualDir);
        actualDir = new File(actualDir.getAbsolutePath() + "\\\\" + tmp);
        return tmp;
    }

}
