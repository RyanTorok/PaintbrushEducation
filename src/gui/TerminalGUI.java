package gui;

import java.io.BufferedOutputStream;

public class TerminalGUI extends javafx.scene.Node{
    private BufferedOutputStream outputStream;

    public BufferedOutputStream getOutputStream() {
        return outputStream;
    }
}
