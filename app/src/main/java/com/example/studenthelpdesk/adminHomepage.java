package com.example.studenthelpdesk;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class adminHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhomepage);
        Button logoutBtn = findViewById(R.id.logoutBtn);
        Button studentTableBtn=findViewById(R.id.studentTableBtn);
        Button lecturerTableBtn=findViewById(R.id.lecturerTableBtn);
        Button appointmentTableBtn=findViewById(R.id.appointmentTableBtn);
        Button ratingTable=findViewById(R.id.ratingTableBtn);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the LoginActivity
                Intent logInIntent = new Intent(adminHomepage.this, adminlogin.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        studentTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(adminHomepage.this, studentTable.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        lecturerTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(adminHomepage.this, lecturerTable.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        appointmentTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(adminHomepage.this, appointmentTable.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
        ratingTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logInIntent = new Intent(adminHomepage.this, ratingTable.class); // Replace with your LoginActivity class
                startActivity(logInIntent);
            }
        });
    }
}

