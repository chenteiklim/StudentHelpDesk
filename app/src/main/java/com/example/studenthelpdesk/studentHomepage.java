package com.example.studenthelpdesk;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;


public class studentHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenthomepage);


        ImageView resourceImg = findViewById(R.id.resourceImg);
        ImageView bookImg = findViewById(R.id.bookImg);
        ImageView appointmentImg = findViewById(R.id.appointmentImg);
        ImageView ratingImg = findViewById(R.id.ratingImg);

        Button logoutBtn = findViewById(R.id.logoutBtn);

        // Set an OnClickListener for the "Sign Up" button

        // Retrieve the userName from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = sharedPref.getString("userName", "DefaultUsername"); // "DefaultUsername" is a fallback if the username is not found

        // Find the TextView in your homepage.xml to display the userName
        TextView userNameTextView = findViewById(R.id.userName);
        userNameTextView.setText(userName); // Set the text to display the username


        bookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(studentHomepage.this, book.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        appointmentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(studentHomepage.this, appointment.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        resourceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(studentHomepage.this, resource.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        ratingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(studentHomepage.this, rating.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(studentHomepage.this, studentlogin.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
    }
}

