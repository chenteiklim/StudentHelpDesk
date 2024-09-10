package com.example.studenthelpdesk;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class studentSignup extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private EditText usernameInput;

    private MyDBHelpers dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentsignuppage);

        dbHelper = new MyDBHelpers(this);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmInput);
        usernameInput = findViewById(R.id.usernameInput);

        Button signUpButton=findViewById(R.id.signUpBtn);
        Button logInButton = findViewById(R.id.loginBtn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the SignUpActivity
                Intent intent = new Intent(studentSignup.this, studentlogin.class);
                startActivity(intent);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();
                String username = usernameInput.getText().toString(); // Add this line to retrieve the username.
                Log.d("User Input", "Email: " + email); // Log the email input

                if (!email.contains("@")) {
                    // Email is not valid, show an error message
                    Toast.makeText(studentSignup.this, "Invalid email format. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmPassword)) {
                    // Passwords do not match, show an error message
                    Toast.makeText(studentSignup.this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    // Passwords match, check if the email already exists in the database
                    if (isEmailExists(email)) {
                        // Email already exists, show an error message
                        Toast.makeText(studentSignup.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    } else if (isPasswordExists(password)) {
                        // Password already exists, show an error message
                        Toast.makeText(studentSignup.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                    else if (isUsernameExists(username)) {
                        // Username already exists, show an error message
                        Toast.makeText(studentSignup.this, "Username already exists. Please choose a different username.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String hashedPassword = hashPassword(password);


                        SQLiteDatabase database = dbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put("email", email);
                        values.put("password", hashedPassword);
                        values.put("username", username); // Add the username to the ContentValues.

                        long newRowId = database.insert("USERS", null, values);

                        if (newRowId != -1) {
                            // Insertion was successful
                            Log.d("Signup Activity", "Signup successful for user: " + username); // Log the username
                            SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("userName", username);
                            editor.apply();
                            Intent intent = new Intent(studentSignup.this, studentHomepage.class);
                            startActivity(intent);
                        } else {
                            // Insertion failed
                            Toast.makeText(studentSignup.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }

                        dbHelper.close();
                    }
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

    private boolean isEmailExists(String email) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE email = ?";
        Cursor cursor = database.rawQuery(query, new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private boolean isPasswordExists(String password) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE password = ?";
        Cursor cursor = database.rawQuery(query, new String[]{password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    private boolean isUsernameExists(String username) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM USERS WHERE username = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}