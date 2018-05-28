package gui;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import terminal.TerminalUI;

public class Terminal extends javafx.stage.Stage {
    public void start() {

        //make terminal automatically close if it loses focus, but retain its contents
        Terminal self = this;
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && oldValue) {
                self.hide();
            }
        });
        TerminalUI eval = new TerminalUI();
        Text text = new Text(eval.getPrompt());
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
    }
}
