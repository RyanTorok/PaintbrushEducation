package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Root;
import main.User;

import java.io.FileInputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by 11ryt on 4/1/2017.
 */
public class Main_Portal extends Application implements Runnable {

    private static int hour;
    private static Date current;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paintbrush");
        primaryStage.getIcons().add(new Image(new FileInputStream(".\\SubLogo_2.png")));

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
        Image backgroundImg = new Image(new FileInputStream(".\\StartScreenImages\\Sunny_" + hour + ".png"));
        GridPane bkrd = new GridPane();
        ImageView img = new ImageView(backgroundImg);
        img.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
        img.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        bkrd.add(img, 0, 0);
        primaryStage.setScene(new Scene(bkrd));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        StackPane fgrd = new StackPane(grid);

        //welcome message in top left corner
        Text welcomeTitle = new Text(getWelcomeMessage(Root.getActiveUser()));
        welcomeTitle.setFill(Color.WHITE);
        welcomeTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, parseFontSize(grid, 100)));
        grid.add(welcomeTitle, 0, 0);

        //subtitle under welcome message
        Text subtitle = new Text("Let's get something done " + (hour > 6 && hour < 21 ? "today." : "tonight."));
        subtitle.setFill(Color.WHITE);
        subtitle.setFont(Font.font("Lucida Handwriting", FontWeight.BOLD, parseFontSize(grid, 60)));
        grid.add(subtitle, 0, 1);

        //RecentUpdates and UpcomingEvents Pane
        GridPane rugrid = new GridPane();
        ArrayList<Node> ruNodes = new ArrayList();
        ruNodes.add(new Text("hello"));
        Group recentUpdatesPane = new Group(ruNodes);
        SubScene ss = new SubScene(recentUpdatesPane, Screen.getPrimary().getVisualBounds().getWidth() /4, Screen.getPrimary().getVisualBounds().getHeight() / 4);
        grid.add(recentUpdatesPane, 1, 0);

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
        if (activeUser != null) {
            if (new SimpleDateFormat("MM/DD").format(activeUser.getBirthday()).equals(new SimpleDateFormat("MM/DD").format(current)))
                return "Happy Birthday, " + activeUser.getFirst() + "!";
            return timeMessage + ", " + activeUser.getFirst();
        } else{
            return timeMessage;
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
}
