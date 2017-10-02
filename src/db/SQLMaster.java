package db;

import classes.School;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by 11ryt on 7/19/2017.
 */
public class SQLMaster extends SQL {

    private static Connection overallServerConnection = null;
    private static Connection remoteDistrictConnection = null;

    public static void connectToOverallServer() {
        String url = "jdbc:mysql://localhost:3306/paintbrush_server_master";
        String username = "java_server_master";
        String password = "zg7PvdMZeZg76p1f";
        try {
            overallServerConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private static void connectToRemoteDistrictDB(String schoolCode, String password){
        String url = "jdbc:mysql://localhost:3306/districttestdb";
        String username = "school_access_" + schoolCode.substring(schoolCode.indexOf("-")+1);
        try {
            remoteDistrictConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static ArrayList lookUpActivationKey(String text) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            overallServerConnection.setAutoCommit(false);
            ps = overallServerConnection.prepareStatement("SELECT `SchoolCode`, `usertype` FROM activationKeys WHERE `activationkey` = ?");
            ps.setString(1, text);
            rs = ps.executeQuery();
            overallServerConnection.commit();
        } finally {
            overallServerConnection.setAutoCommit(true);
        }
        ArrayList al = new ArrayList();
        if(rs.first()){
            String wholeSC = rs.getString("SchoolCode");
            String districtCode = wholeSC.substring(0, wholeSC.indexOf("-"));
            String schoolIdentifier = wholeSC.substring(wholeSC.indexOf("-")+1);
            PreparedStatement ps2 = null;
            ResultSet rs2 = null;
            try {
                connectToRemoteDistrictDB(wholeSC, text);
                remoteDistrictConnection.setAutoCommit(false);
                ps2 = remoteDistrictConnection.prepareStatement("SELECT * FROM schools WHERE `SchoolCode` = ?");
                ps2.setString(1, schoolIdentifier);
                rs2 = ps2.executeQuery();
                remoteDistrictConnection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                overallServerConnection.setAutoCommit(true);
            }
            rs2.first();
            al.add(new School(rs2.getString("Name")));
            ps2.close();
            al.add(rs.getInt("usertype"));
            ps.close();
        } else{
            ps.close();
            al.add(null);
            al.add(-1);
        }

        return al;
    }
}
