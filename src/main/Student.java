package main;

import classes.AttendanceCode;
import classes.ClassPd;
import exceptions.AttendanceException;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by 11ryt on 4/21/2017.
 */
public class Student extends User {
    private String studentID;

    public Student(int id, String mac, String username, String password, String first, String middle, String last, String email, String homephone, String cellphone, String address, String studentID, Timestamp timestamp) {
        super(id, mac, username, password, first, middle, last, email, homephone, cellphone, address, timestamp);
        this.studentID = studentID;
    }

    public Student(int id, String mac, String username, String password, String first, String middle, String last, String email, String homePhone, String cellPhone, String address, String schoolCode, String studentID, Timestamp timestamp) {

    }

    @Override
    public String getID() {
        return studentID;
    }

    public boolean markPresent(ClassPd activeClass, Time time) {
        try {
            if (main.Root.getUtilAndConstants().elapsedTimeMillis(time, activeClass.getTodaysInstance().getEndTime()) < main.Root.getUtilAndConstants().getMAX_ATTENDANCE_EXTRA_TIME()) {
                if (main.Root.getUtilAndConstants().elapsedTimeMillis(time, (activeClass.getPeriodNo() == 1) ? Root.getUtilAndConstants().getAttendanceStartTime() : Root.getUtilAndConstants().getTodaysSchedule().getEndTime(activeClass.getPeriodNo()-1)) < 0) {
                    activeClass.getTodaysInstance().getAttendance().set(activeClass.getStudentList().indexOf(this), new AttendanceCode(Root.getUtilAndConstants().getAttendanceCodes(0)));
                    return true;
                } else{
                    throw new AttendanceException("Too early to mark attendance. Class =" + activeClass.getCastOf().getName() + ", Period = " + activeClass.getPeriodNo()+ ".");
                }
            } else {
                throw new AttendanceException("Attendance deadline passed.");
            }
        } catch (Exception e) {
            return false;
        }
    }
}