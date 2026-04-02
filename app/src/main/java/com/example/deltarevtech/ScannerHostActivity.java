package com.example.deltarevtech;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ScannerHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the top bar for a full-screen scanner look
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // 1. We create a simple container in code if you don't have a layout file
        android.widget.FrameLayout container = new android.widget.FrameLayout(this);
        container.setId(android.view.View.generateViewId());
        setContentView(container);

        // 2. Load your ScannerFragment into that container
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(container.getId(), new ScannerFragment())
                    .commit();
        }
    }
}