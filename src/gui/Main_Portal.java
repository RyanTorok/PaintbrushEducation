package gui;

import classes.Record;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Root;
import main.User;
import main.UtilAndConstants;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by 11ryt on 4/1/2017.
 */
public class Main_Portal extends Application implements Runnable {

    private static int hour;
    private static Date current;

    public Main_Portal() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paintbrush");
        primaryStage.getIcons().add(new Image(new FileInputStream(UtilAndConstants.parseFileNameForOS(".\\SubLogo_2.png"))));

        try {
            hour = Integer.parseInt(new SimpleDateFormat("HH").format(Clock.currentSafeTime()));
            current = new Date(Clock.currentSafeTime());
        } catch (Exception e) {
            hour = Integer.parseInt(new SimpleDateFormat("HH").format(System.currentTimeMillis()));
            current = new Date(System.currentTimeMillis());
        }

        //maximize
        primaryStage.setMaximized(true);

        //start screen background image
        Image backgroundImg;
        try {
            backgroundImg = new Image(new FileInputStream(UtilAndConstants.parseFileNameForOS(".\\StartScreenImages\\Sunny_" + hour + ".png")));
        } catch (FileNotFoundException e) {
            try {
                backgroundImg = new Image(new FileInputStream(UtilAndConstants.parseFileNameForOS(".\\StartScreenImages\\DefaultStartBkgd.png")));
            } catch (FileNotFoundException e1) {
                backgroundImg = null;
            }
        }
        GridPane bkrd = new GridPane();
        ImageView img = new ImageView(backgroundImg);
        img.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        img.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        bkrd.add(img, 0, 0);
        primaryStage.setScene(new Scene(bkrd));

        GridPane grid = new GridPane();
        grid.setBorder(Border.EMPTY);
        grid.setHgap(0);
        grid.setVgap(0);
        StackPane fgrd = new StackPane(grid);

        //topbar
        GridPane topbar = new GridPane();
        topbar.setAlignment(Pos.TOP_CENTER);
        ArrayList<Node> topbarNodes = new ArrayList();
        topbarNodes.add(topbar);
        Group topbarGroup = new Group(topbarNodes);
        SubScene topbarScene = new SubScene(topbarGroup, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getWidth() / 20);
        Color topbarBkgdColor = (Root.getActiveUser() == null || Root.getActiveUser().getAccentColor() == null) ? Color.LIGHTGRAY : Root.getActiveUser().getAccentColor();
        topbarScene.setFill(topbarBkgdColor);
        topbarScene.setTranslateY(-1 * Screen.getPrimary().getVisualBounds().getHeight()/12.5);
        grid.setStyle("border: 0em 0em 10em 0em");


        //welcome message in top left corner
        Text welcomeTitle = new Text(getWelcomeMessage(Root.getActiveUser()));
        welcomeTitle.setFill(Color.WHITE);
        welcomeTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, parseFontSize(grid, 20)));
        welcomeTitle.setStyle("font-family: Wingdings");
        topbar.add(welcomeTitle, 0, 0);

        //subtitle under welcome message
        Text subtitle = new Text(" Let's get something done " + (hour > 6 && hour < 21 ? "today." : "tonight."));
        subtitle.setFill(Color.WHITE);
        subtitle.setFont(Font.font("Lucida Handwriting", FontPosture.ITALIC, parseFontSize(grid, 20)));
        topbar.add(subtitle, 1, 0);

        //RecentUpdates and UpcomingEvents Pane
        GridPane rugrid = new GridPane();
        ArrayList<Node> ruNodes = new ArrayList();
        ruNodes.add(new Text("Recent Updates"));
        if (Root.getActiveUser().getUpdates() != null && !Root.getActiveUser().getUpdates().isEmpty()) {
            for (Record r : Root.getActiveUser().getUpdates()) {
                ruNodes.add(new Text(r.toString()));
            }
        } else {
            ruNodes.add(new Text("No recent updates."));
        }
        Group recentUpdatesPane = new Group(ruNodes);
        SubScene ss = new SubScene(recentUpdatesPane, Screen.getPrimary().getVisualBounds().getWidth() / 4, Screen.getPrimary().getVisualBounds().getHeight() / 4);
        ss.setFill(Color.AQUA);
        ss.setOpacity(0.5);
        ss.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ss.setOpacity(1);
            }
        });
        ss.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ss.setOpacity(0.75);
            }
        });
        grid.add(ss, 1, 0);
        
        //time and date
        GridPane tdGrid = new GridPane();
        ArrayList<Node> tdNodes = new ArrayList();
        Text time = new Text(new SimpleDateFormat((Root.getUtilAndConstants() == null || Root.getUtilAndConstants().getStartScreenTimeFormat() == null ? "HH:MM" : Root.getUtilAndConstants().getStartScreenTimeFormat())).format(new Time(Clock.currentSafeTime())));
        time.setFill(Color.WHITE);
        time.setFont(Font.font("Lucida HandWriting", FontWeight.BOLD, parseFontSize(tdGrid, 100)));
        tdNodes.add(time);
        Group td = new Group(tdNodes);
        SubScene tdss = new SubScene(td, Screen.getPrimary().getVisualBounds().getWidth() / 2, Screen.getPrimary().getVisualBounds().getHeight() / 2);
        time.setLayoutY(tdss.getHeight() / 2);

        grid.add(topbarScene, 0,0);
        grid.add(tdss, 0, 2);
        bkrd.add(fgrd, 0, 0);
        primaryStage.show();
    }

    private String getWelcomeMessage(User activeUser) {
        String timeMessage;
        switch (hour) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                timeMessage = "Good Morning";
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
                timeMessage = "Good Afternoon";
                break;
            case 18:
            case 19:
            case 20:
            case 21:
                timeMessage = "Good Evening";
                break;
            case 22:
            case 23:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                timeMessage = "Good Night";
                break;
            default:
                timeMessage = "Welcome";
        }
        if (activeUser != null && activeUser.getFirst() != null) {
            if (new SimpleDateFormat("MM/DD").format(activeUser.getBirthday()).equals(new SimpleDateFormat("MM/DD").format(current))) {
                return "Happy Birthday" + ((activeUser.getFirst() != null) ? ", " + activeUser.getFirst() : "") + "!";
            }
            return timeMessage + ", " + activeUser.getFirst();
        } else {
            return timeMessage + ".";
        }
    }

    public static Double parseFontSize(GridPane s, double size) {
        //return new Double(Math.max(s.getWidth() /999.0 * size, 1));
        return size;
        //TODO fix this.
    }

    @Override
    public void run() {
        launch(null);
    }

    public void run(String errorMsg) {
        Alert error = new Alert(Alert.AlertType.ERROR, "Fatal Error: " + errorMsg);
        Optional<ButtonType> result = error.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(-2);
        }
    }
}
