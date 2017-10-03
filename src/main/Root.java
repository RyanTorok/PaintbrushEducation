package main;

import db.SQL;
import exceptions.OfflineException;
import gui.Main_Portal;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;
import javax.swing.*;

/**
 * Created by 11ryt on 4/14/2017.
 */

public class Root {

    private static Integer activeID;
    private static Main_Portal activeFrame;
    private static UtilAndConstants utilAndConstants;
    private static User active = null;
    private static Main_Portal portal;


    public static void main(String[] args) {
        portal = new Main_Portal();
        activeFrame = getPortal();
        try {
            SQL.connect();
        } catch (OfflineException e) {
            //TODO setup offline setup edge case handle
            e.printStackTrace();
        }
        utilAndConstants = SQL.initUtilAndConstants();
        String mac = getMACAddress();
        
       portal.run();
    }

    public static String getMACAddress() {
        try {
            InetAddress ip6 = Inet6Address.getLocalHost();
            NetworkInterface nwi = NetworkInterface.getByInetAddress(ip6);
            byte mac[] = nwi.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println("Error accessing system identifying information.");
            return "";
        }
    }

    public static Integer getActiveID() {
        return activeID;
    }

    public static javafx.application.Application getActiveFrame() {
        return activeFrame;
    }

    public static UtilAndConstants getUtilAndConstants() {
        return utilAndConstants;
    }

    public static Main_Portal getPortal() {
        return portal;
    }

    public static User getActiveUser() {
        return active;
    }
}
