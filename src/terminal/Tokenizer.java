package terminal;

import gui.Terminal;

import java.util.ArrayList;

public class Tokenizer {

    private ArrayList<Token> tokens;

    private Token parseToken(String s, boolean inst) throws TerminalException {
        if (s.charAt(0) == '\\')
            return Tag.fromString(s.substring(1));
        if (inst) {
            switch (s.toLowerCase()) {
                case "ls": return new Token(Command.CommandType.LS);
                case "cd": return new Token(Command.CommandType.CD);
                case "pwd": return new Token(Command.CommandType.PWD);
                case "mkdir": return new Token(Command.CommandType.MKDIR);
                case "new": return new Token(Command.CommandType.NEW);
                case "take": return new Token(Command.CommandType.TAKE);
                case "sync": return new Token(Command.CommandType.SYNC);
                case "create": return new Token(Command.CommandType.CREATE);
                case "touch": return new Token(Command.CommandType.TOUCH);
                case "edit": return new Token(Command.CommandType.EDIT);
                case "delete": return new Token(Command.CommandType.DELETE);
                case "rm": return new Token(Command.CommandType.RM);
                case "run": return new Token(Command.CommandType.RUN);
                case "cat": return new Token(Command.CommandType.CAT);
                case "see": return new Token(Command.CommandType.SEE);
                case "search": return new Token(Command.CommandType.SEARCH);
                case "switch": return new Token(Command.CommandType.SWITCH);
                case "submit": return new Token(Command.CommandType.SUBMIT);
                case "turnin": return new Token(Command.CommandType.TURNIN);
                case "define": return new Token(Command.CommandType.DEFINE);
                case "clear": return new Token(Command.CommandType.CLEAR);
                case "exit": return new Token(Command.CommandType.EXIT);
                default: throw new TerminalException(s + " - Command not found");
            }
        }

        throw new TerminalException("unexpected token: " + s);
    }

    public void loadCommand(String input) throws TerminalException {
        setTokens(new ArrayList<>());
        String[] split = input.trim().split("[^A-Za-z0-9]|\\s+");
        int index = 0;
        for (String s : split) {
            getTokens().add(parseToken(s, index++ == 0));
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Command get() throws TerminalException {
        if (tokens.get(0).getType() != Token.Type.COMMAND)
            throw new TerminalException("non-command token in index 0");

        switch (tokens.get(0).getCtype()) {
            case LS: return new LS();
        }
        return null;
    }
}
