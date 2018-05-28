package terminal;

public class TerminalUI {
    public String getPrompt() {
        return "user@school(TODO) >";
    }

    void command(String input) {
        Command.command(input);
    }
}
