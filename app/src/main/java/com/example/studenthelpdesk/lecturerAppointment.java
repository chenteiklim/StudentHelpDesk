package com.example.studenthelpdesk;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class lecturerAppointment extends AppCompatActivity {
    private MyDBHelpers dbHelper;
    private LinearLayout appointmentsContainer;
    private TextView appointmentDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecturerappoinment);

        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(lecturerAppointment.this, lecturerHomepage.class);
                startActivity(logInIntent);
            }
        });

        dbHelper = new MyDBHelpers(this);

        // Find the TextView and LinearLayout in your lecturerappoinment.xml layout
        appointmentDataTextView = findViewById(R.id.appointmentDataTextView);
        appointmentsContainer = findViewById(R.id.appointmentsContainer);

        // Retrieve data from the database and display appointments initially
        retrieveAndDisplayAppointments();
    }

    private void updateStatus(final String studentId, final String newStatus) {
        dbHelper.updateAppointmentStatus(studentId, newStatus);

        // Introduce a delay using Handler to allow time for the database to update
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Refresh the UI after the delay
                retrieveAndDisplayAppointments();
            }
        }, 1000); // 1000 milliseconds (1 second) delay
    }

    private void retrieveAndDisplayAppointments() {
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

        String selection = "lecturer_userName = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query("APPOINTMENT", projection, selection, selectionArgs, null, null, null);

        // Clear the existing data
        appointmentsContainer.removeAllViews();
        appointmentDataTextView.setText("");

        while (cursor.moveToNext()) {
            @SuppressLint("Range") final String studentId = cursor.getString(cursor.getColumnIndex("student_id"));

            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String lecturerName = cursor.getString(cursor.getColumnIndex("lecturer_userName"));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex("time"));
            @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
            @SuppressLint("Range") final String status = cursor.getString(cursor.getColumnIndex("status"));

            // Create a new LinearLayout for each appointment
            LinearLayout appointmentLayout = new LinearLayout(this);
            appointmentLayout.setOrientation(LinearLayout.VERTICAL);
            appointmentLayout.setPadding(20, 16, 16, 16); // Add padding

            // Create a new TextView for each appointment
            TextView appointmentTextView = new TextView(this);
            appointmentTextView.setText(String.format("Name: %s\nEmail: %s\nStudent ID: %s\nLecturer Name: %s\nDate: %s\nTime: %s\nDescription: %s\nStatus: %s\n",
                    name, email, studentId, lecturerName, date, time, description, status));
            appointmentTextView.setTextSize(16); // Set text size

            // Create a new Button for each appointment
            Button approveButton = new Button(this);
            // Set button text and behavior based on the appointment status
            approveButton.setText(status); // Set text to the current status

            approveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Fetch the updated status from the database
                    String updatedStatus = getUpdatedStatus(studentId);

                    // Call a method to update the status based on the current status in the database
                    updateStatus(studentId, updatedStatus);
                }
            });

            // Add the TextView and Button to the LinearLayout
            appointmentLayout.addView(appointmentTextView);
            appointmentLayout.addView(approveButton);

            // Add the LinearLayout to the main container
            appointmentsContainer.addView(appointmentLayout);
        }

        cursor.close();
        db.close();
    }

    // Helper method to get the updated status from the database
    @SuppressLint("Range")
    private String getUpdatedStatus(String studentId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"status"};
        String selection = "student_id = ?";
        String[] selectionArgs = {studentId};
        Cursor cursor = db.query("APPOINTMENT", projection, selection, selectionArgs, null, null, null);
        String updatedStatus = "";
        if (cursor.moveToFirst()) {
            updatedStatus = cursor.getString(cursor.getColumnIndex("status"));
        }
        cursor.close();
        db.close();
        return updatedStatus;

    }
}