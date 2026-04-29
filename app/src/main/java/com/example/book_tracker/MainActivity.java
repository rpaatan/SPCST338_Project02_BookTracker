package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: Get SharedPreferences

        // TODO: Check if user is already logged in

        // TODO: If use is logged in, go to LandingPage

        // TODO: Set login button listener

        // TODO: Set Create Account button listener
        // Just comment out the CreateAccountActivity,
        // I don't think we need to implement a functional createAcc activity



    }

    public static Intent mainActivityIntentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }
}