package com.example.studenthelpdesk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;
import androidx.annotation.Nullable;

public class MyDBHelpers extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "STUDENT_HELP_DESKS";
    static int DATABASE_VERSION = 13; // Increment the version number
    static String TABLE_NAME = "USERS";
    static final String KEY_userName = "userName";
    static final String KEY_ID = "id";
    static final String KEY_EMAIL = "email";
    static final String KEY_PASSWORD = "password";

    static String TABLE_NAME_LECTURERS = "LECTURERS";
    static final String KEY_LECTURER_NAME = "lecturer_userName";
    static final String KEY_LECTURER_ID = "lecturer_id";
    static final String KEY_LECTURER_EMAIL = "lecturer_email";
    static final String KEY_LECTURER_PASSWORD = "lecturer_password";

    static String TABLE_NAME_APPOINTMENTS = "APPOINTMENT";
    static final String KEY_APPOINTMENT_ID = "appointment_id";
    static final String KEY_APPOINTMENT_NAME = "name";
    static final String KEY_APPOINTMENT_EMAIL = "email";
    static final String KEY_STUDENT_ID = "student_id";
    static final String KEY_APPOINTMENT_DATE = "date";
    static final String KEY_APPOINTMENT_TIME = "time";
    static final String KEY_APPOINTMENT_DESCRIPTION = "description";
    static final String KEY_APPOINTMENT_STATUS = "status";
    static String TABLE_NAME_RATING = "RATING";
    static final String KEY_RATING_ID = "rating_id";
    static final String KEY_FEEDBACK = "feedback";
    static final String KEY_RATING = "rating";
    public MyDBHelpers(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_EMAIL + " TEXT, " + KEY_PASSWORD + " TEXT, " + KEY_userName + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_LECTURERS + "(" + KEY_LECTURER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_LECTURER_EMAIL + " TEXT, " + KEY_LECTURER_PASSWORD + " TEXT, " + KEY_LECTURER_NAME + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_APPOINTMENTS + "(" + KEY_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_APPOINTMENT_NAME + " TEXT, " + KEY_APPOINTMENT_EMAIL + " TEXT, " + KEY_STUDENT_ID + " TEXT, " + KEY_APPOINTMENT_STATUS + " TEXT, " + KEY_LECTURER_NAME + " TEXT, " + KEY_APPOINTMENT_DATE + " TEXT, " + KEY_APPOINTMENT_TIME + " TEXT, " + KEY_APPOINTMENT_DESCRIPTION + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_RATING + "(" +
                KEY_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_FEEDBACK + " TEXT, " +
                KEY_RATING + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 9) {
            // Drop the existing "APPOINTMENT" table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_APPOINTMENTS);

            // Create the "APPOINTMENT" table with the new structure
            db.execSQL("CREATE TABLE " + TABLE_NAME_APPOINTMENTS + "(" +
                    KEY_APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_APPOINTMENT_NAME + " TEXT, " +
                    KEY_APPOINTMENT_EMAIL + " TEXT, " +
                    KEY_STUDENT_ID + " TEXT, " +
                    KEY_LECTURER_NAME + " TEXT, " +
                    KEY_APPOINTMENT_DATE + " TEXT, " +
                    KEY_APPOINTMENT_TIME + " TEXT, " +
                    KEY_APPOINTMENT_DESCRIPTION + " TEXT, " +
                    KEY_APPOINTMENT_STATUS + " TEXT)");
        }

        if (oldVersion < 12) {
            // Drop the existing "RATING" table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RATING);

            // Create the "RATING" table with the new structure
            db.execSQL("CREATE TABLE " + TABLE_NAME_RATING + "(" +
                    KEY_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_FEEDBACK + " TEXT, " +
                    KEY_RATING + " INTEGER)");
        }
    }
    public void updateAppointmentStatus(String studentId, String currentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Toggle the status
        String newStatus;
        switch (currentStatus) {
            case "Pending":
                newStatus = "Approved";
                break;
            case "Approved":
                newStatus = "Rejected";
                break;
            case "Rejected":
            default:
                newStatus = "Pending";
                break;
        }

        values.put(KEY_APPOINTMENT_STATUS, newStatus);

        // Specify the WHERE clause to identify the appointment to be updated
        String whereClause = KEY_STUDENT_ID + " = ?";
        String[] whereArgs = {studentId};

        // Update the row
        db.update(TABLE_NAME_APPOINTMENTS, values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }
    public void insertRating(String feedback, int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_FEEDBACK, feedback);
        values.put(KEY_RATING, rating);

        // Insert the values into the 'RATING' table
        db.insert(TABLE_NAME_RATING, null, values);

        // Close the database connection
        db.close();
    }

}