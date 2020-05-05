import java.io.File;

public class Help extends Command {

    @Override
    public String execute(File actualDir) {
        String help = "HELP\n"
                + String.format("%-7s %s\n", "help", "Display help")
                + String.format("%-7s %s\n", "dir", "Display list of files and folders", "Parameters -o -e -s")
                + String.format("%-7s %s\n", "cd", "Change directory", "Parameters [folder name] ..")
                + String.format("%-7s %s\n", "mkdir", "Creates new folder", "Parameters [folder name] ..")
                + String.format("%-7s %s\n", "rename", "Rename folder or file", "Parameters [folderFrom] [folderTo]")
                + String.format("%-7s %s\n", "exit", "Quit the program");
        return help;
    }

}
