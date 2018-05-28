package terminal;

public class PWD extends Command {
    @Override
    protected String execute(String[] input) {
        return System.getProperty("user.dir");
    }

}
