package com.example.studenthelpdesk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class rating extends AppCompatActivity {


    private EditText feedbackEditText;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private Button submitButton;
    private MyDBHelpers myDBHelpers;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);



        // Initialize views
        feedbackEditText = findViewById(R.id.feedbackEditText);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox5 = findViewById(R.id.checkbox5);
        submitButton = findViewById(R.id.submitButton);
        db = openOrCreateDatabase("STUDENT_HELP_DESKS", MODE_PRIVATE, null);

        myDBHelpers = new MyDBHelpers(this);
        createRatingTable();

        Button homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the SignUpActivity
                Intent intent = new Intent(rating.this, studentHomepage.class);
                startActivity(intent);
            }
        });
        // Set click listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the feedback text
                String feedback = feedbackEditText.getText().toString().trim();

                // Get the selected rating from checkboxes
                int selectedRating = getSelectedCheckboxIndex();

                if (feedback.isEmpty()) {
                    // Show an error message if feedback is empty
                    feedbackEditText.setError("Feedback cannot be empty");
                    feedbackEditText.requestFocus();
                } else {
                    // Save feedback and rating to the database
                    saveFeedbackAndRating(feedback, selectedRating);

                    // Display a toast message indicating that the feedback is submitted
                    Toast.makeText(rating.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();

                    // Clear the feedback text and uncheck all checkboxes
                    feedbackEditText.getText().clear();
                    uncheckAllCheckboxes();
                }
            }
        });

        // Set click listeners for checkboxes
        setCheckboxClickListeners();
    }
    private void createRatingTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS RATING (" +
                "rating_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "feedback TEXT, " +
                "rating INTEGER)");
    }
    private void saveFeedbackAndRating(String feedback, int rating) {
        // Call the method in your database helper to insert the feedback and rating into the 'RATING' table
        myDBHelpers.insertRating(feedback, rating);
    }
    // Helper method to save feedback and rating to the database


    // Helper method to get the index of the selected checkbox
    private int getSelectedCheckboxIndex() {
        if (checkBox1.isChecked()) {
            return 1;
        } else if (checkBox2.isChecked()) {
            return 2;
        } else if (checkBox3.isChecked()) {
            return 3;
        } else if (checkBox4.isChecked()) {
            return 4;
        } else if (checkBox5.isChecked()) {
            return 5;
        } else {
            return 0; // No checkbox selected
        }
    }

    // Helper method to uncheck all checkboxes
    private void uncheckAllCheckboxes() {
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
    }

    // Helper method to set click listeners for checkboxes
    private void setCheckboxClickListeners() {
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllCheckboxesExcept(checkBox1);
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllCheckboxesExcept(checkBox2);
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllCheckboxesExcept(checkBox3);
            }
        });

        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllCheckboxesExcept(checkBox4);
            }
        });

        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uncheckAllCheckboxesExcept(checkBox5);
            }
        });
    }

    // Helper method to uncheck all checkboxes except the provided one
    private void uncheckAllCheckboxesExcept(CheckBox checkboxToKeepChecked) {
        checkBox1.setChecked(checkBox1 == checkboxToKeepChecked);
        checkBox2.setChecked(checkBox2 == checkboxToKeepChecked);
        checkBox3.setChecked(checkBox3 == checkboxToKeepChecked);
        checkBox4.setChecked(checkBox4 == checkboxToKeepChecked);
        checkBox5.setChecked(checkBox5 == checkboxToKeepChecked);
    }
}
