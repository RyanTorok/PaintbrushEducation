package classes;

import main.Student;

import java.util.ArrayList;

/**
 * Created by S507098 on 4/13/2017.
 */

public class ClassPd {

    private Course castOf;
    private ArrayList<Student> studentList;
    private int periodNo;

    public classes.ClassPdInstance getTodaysInstance() {
        return null;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }
    public Course getCastOf(){
        return castOf;
    }

    public int getPeriodNo() {
        return periodNo;
    }
}
