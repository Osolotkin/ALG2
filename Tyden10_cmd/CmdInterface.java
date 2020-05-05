public interface CmdInterface {

    public String getActualDir();
    public boolean isRunning();
    public String parseAndExecute(String line);
	
}
