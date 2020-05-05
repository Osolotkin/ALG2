import java.io.File;

public class Exit extends Command{

    public String execute(File actualDir) {
        if (parameters.length == 1) {
            System. exit(0);
        }
        return null;
    }

}
