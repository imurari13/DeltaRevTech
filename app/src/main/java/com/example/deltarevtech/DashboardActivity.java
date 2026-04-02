package com.example.deltarevtech;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide ActionBar for the custom Delta design
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard);

        // 1. UI Initialization
        TextView tvName = findViewById(R.id.tv_user_name);
        TextView tvEmail = findViewById(R.id.tv_user_email);
        ImageView ivProfile = findViewById(R.id.iv_user_profile);
        ImageView btnLogout = findViewById(R.id.btn_logout);
        View btnScan = findViewById(R.id.bottom_scan_area);

        // 2. Firebase User Data
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tvName.setText(user.getDisplayName() != null ? user.getDisplayName() : "Welcome!");
            tvEmail.setText(user.getEmail());
            if (user.getPhotoUrl() != null) {
                Glide.with(this).load(user.getPhotoUrl()).circleCrop().into(ivProfile);
            }
        }

        // 3. Logout Click (Ensuring it clears the activity stack)
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> signOutUser());
        }

        // 4. Scanner Click
        if (btnScan != null) {
            btnScan.setOnClickListener(v -> {
                Intent intent = new Intent(DashboardActivity.this, ScannerActivity.class);
                startActivity(intent);
            });
        }

        setupMenuRows();
    }

    private void setupMenuRows() {
        // Mapping names, icons, and URLs for Delta Publications
        setMenuRow(R.id.menu_home, "Home", R.drawable.ic_home,
                "https://deltapublications.in/");

        setMenuRow(R.id.menu_demo, "Demo", android.R.drawable.ic_media_play,
                "https://deltapublications.in/demo/");

        setMenuRow(R.id.menu_schedule, "Schedule", android.R.drawable.ic_menu_my_calendar,
                "https://deltapublications.in/schedule/");

        setMenuRow(R.id.menu_exam, "Grand Test", android.R.drawable.ic_menu_mylocation,
                "https://deltapublications.in/grand-test/");

        setMenuRow(R.id.menu_results, "Toppers List", android.R.drawable.ic_popup_reminder,
                "https://deltapublications.in/toppers-list/");

        setMenuRow(R.id.menu_contact, "Contact Us", android.R.drawable.ic_menu_call,
                "https://deltapublications.in/contact-us/");

        setMenuRow(R.id.menu_smart_pad, "Smart Exam Pad", android.R.drawable.ic_menu_edit,
                "https://deltapublications.in/");
    }

    /**
     * Helper method to set up each menu row with an icon, text, and a web link
     */
    private void setMenuRow(int viewId, String label, int iconResId, String url) {
        View row = findViewById(viewId);
        if (row != null) {
            TextView tv = row.findViewById(R.id.item_text);
            ImageView iv = row.findViewById(R.id.item_icon);

            if (tv != null) tv.setText(label);
            if (iv != null) iv.setImageResource(iconResId);

            row.setOnClickListener(v -> {
                Toast.makeText(this, "Opening " + label, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            });
        }
    }

    private void signOutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        // Clear activity stack so user can't go back after logout
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}