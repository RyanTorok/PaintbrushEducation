package terminal;

import java.util.ArrayList;

public class PWD extends Command {
    @Override
    protected String execute(ArrayList<Token> tokens) {
        return System.getProperty("user.dir");
    }

}
