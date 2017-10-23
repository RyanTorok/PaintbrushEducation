package terminal;

public class Token {

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        COMMAND, ADDRESS, VARIABLE, VALUE, IDENTIFIER, TAG
    }

    private Type type;



}
