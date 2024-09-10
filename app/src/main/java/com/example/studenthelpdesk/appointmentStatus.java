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
import androidx.appcompat.app.AppCompatActivity;




public class appointmentStatus extends AppCompatActivity {
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointmentstatus);
        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(appointmentStatus.this, studentHomepage.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        dbHelper = new MyDBHelpers(this);

        // Find the TextView or any other UI elements in your appointmentStatus.xml layout
        TextView appointmentDataTextView = findViewById(R.id.appointmentDataTextView);
        // Retrieve data from the database
        String appointmentData = retrieveAppointmentData();

        // Display the data in your TextView or any other UI elements
        appointmentDataTextView.setText(appointmentData.replace("\n", "\n\n"));
    }

    private String retrieveAppointmentData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "default_username");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "name",
                "email",
                "student_id",
                "lecturer_userName",
                "date",
                "time",
                "description",
                "status"
        };

        // Perform the query with a selection condition for the specific username
        String selection = "name = ?";
        String[] selectionArgs = {username};

        // Perform the query
        Cursor cursor = db.query("APPOINTMENT", projection, selection, selectionArgs, null, null, null);
        StringBuilder appointmentData = new StringBuilder();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String studentId = cursor.getString(cursor.getColumnIndex("student_id"));
            @SuppressLint("Range") String lecturerName = cursor.getString(cursor.getColumnIndex("lecturer_userName"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") String status = cursor.getString(cursor.getColumnIndex("status"));


            // Append the retrieved data to the StringBuilder
            appointmentData.append("Name: ").append(name).append("\n");
            appointmentData.append("Email: ").append(email).append("\n");
            appointmentData.append("Student ID: ").append(studentId).append("\n");
            appointmentData.append("Lecturer Name: ").append(lecturerName).append("\n");
            appointmentData.append("Date: ").append(date).append("\n");
            appointmentData.append("Time: ").append(time).append("\n");
            appointmentData.append("Description: ").append(description).append("\n");
            appointmentData.append("Status: ").append(status).append("\n\n");

        }

        cursor.close();
        db.close();

        return appointmentData.toString();
    }


}