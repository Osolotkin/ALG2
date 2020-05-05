import java.io.File;
import java.util.Date;

public class Dir extends Command {

    @Override
    public String execute(File actualDir) {
        File[] files;
        files = actualDir.listFiles();
        if (parameters.length == 1) {
            return dirToString(files);
        } else if(parameters.length == 2 && parameters[1].equals("-o")) {
            orderFiles(files);
            return dirToString(files);
        } else if(parameters.length == 3 && parameters[1].equals("-e")) {
            return dirToString(sameExtension(files, parameters[2]));
        } else if(parameters.length == 3 && parameters[1].equals("-s")) {
            return dirToString(specifiedSize(files, Integer.parseInt(parameters[2])));
        } else {
            writeError();
        }
        return null;
    }

    private File [] specifiedSize(File[] files, int size) {
        File [] tmp = new File [files.length];
        int c = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].length() > size) {
                tmp[c] = files[i];
                c++;
            }
        }
        File [] ans = new File [c];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = tmp[i];
        }
        return ans;
    }

    private File [] sameExtension(File[] files, String ext) {
        if (!ext.contains(".")) ext = "." + ext;
        File [] tmp = new File [files.length];
        int c = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(ext)) {
                tmp[c] = files[i];
                c++;
            }
        }
        File [] ans = new File [c];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = tmp[i];
        }
        return ans;
    }
    private void orderFiles(File[] files) {
        for (int i = 0; i < files.length; i++) {
            for (int j = 1; j < files.length; j++) {
                if (files[j].compareTo(files[j-1]) < 0){
                    File tmp = files[j];
                    files[j] = files[j-1];
                    files[j-1] = tmp;
                }
            }
        }
    }

    private String dirToString(File[] files) {
        StringBuilder sb = new StringBuilder("");
        for (File file : files) {
            if (file.isDirectory()) {
                sb.append(String.format("%s%n", file.getName()));
            } else {
                sb.append(String.format("%-20s%6d", file.getName(), file.length()));
                sb.append(new Date(file.lastModified())).append("\n");
            }
        }
        return sb.toString();
    }

}
