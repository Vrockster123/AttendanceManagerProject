package com.example.vrock.attendancemanager2;

import android.provider.BaseColumns;

/**
 * Created by vvvro on 1/30/2016.
 */
public final class AttendanceContract {
    public AttendanceContract() {}
    public static abstract class SubjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "subject";
        public static final String COLUMN_NAME_SUBJECT_NAME = "name";
        public static final String COLUMN_NAME_HOURS_ATTENDED="attended";
        public static final String COLUMN_NAME_HOURS_TOTAL="total";
        public static final String COLUMN_NAME_ATTENDANCE_PERCENTAGE = "percentage";
    }
}
