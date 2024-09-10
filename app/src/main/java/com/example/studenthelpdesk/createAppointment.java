package com.example.studenthelpdesk;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class createAppointment extends AppCompatActivity {
    private  MyDBHelpers dbHelper; // Assuming you have a database helper class like MyDBHelper
    EditText emailInput;
    EditText studentIDInput;
    EditText lecturerInput;
    EditText dateInput;
    EditText timeInput;
    EditText descriptionInput;

    private String time;
    private String date;
    private String lecturerName;

    public createAppointment(String time, String date, String lecturerName) {
        this.time = time;
        this.date = date;
        this.lecturerName = lecturerName;
    }

    public createAppointment() {
        // Default constructor with no parameters
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);
        Button homeBtn = findViewById(R.id.homeBtn);
        // Set an OnClickListener for the "Sign Up" button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the SignUpActivity
                Intent intent = new Intent(createAppointment.this, studentHomepage.class);
                startActivity(intent);
            }
        });

        dbHelper = new MyDBHelpers(this);

        // Find your UI elements inside the onCreate method
        emailInput = findViewById(R.id.emailInput);
        studentIDInput = findViewById(R.id.studentIDInput);
        RadioGroup lecturerRadioGroup = findViewById(R.id.lecturerRadioGroup);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        descriptionInput = findViewById(R.id.descriptionInput);


        Button createButton = findViewById(R.id.createBtn);
       
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve input data from UI elements
                String email = emailInput.getText().toString();
                String studentId = studentIDInput.getText().toString();
                String selectedLecturer = getSelectedLecturer(lecturerRadioGroup); // Get the selected lecturer
                String date = dateInput.getText().toString();
                String time = timeInput.getText().toString();
                String description = descriptionInput.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("userName", "default_username");
                String status ="pending";
                // Call the method to insert the appointment data into the database
                insertAppointments(username, email, studentId, selectedLecturer, date, time, description, status);
                Log.d("CreateAppointment", "Details inserted into the database: " +
                        "Name: " + username +
                        ", Email: " + email +
                        ", Student ID: " + studentId +
                        ", Lecturer Name: " + lecturerName +
                        ", Date: " + date +
                        ", Time: " + time +
                        ", Description: " + description);
                Log.d("createAppointment", "insertAppointments method called");


                // You can also add a Toast message to provide user feedback if needed
                Toast.makeText(createAppointment.this, "Appointment created and saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getSelectedLecturer(RadioGroup lecturerRadioGroup) {
        int selectedRadioButtonId = lecturerRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return selectedRadioButton.getText().toString();
        } else {
            return "No lecturer selected";
        }
    }

    public void insertAppointments(String username, String email, String studentId, String lecturerName, String date, String time, String description, String status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", username);
        values.put("email", email);
        values.put("student_id", studentId);
        values.put("lecturer_userName", lecturerName);
        values.put("date", date);
        values.put("time", time);
        values.put("description", description);
        values.put("status", status);
        long newRowId2 = db.insert("APPOINTMENT", null, values);
        if (newRowId2 != -1) {
            // Insertion was successful
            // You can also display a success message or perform additional actions
            Log.d("createAppointment", "Data inserted successfully");
        } else {
            // Insertion failed
            // You can display an error message or take appropriate action
            Log.e("createAppointment", "Data insertion failed");
        }
        db.close();
    }
}