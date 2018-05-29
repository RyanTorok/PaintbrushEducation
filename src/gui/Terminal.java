package gui;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import terminal.TerminalUI;

public class Terminal extends AnchorPane {

    TranslateTransition ent;
    TranslateTransition exit;

    public Terminal() {
        //make terminal automatically close if it loses focus, but retain its contents
        Terminal self = this;
        focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && oldValue) {
                self.exit.play();
            }
        });

        TranslateTransition init = new TranslateTransition();
        init.setByY(250);
        init.setByX(-2000);
        init.setDuration(Duration.millis(1));
        init.setNode(this);
        init.play();

        ent = new TranslateTransition();
        ent.setDuration(Duration.millis(100));
        ent.setNode(this);
        ent.setByX(2300);
        ent.setAutoReverse(false);

        exit = new TranslateTransition();
        exit.setDuration(Duration.millis(100));
        exit.setNode(this);
        exit.setByX(-2300);
        exit.setAutoReverse(false);

        TerminalUI eval = new TerminalUI();
        Text text = new Text(eval.getPrompt());
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);

        setStyle("-fx-background-color: #303030");
        setPrefHeight(600);
        setPrefWidth(1200);
    }

    void start(){
        ent.play();
    }

    void exit() {
        exit.play();
    }
}
