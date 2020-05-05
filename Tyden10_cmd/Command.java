import java.io.File;

public abstract class Command {

    public static String CommandPackage = "com.company";
    protected String [] parameters;

    Command() {

    }

    public abstract String execute(File actualDir);

    public void setParameters(String[] parameters) {
        this.parameters = parameters.clone();
    }

    protected String writeError() {
        return "\\u001B[31m Warning! There is something wrong with parameters \\u001B[0m";
    }

}
