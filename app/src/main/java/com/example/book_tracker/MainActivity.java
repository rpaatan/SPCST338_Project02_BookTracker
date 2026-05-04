package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // If use is logged in, go to LandingPage
        // - if user is not logged in, then remain on this page.
        if (isLoggedIn) {
            startActivity(LandingPage.landingPageIntentFactory(this));
            finish();
            return;
        }

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        // Set Create Account button listener
        // Set up just in case & for future use (?) just takes you to the create account page. - rhu
        binding.createAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Comment back in once SignUpActivity is created.
                startActivity(SignUpActivity.signUpIntentFactory(getApplicationContext()));
            }
        });
    }

    public static Intent mainActivityIntentFactory(Context applicationContext, int id) {
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra("userId", id);
        return intent;
    }
}