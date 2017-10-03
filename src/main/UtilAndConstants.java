package main;

import classes.MasterSchedule;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by 11ryt on 7/3/2017.
 */
public class UtilAndConstants {

    private final int MAX_ATTENDANCE_EXTRA_TIME;
    private ArrayList<String> attendanceCodes;
    private final Time attendanceStartTime;
    private static String operatingSystem;

    public UtilAndConstants(int mAET, ArrayList<String> attendanceCodes, Time attendanceStartTime) {
        MAX_ATTENDANCE_EXTRA_TIME = mAET;
        this.attendanceCodes = attendanceCodes;
        this.attendanceStartTime = attendanceStartTime;
    }

    public static String parseFileNameForOS(String fn) {
        if (operatingSystem == null) {
            initOS();
        }
        switch (operatingSystem) {
            case "win":
                return fn;
            case "linux":
                if (fn.indexOf(".\\") == 0)
                    fn = fn.substring(2);
                return fn.replaceAll("\\\\", "/");
            case "mac":
                return fn;
            default:
                return null;
        }
    }

    private static void initOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.indexOf("win") >= 0)
            operatingSystem = "win";
        else if(os.indexOf("linux") >= 0)
            operatingSystem = "linux";
        else if (os.indexOf("mac") >= 0)
            operatingSystem = "mac";
    }

    public int getMAX_ATTENDANCE_EXTRA_TIME() {
        return MAX_ATTENDANCE_EXTRA_TIME;
    }

    public long elapsedTimeMillis(Time earlierTime, Time laterTime) {
        return laterTime.getTime() - earlierTime.getTime();
    }

    public BigDecimal elapsedTimeUnits(Time earlierTime, Time laterTime, byte convertTo) {
        //convertTo Key:
        //0: millisecond (1/1000 second) - returns original value
        //1: centisecond (1/100 second)
        //2: decisecond  (1/10 second)
        //3: second
        //4: minute
        //5: hour
        //6: day
        //7: year
        //8: decade
        //9: century
        //10: millennium
        return convertTimeUnits(elapsedTimeMillis(earlierTime, laterTime), convertTo);
    }

    private BigDecimal convertTimeUnits(long millis, byte convertTo) {
        BigDecimal bd = new BigDecimal(Long.toString(millis));
        switch (convertTo) {
            case 10:
                bd = bd.divide(new BigDecimal("10"));
            case 9:
                bd = bd.divide(new BigDecimal("10"));
            case 8:
                bd = bd.divide(new BigDecimal("10"));
            case 7:
                bd = bd.divide(new BigDecimal("365.2422"));
            case 6:
                bd = bd.divide(new BigDecimal("24"));
            case 5:
                bd = bd.divide(new BigDecimal("60"));
            case 4:
                bd = bd.divide(new BigDecimal("60"));
            case 3:
                bd = bd.divide(new BigDecimal("10"));
            case 2:
                bd = bd.divide(new BigDecimal("10"));
            case 1:
                bd = bd.divide(new BigDecimal("10"));
            case 0:
                break;
            default:
                throw new IllegalArgumentException("Illegal Time Converter Index: " + convertTo);
        }
        return bd;
    }

    public String getAttendanceCodes(int i) {
        return attendanceCodes.get(i);
    }

    public Time getAttendanceStartTime() {
        return attendanceStartTime;
    }

    public MasterSchedule getTodaysSchedule() {
        return null;
    }
}
