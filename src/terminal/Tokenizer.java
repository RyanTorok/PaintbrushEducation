package terminal;

import java.util.ArrayList;

public class Tokenizer {

    private ArrayList<Token> tokens;

    private Token parseToken(String s, boolean inst) throws TerminalException {
        if (s.charAt(0) == '\\')
            return Tag.fromString(s.substring(1));
        if (inst) {
            switch (s) {
                case "ls": return new Token();
            }
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

    public Command get() {
    }
}
