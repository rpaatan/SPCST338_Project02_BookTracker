package com.example.book_tracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LandingPage extends AppCompatActivity {

    private Button logoutButton;
    private Button adminButton;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        logoutButton = findViewById(R.id.logoutButton);
        adminButton = findViewById(R.id.adminButton);
        welcomeMessage = findViewById(R.id.welcomeMessageTextView);

        // Stored user data
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Display welcome message
        welcomeMessage.setText("Welcome, " + username + "!");

        // Display admin button if user is an admin
        if (isAdmin) {
            adminButton.setVisibility(View.VISIBLE);
        } else {
            adminButton.setVisibility(View.INVISIBLE);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the shared preferences to indicate the user is logged out
                updateSharedPreference("", false);

                // Go back to the MainActivity
                Intent intent = new Intent(LandingPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // TODO: Handle admin button click
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle admin button click
            }
        });
    }

    private void updateSharedPreference(String username, boolean isAdmin){
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putBoolean("isAdmin", isAdmin);
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }
}