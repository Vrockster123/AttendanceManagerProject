package com.example.vrock.attendancemanager2;

/**
 * Created by vvvro on 3/5/2016.
 */
public class Subject {
    public String subjectName;
    public int ClassesAttended;
    public int total;
    public float percent;

    Subject(){
        subjectName=null;
        ClassesAttended=0;
        total=0;
        percent=0;
    }

    Subject(String name, int attended, int total){
        subjectName = name;
        ClassesAttended = attended;
        this.total = total;
        percent = ((float)attended/total) * 100;
    }

    Subject(String name, int attended, int total, float percent){
        subjectName = name;
        ClassesAttended = attended;
        this.total = total;
        this.percent = percent;
    }
}
