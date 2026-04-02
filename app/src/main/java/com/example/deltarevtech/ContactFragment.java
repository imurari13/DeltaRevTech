package com.example.deltarevtech;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ContactFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Link our XML layout to this file
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        // Find our views
        EditText etName = view.findViewById(R.id.et_name);
        EditText etEmail = view.findViewById(R.id.et_email);
        EditText etMessage = view.findViewById(R.id.et_message);
        Button btnSubmit = view.findViewById(R.id.btn_submit);

        // Make the button do something
        btnSubmit.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String message = etMessage.getText().toString().trim();

            // Check if any fields are empty
            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
            } else {
                // If everything is filled out, show a success message and clear the form
                Toast.makeText(getContext(), "Message sent securely to Delta Rev Tech!", Toast.LENGTH_LONG).show();

                etName.setText("");
                etEmail.setText("");
                etMessage.setText("");
            }
        });

        return view;
    }
}