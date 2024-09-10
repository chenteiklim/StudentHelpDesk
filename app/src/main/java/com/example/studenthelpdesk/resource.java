package com.example.studenthelpdesk;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class resource extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource);
        Button homeBtn = findViewById(R.id.homeBtn);
        // Set an OnClickListener for the "Sign Up" button
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to switch to the SignUpActivity
                Intent intent = new Intent(resource.this, studentHomepage.class);
                startActivity(intent);
            }
        });

    }
}

