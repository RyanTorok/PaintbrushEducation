package terminal;

import java.util.ArrayList;

public abstract class Command {
    String name;
    String alias;

    /*
        Performs any action specified by the command, then returns any text to be printed.
        If there is no output, returns "Success" to verify the command succeeded.
     */
    protected abstract String execute(ArrayList<Token> tokens);

    static String command(String input) {
        Tokenizer tok = new Tokenizer();
        try {
            tok.loadCommand(input);
            Command c = tok.get();
        } catch (TerminalException e) {
            return "Terminal: " + e.getMessage();
        }
        return null;
    }

    public enum CommandType {
        MKDIR, LS, PWD, NEW, CD, TAKE, SYNC, CREATE, TOUCH, EDIT, DELETE,
        RM, RUN, CAT, SEE, LOOKAT, SEARCH, SWITCH, SUBMIT, TURNIN, CLEAR, EXIT, DEFINE
    }
}
