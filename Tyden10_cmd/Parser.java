public class Parser {

    public static Command parse(String line) {
        String [] p = line.split(" +");
        char first = Character.toUpperCase(p[0].charAt(0));
        String name = Command.CommandPackage + "." + first + p[0].substring(1);
        try {
            Class c = Class.forName(name);
            Command command = (Command) c.newInstance();
            command.setParameters(p);
            return command;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
