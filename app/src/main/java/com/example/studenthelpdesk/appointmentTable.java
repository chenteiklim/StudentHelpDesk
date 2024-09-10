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




public class appointmentTable extends AppCompatActivity {
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmenttable);
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
                Intent logInIntent = new Intent(appointmentTable.this, adminHomepage.class); // Replace with your LoginActivity class
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
                "name",
                "email",
                "student_id",
                "lecturer_name",
                "date",
                "time",
                "description"
        };

        // Perform the query with a selection condition for the specific username

        // Perform the query
        Cursor cursor = db.query("APPOINTMENT", projection, null, null, null, null, null);
        StringBuilder appointmentData = new StringBuilder();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String lecturerName = cursor.getString(cursor.getColumnIndex("lecturer_name"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));



            // Append the retrieved data to the StringBuilder
            appointmentData.append("Name: ").append(name).append("\n");
            appointmentData.append("Email: ").append(email).append("\n");
            appointmentData.append("Student ID: ").append(studentId).append("\n");
            appointmentData.append("Lecturer Name: ").append(lecturerName).append("\n");
            appointmentData.append("Date: ").append(date).append("\n");
            appointmentData.append("Time: ").append(time).append("\n");
            appointmentData.append("Description: ").append(description).append("\n\n");
        }

        cursor.close();
        db.close();

        return appointmentData.toString();
    }
    private void clearAllRecords() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Specify the table to delete records from (in this case, "student")
        String tableName = "APPOINTMENT";

        // Use a SQL DELETE statement to delete all records from the table
        db.execSQL("DELETE FROM " + tableName);

        // Close the database connection
        db.close();

        // Notify the user that records have been cleared (optional)
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }


}