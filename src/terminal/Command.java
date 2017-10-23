package terminal;

import filesystem.Directory;

import java.io.IOException;
import java.util.List;

public class Command extends Token{

    private CommandType type;

    public Command(Command.CommandType type) {
        super(Token.Type.COMMAND);
        this.type = type;
    }

    public CommandType getCommandType() {
        return type;
    }

    public void execute(List<Token> tokens, Terminal terminal) throws IOException, TerminalException {
        switch (this.getCommandType()) {
            case LS:
                terminal.output(terminal.getFileSystemParser().ls());
                break;
            case CD: {
                if (tokens == null || tokens.size() == 0) {
                    throw new TerminalException("expected file address after cd");
                }
                if(tokens.size() > 1) {
                    throw new TerminalException("unexpected token after file address: " + tokens.get(1).toString());
                }
                terminal.getFileSystemParser().navigate(tokens.get(0).toString());
                break;
            }
            case MKDIR: {
                if (tokens == null || tokens.size() == 0 || !(tokens.get(0).getType() == Token.Type.IDENTIFIER)) {
                    throw new TerminalException("expected directory name after mkdir or new folder");
                }
                String directoryName = tokens.remove(0).toString();
                Directory newDir = new Directory(directoryName);
                while (!tokens.isEmpty()) {
                    switch (tokens.get(0).getType()) {
                        case TAG:
                            Tag.fromString(tokens.get(0).toString()).execute(newDir, tokens);
                        default: throw new TerminalException("unexpected token after directory name: " + tokens.get(0).toString());
                    }
                }
            }
            case INVOKE: {
                if (tokens == null || tokens.size() == 0 || !(tokens.get(0).getType() == Token.Type.IDENTIFIER)) {
                    throw new TerminalException("expected function identifier after invoke");
                }
            }
        }
    }

    public enum CommandType {
        LS, MKDIR, CD, INVOKE, NEW, FOLDER, SET, ASSIGNMENT, MACRO, MARK, PRESENT, ABSENT, TARDY, CHANGE, GRADE, FOR
    }
}