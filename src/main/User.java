package main;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by 11ryt on 4/21/2017.
 */
public abstract class User implements classes.setbuilder.Classifiable{

    private int id;
    private String mac;
    private String username;
    private String password;
    private String first;
    private String middle;
    private String last;
    private String email;
    private String homephone;
    private String cellphone;
    private String schoolCode;
    private String address;
    private Timestamp timestamp;
    private Color accentColor;
    private Date birthday;

    public User(int id, String mac, String username, String password, String first, String middle, String last, String email, String homephone, String cellphone, String address, Timestamp timestamp) {
        this.id = id;
        this.mac = mac;
        this.username = username;
        this.password = password;
        this.first = first;
        this.middle = middle;
        this.last = last;
        this.email = email;
        this.homephone = homephone;
        this.cellphone = cellphone;
        this.address = address;
        this.timestamp = timestamp;
    }

    protected User() {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

    public String getEmail() {
        return email;
    }

    public String getHomePhone() {
        return homephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public String getAddress() {
        return address;
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public abstract String getID();

    public void setAccentColor(Color c) {
        this.accentColor = c;
    }

    public Color getAccentColor() {
        return accentColor;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public Date getBirthday() {
        return birthday;
    }
}
