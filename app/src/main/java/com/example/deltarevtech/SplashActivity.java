package com.example.deltarevtech;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler; // Required for the timer
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. MUST ADD THIS: Links the Java to your neon XML layout
        setContentView(R.layout.activity_splash);

        // 2. DELAY LOGIC: Wait 3 seconds so the user sees your logo
        new Handler().postDelayed(() -> {

            // THE "LOGIN ONLY ONCE" LOGIC
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // User session exists, go to Dashboard
                startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
            } else {
                // No user session, go to Login (MainActivity)
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            finish(); // Close SplashActivity so they can't "Go Back" to it

        }, 3000); // 3000ms = 3 Seconds
    }
}