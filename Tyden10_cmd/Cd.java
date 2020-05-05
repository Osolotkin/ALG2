import java.io.File;

public class Cd extends Command {

    @Override
    public String execute(File actualDir) {
        File[] files;
        files = actualDir.listFiles();
        String st;

        if(parameters.length == 2) {
            if (parameters[1].equals("..")) st = setActualDir(actualDir);
            else st = setActualDir(files, parameters[1]);
            if (st == null) writeError();
            else return st;
        } else {
            writeError();
        }
        return null;
    }

    private String setActualDir(File[] files, String dirName) {
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory() && files[i].getName().equals(dirName)) {
                return files[i].getName();
            }
        }
        return null;
    }

    private String setActualDir(File file) {
        return file.getParentFile().getName();
    }

}
