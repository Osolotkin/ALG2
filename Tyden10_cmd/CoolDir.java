package com.company;

import java.io.File;
import java.util.Collections;

public class CoolDir extends Command {

    private Dir dir;

    @Override
    public String execute(File actualDir) {
        dir = new Dir();
        dir.setParameters(new String [] {"dir"});
        return dirRec(actualDir, 1);
    }

    public String dirRec(File actualDir, int step) {
        String dirs = dir.execute(actualDir);
        String [] tmp = dirs.split("\n");
        dirs = "\n";
        for (int i = 0; i < tmp.length; i++) {
            dirs = dirs + String.join("", Collections.nCopies(step, "-")) + tmp[i];
            File newDir = new File(tmp[i].trim());
            if (newDir.isDirectory()) {
                dirs = dirs + dirRec(newDir, step + 1);
            }
            if (i < tmp.length - 1) dirs = dirs + "\n";
        }
        return dirs;
    }

}
