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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class studentlogin extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    Button signUpButton;
    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentloginpage);
        Button signUpButton = findViewById(R.id.signUpBtn);
        dbHelper = new MyDBHelpers(this); // Initialize your database helper

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);


        Button loginButton = findViewById(R.id.loginBtn);
        Button lecturerButton = findViewById(R.id.lecturerLoginBtn);
        Button adminLoginButton=findViewById(R.id.adminLoginBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentlogin.this, studentSignup.class);
                startActivity(intent);
            }
        });
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentlogin.this, adminlogin.class);
                startActivity(intent);
            }
        });
        lecturerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(studentlogin.this, lecturerLogin.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect user input
                String enteredEmail = emailInput.getText().toString();
                String enteredPassword = passwordInput.getText().toString();
                String hashedEnteredPassword = hashPassword(enteredPassword);

                // Query the database to verify login credentials
                try (SQLiteDatabase db = dbHelper.getReadableDatabase();
                     Cursor cursor = db.query(
                             MyDBHelpers.TABLE_NAME,
                             new String[]{MyDBHelpers.KEY_ID, MyDBHelpers.KEY_userName},
                             MyDBHelpers.KEY_EMAIL + " = ? AND " + MyDBHelpers.KEY_PASSWORD + " = ?",
                             new String[]{enteredEmail, hashedEnteredPassword},
                             null,
                             null,
                             null)) {

                    if (cursor.moveToFirst()) {
                        // Login was successful, fetch the username
                        @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(MyDBHelpers.KEY_userName));

                        // Save the username in SharedPreferences
                        saveUsernameInSharedPreferences(username);

                        // Navigate to the main activity or another appropriate screen
                        Intent intent = new Intent(studentlogin.this, studentHomepage.class);
                        startActivity(intent);
                    } else {
                        // Login failed, display an error message
                        Toast.makeText(studentlogin.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Handle exceptions, log, or notify the user
                    e.printStackTrace();
                }
            }
        });


    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder hash = new StringBuilder();
            for (byte b : hashBytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveUsernameInSharedPreferences(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", username);
        editor.apply();
    }
}