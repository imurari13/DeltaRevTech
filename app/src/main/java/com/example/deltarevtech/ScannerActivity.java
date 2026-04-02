package com.example.deltarevtech;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar for a clean scanner look
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        // 1. Create a container to hold the fragment
        android.widget.FrameLayout container = new android.widget.FrameLayout(this);
        container.setId(android.view.View.generateViewId());
        setContentView(container);

        // 2. Load your existing ScannerFragment into this Activity
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(container.getId(), new ScannerFragment())
                    .commit();
        }
    }
}