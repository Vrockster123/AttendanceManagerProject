package com.example.vrock.attendancemanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vvvro on 1/30/2016.
 */
public class AttendanceDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Attendance.db";

    public AttendanceDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + AttendanceContract.SubjectEntry.TABLE_NAME +
                " (" +
                AttendanceContract.SubjectEntry._ID + " INTEGER PRIMARY KEY," +
                AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME + " TEXT NOT NULL," +
                AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_ATTENDED + " NUMBER NOT NULL," +
                AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_TOTAL + " NUMBER NOT NULL," +
                AttendanceContract.SubjectEntry.COLUMN_NAME_ATTENDANCE_PERCENTAGE + " FLOAT NOT NULL);";
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + AttendanceContract.SubjectEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertSubject(String name, int attended, int total, float percent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME, name);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_ATTENDED, attended);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_TOTAL, total);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_ATTENDANCE_PERCENTAGE, percent);
        db.insert(AttendanceContract.SubjectEntry.TABLE_NAME, null, contentValues);
        return true;
    }
    public boolean updateSubject(String name, int attended, int total, float percent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME, name);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_ATTENDED, attended);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_HOURS_TOTAL, total);
        contentValues.put(AttendanceContract.SubjectEntry.COLUMN_NAME_ATTENDANCE_PERCENTAGE, percent);
        db.update(AttendanceContract.SubjectEntry.TABLE_NAME, contentValues, AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME + " = ? ", new String[]{name});
        return true;
    }

    public Cursor getSubject(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                + AttendanceContract.SubjectEntry.TABLE_NAME
                + " WHERE "
                + AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME
                + "=?", new String[]{name});
        return cursor;
    }

    public Cursor getAllSubjects(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "
                +AttendanceContract.SubjectEntry.TABLE_NAME, null);
        return cursor;
    }

    public Integer deleteSubject(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(AttendanceContract.SubjectEntry.TABLE_NAME, AttendanceContract.SubjectEntry.COLUMN_NAME_SUBJECT_NAME
                + " = ? ", new String[]{name});
    }

    void deleteAllSubjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AttendanceContract.SubjectEntry.TABLE_NAME, null, null);
    }
}

