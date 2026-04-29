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
    // Constant Fields
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.book_tracker.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;
    private BookTrackerRepository repository;

    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        // Get SharedPreferences
        updateSharedPreference();

        // Check if user is already logged in
        // If use is logged in, go to LandingPage
        // - if user is not logged in, then remain on this page.
        if (userIsLoggedIn()) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Button Listeners
        // Set login button listener
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
//                startActivity(SignUpActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        // Just comment out the CreateAccountActivity,
        // I don't think we need to implement a functional createAcc activity
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    private boolean userIsLoggedIn() {
        return loggedInUserId == -1;
    }

    private void logInUser() {
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    public static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);

        // TODO: Uncomment once UserID is functional.
//        intent.putExtra(MAIN_ACTIVITY_USER_ID, userID);

        return intent;
    }
}