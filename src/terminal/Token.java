package terminal;

public class Token {

    private String tokenLabel;
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        COMMAND, ADDRESS, VARIABLE, VALUE, IDENTIFIER, TAG, LPAREN, RPAREN, LBRACE, RBRACE, LBRACKET, RBRACKET, EQUALS, LANGLE, RANGLE, COMMA
    }



    public Token(Type t) {
        type = t;
    }

    @Override
    public String toString() {
        return (tokenLabel == null) ? type.toString().toLowerCase() : tokenLabel;
    }
}