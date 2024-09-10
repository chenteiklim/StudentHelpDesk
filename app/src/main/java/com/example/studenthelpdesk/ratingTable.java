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




public class ratingTable extends AppCompatActivity {
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratingtable);
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
                Intent logInIntent = new Intent(ratingTable.this, adminHomepage.class); // Replace with your LoginActivity class
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
         "rating_id",
        "feedback",
        "rating"
        };

        // Perform the query with a selection condition for the specific username

        // Perform the query
        Cursor cursor = db.query("RATING", projection, null, null, null, null, null);
        StringBuilder appointmentData = new StringBuilder();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String rating_id = cursor.getString(cursor.getColumnIndex("rating_id"));
            @SuppressLint("Range") String feedback= cursor.getString(cursor.getColumnIndex("feedback"));
            @SuppressLint("Range") String rating = cursor.getString(cursor.getColumnIndex("rating"));

            // Append the retrieved data to the StringBuilder
            appointmentData.append("Rating_id: ").append(rating_id).append("\n");
            appointmentData.append("feedback: ").append(feedback).append("\n");
            appointmentData.append("rating: ").append(rating).append("\n");
         ;
        }

        cursor.close();
        db.close();

        return appointmentData.toString();
    }
    private void clearAllRecords() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Specify the table to delete records from (in this case, "student")
        String tableName = "RATING";

        // Use a SQL DELETE statement to delete all records from the table
        db.execSQL("DELETE FROM " + tableName);

        // Close the database connection
        db.close();

        // Notify the user that records have been cleared (optional)
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }


}