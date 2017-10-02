package gui;

import classes.setbuilder.Classifiable;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 11ryt on 7/26/2017.
 */
public class MainView extends JPanel {

    WeatherManager weatherManager;
    private ArrayList<Clock> activeClocks;

    public MainView() {
        super();
        weatherManager = new WeatherManager(77379);
        weatherManager.update();
        activeClocks = new ArrayList<>();

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint background image
        BufferedImage background = null;
        BufferedImage sunOrMoon = null;
        try {
            int currentHour = Integer.parseInt(new SimpleDateFormat("HH").format(Calendar.getInstance().getTime()));
            try {
                background = ImageIO.read(new File(weatherManager.getCurrent().toString() + "_" + currentHour + ".png"));
            } catch (IOException e) {
                background = ImageIO.read(new File("StartScreenImages/DefaultStartBkgd.png"));
            }
            if (currentHour > 20 || currentHour < 7) {
                sunOrMoon = ImageIO.read(new File("Moon_Unsoftened.png"));
            }
        } catch (IOException e) {
            if (e instanceof IIOException) {
                try {
                    background = ImageIO.read(new File("Day_Sunny.png"));
                } catch (IOException e1) {
                    setBackground(Color.black);
                    background = null;
                }
            } else
                e.printStackTrace();
        }
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public void addClock(Clock clock) {
        activeClocks.add(clock);
    }

    private class AutoUpdateClock implements Runnable {
        @Override
        public void run() {
            for (Clock c : activeClocks) {
                c.update();
            }
        }
    }
}
