package com.example.studenthelpdesk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences; // Add this line


import androidx.appcompat.app.AppCompatActivity;

public class adminlogin extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    Button signUpButton;
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginButton = findViewById(R.id.loginBtn);
        Button studentloginButton=findViewById(R.id.studentLoginBtn);

        studentloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminlogin.this, studentlogin.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect user input
                String enteredEmail = emailInput.getText().toString();
                String enteredPassword = passwordInput.getText().toString();

                //important(email and password credential)
                if (enteredEmail.equals("chenteik_99@hotmail.com") && enteredPassword.equals("66888")) {

                    // Navigate to the main activity or another appropriate screen
                    Intent intent = new Intent(adminlogin.this, adminHomepage.class);
                    startActivity(intent);
                } else {
                    // Login failed, display an error message
                    Toast.makeText(adminlogin.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}