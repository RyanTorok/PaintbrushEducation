package terminal;

import java.io.IOException;
import java.util.List;

public class Command {

    public static Command parseCommand(Token t) {
    }

    private Type type;

    public Type getType() {
        return type;
    }

    public void execute(List<Token> tokens, Terminal terminal) throws IOException, TerminalException {
        switch (this.getType()) {
            case LS:
                terminal.output(terminal.getFileSystemParser().ls());
                break;
            case CD: {
                if (tokens == null || tokens.size() == 0) {
                    throw new TerminalException("expected file address after cd");
                }
                if(tokens.size() > 1) {
                    throw new TerminalException("uexpected token after file address: " + tokens.get(1).toString());
                }
                terminal.getFileSystemParser().navigate(tokens.get(0).toString());
                break;
            }
            case MKDIR: {
                if (tokens == null || tokens.size() == 0 || !(tokens.get(0).getType() == Token.Type.IDENTIFIER)) {
                    throw new TerminalException("expected directory name after mkdir or new folder");
                }
                String directoryName = tokens.remove(0).toString();
                while (!tokens.isEmpty()) {
                    switch (tokens.get(0).getType()) {
                        case TAG:
                            
                    }
                }
            }
        }
    }

    public enum Type {
        LS, MKDIR, CD
    }
}