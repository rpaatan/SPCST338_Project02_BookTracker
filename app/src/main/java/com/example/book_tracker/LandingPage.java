package com.example.book_tracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    private ActivityLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Stored user data
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "User");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        // Display welcome message
        binding.welcomeMessageTextView.setText("Welcome, " + username + "!");

        // Display admin button if user is an admin
        if (isAdmin) {
            binding.adminButton.setVisibility(View.VISIBLE);
        } else {
            binding.adminButton.setVisibility(View.INVISIBLE);
        }

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the shared preferences to indicate the user is logged out
                updateSharedPreference("", false);

                // Go back to the MainActivity
                Intent intent = new Intent(LandingPage.this, MainActivity.class);
                // Avoid user to go back to landing page using back button
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, AdminPage.class));
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

    public static Intent landingPageIntentFactory(Context context){
        return new Intent(context, LandingPage.class);
    }

}