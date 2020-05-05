import java.io.File;

public class Mkdir extends Command {

    public String execute(File actualDir) {
        if (parameters.length == 2) {
            File fl = new File(parameters[2]);
            fl.mkdirs();
        }

        return null;
    }

}
