package terminal;

import java.util.ArrayList;

public class Tokenizer {

    private ArrayList<Token> tokens;

    private Token parseToken(String s) {

    }

    public void loadCommand(String input) {
        setTokens(new ArrayList<>());
        String[] split = input.trim().split("\\s+");
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
