package terminal;

import java.util.ArrayList;

public class Tokenizer {

    private ArrayList<Token> tokens;

    private Token parseToken(String s) throws TerminalException {
        if (s.charAt(0) == '\\')
            return Tag.fromString(s.substring(1));
        switch (s) {
            case "mkdir":
                return new Command(Command.CommandType.MKDIR);
            case "ls": return new Command(Command.CommandType.LS);
            case "cd": return new Command(Command.CommandType.CD);
            case "new": return new Command(Command.CommandType.NEW);
                default:
        }
        throw new TerminalException("unexpected token: " + s);
    }

    public void loadCommand(String input) throws TerminalException {
        setTokens(new ArrayList<>());
        String[] split = input.trim().split("[^A-Za-z0-9]|\\s+");
        for (String s : split) {
            getTokens().add(parseToken(s));
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
}
