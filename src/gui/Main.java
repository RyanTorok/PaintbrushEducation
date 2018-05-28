package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    BarMenu menus[] = new BarMenu[5];
    int currentMenu = 0;
    Text subtitle;
    boolean caps = Toolkit.getDefaultToolkit().getLockingKeyState(java.awt.event.KeyEvent.VK_CAPS_LOCK);

    public static final int BASE_STATE = 0;
    public static final int SLEEP_STATE = 1;

    Node top_bar;
    Node sleepBody;
    int state = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create taskbar icon
        String icon_path = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "icon.png" + File.separator;
        Image icon = new Image("file:"+ icon_path);
        primaryStage.getIcons().add(icon);
        ImageView imageView = new ImageView(icon);

        primaryStage.setTitle("Paintbrush LMS");
        primaryStage.setMaximized(true);
        Text mainlogo = new Text("paintbrush.    ");
        mainlogo.setFont(Font.font("Comfortaa", 60));
        mainlogo.setFill(Color.WHITE);
        final String upper = mainlogo.getText().substring(0, mainlogo.getText().length() - 2).toUpperCase();
        final String lower = mainlogo.getText();

        Text subText = new Text("Let's get something done today.");
        subtitle = subText;
        subText.setFont(Font.font("Sans Serif", FontPosture.ITALIC, 20));
        subText.setFill(Color.WHITE);
        VBox titles = new VBox(mainlogo, subText);
        titles.setSpacing(5);

        //scroll links
        menus[0] = new BarMenu("latest", 0);
        menus[1] = new BarMenu("classes", 1);
        menus[2] = new BarMenu("organizations", 2);
        menus[3] = new BarMenu("browse lessons", 3);
        menus[4] = new BarMenu("community", 4);

        for (BarMenu m :menus) {
            m.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                scrollBody(m.scrollPos, subtitle);
            });
        }
        menus[0].setFont(Font.font(menus[0].getFont().getFamily(), FontWeight.BOLD, menus[0].getFont().getSize()));

        HBox topbar = new HBox(titles, menus[0], menus[1], menus[2], menus[3], menus[4]);
        top_bar = topbar;
        topbar.setSpacing(35);
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setStyle("-fx-background-color: #000000");
        topbar.setPadding(new Insets(15));

        //clock
        final Text clock = new Text("");
        final Text date = new Text("");
        clock.setFill(Color.WHITE);
        clock.setFont(Font.font("Sans Serif", FontWeight.NORMAL, 100));
        date.setFill(Color.WHITE);
        date.setFont(Font.font("Sans Serif", FontWeight.NORMAL, 45));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (state == SLEEP_STATE) {
                    String time = new SimpleDateFormat("EEEEEEEE, MMMMMMMMM dd, YYYY  h:mm:ss aa").format(new Date());
                    String[] timeanddate = time.split("  ");
                    clock.setText(timeanddate[1]);
                    date.setText(timeanddate[0]);
                }
            }
        }, 0, 500);
        BorderPane sleepbody = new BorderPane();
        sleepBody = sleepbody;
        HBox sleep_btm = new HBox(new VBox(clock,date));
        sleep_btm.setAlignment(Pos.BOTTOM_CENTER);
        sleepbody.setBottom(sleep_btm);
        sleepbody.setVisible(false);


        BorderPane body = new BorderPane();
        ImageView backgd = new ImageView(new Image("file:" + System.getProperty("user.dir") + File.separator + "resources" + File.separator + "background.jpeg"));
        backgd.setFitWidth(1900);
        backgd.setPreserveRatio(true);
        StackPane allBodyPanes = new StackPane(sleepbody, body);
        VBox root = new VBox(topbar, allBodyPanes);
        StackPane mainArea = new StackPane(backgd,root);
        primaryStage.setScene(new Scene(mainArea, 999, 649));

        primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (state == SLEEP_STATE) {
                if (event.getCode().equals(KeyCode.ESCAPE))
                    return;
                wakeup();
                if (event.getCode().equals(KeyCode.CAPS)) {
                    caps = !caps;
                }
                return;
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                new Terminal().start();
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                if (currentMenu != 0)
                    scrollBody(currentMenu - 1, subtitle);
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                if (currentMenu != menus.length - 1)
                    scrollBody(currentMenu + 1, subtitle);
            }
            if (event.getCode().equals(KeyCode.CAPS)) {
                caps = !caps;
                mainlogo.setText(caps ? upper : lower);
            }
            if (event.getCode().equals(KeyCode.ESCAPE)) {
                sleep();
            }
        });

        primaryStage.show();
    }

    private void wakeup() {
        state = BASE_STATE;
        top_bar.setVisible(true);
        sleepBody.setVisible(false);
    }

    private void sleep() {
        state = SLEEP_STATE;
        top_bar.setVisible(false);
        sleepBody.setVisible(true);
    }

    class BarMenu extends Text {
        int scrollPos;
        public BarMenu(String text, int order) {
            super (text);
            scrollPos = order;
            addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                this.setUnderline(true);
            });
            addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
                this.setUnderline(false);
            });
            addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Main.this.scrollBody(scrollPos, Main.this.subtitle);
            });
            setFont(Font.font("Confortaa", 20));
            setFill(Color.WHITE);
            setTextAlignment(TextAlignment.CENTER);
        }
    }

    private void scrollBody(int scrollPos, Text changeText) {
        String subtitles[] = {
                "Let's get something done today.",
                "Let's learn something today.",
                "Let's do something we love today.",
                "Let's find a new interest today.",
                "Let's see what the world did today."
        };
        changeText.setText(subtitles[scrollPos]);
        currentMenu = scrollPos;
        BarMenu m = menus[scrollPos];
        for (BarMenu m1 :
                menus) {
            m1.setFont(Font.font(m.getFont().getFamily(), FontWeight.NORMAL, m.getFont().getSize()));
        }
        m.setFont(Font.font(m.getFont().getFamily(), FontWeight.BOLD, m.getFont().getSize()));
    }
}


