package com.example.studenthelpdesk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class lecturerTable extends AppCompatActivity {
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturertable);
        Button homeBtn = findViewById(R.id.homeBtn);
        Button clearRecordBtn = findViewById(R.id.clearRecordBtn);

        clearRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to clear all records
                clearAllRecords();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(lecturerTable.this, adminHomepage.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        dbHelper = new MyDBHelpers(this);

        // Find the TextView or any other UI elements in your appointmentStatus.xml layout
        TextView appointmentDataTextView = findViewById(R.id.StudentDataTextView);
        // Retrieve data from the database
        String studentData = retrieveStudentData();

        // Display the data in your TextView or any other UI elements
        appointmentDataTextView.setText(studentData.replace("\n", "\n\n"));
    }

    private String retrieveStudentData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
       "lecturer_userName", "lecturer_id", "lecturer_email", "lecturer_password"
        };

        // Perform the query with a selection condition for the specific username

        // Perform the query
        Cursor cursor = db.query("LECTURERS", projection, null, null, null, null, null);
        StringBuilder appointmentData = new StringBuilder();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String lecturerID = cursor.getString(cursor.getColumnIndex("lecturer_id"));
            @SuppressLint("Range") String lecturer_userName = cursor.getString(cursor.getColumnIndex("lecturer_userName"));
            @SuppressLint("Range") String lecturer_email = cursor.getString(cursor.getColumnIndex("lecturer_email"));
            @SuppressLint("Range") String lecturer_password = cursor.getString(cursor.getColumnIndex("lecturer_password"));


            // Append the retrieved data to the StringBuilder
            appointmentData.append("lecturer ID: ").append(lecturerID).append("\n");
            appointmentData.append("Lecturer userName: ").append(lecturer_userName).append("\n");
            appointmentData.append("Lecturer Email: ").append(lecturer_email).append("\n");
            appointmentData.append("Lecturer Password: ").append(lecturer_password).append("\n\n");
        }

        cursor.close();
        db.close();

        return appointmentData.toString();
    }
    private void clearAllRecords() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Specify the table to delete records from (in this case, "student")
        String tableName = "LECTURERS";

        // Use a SQL DELETE statement to delete all records from the table
        db.execSQL("DELETE FROM " + tableName);

        // Close the database connection
        db.close();

        // Notify the user that records have been cleared (optional)
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }


}