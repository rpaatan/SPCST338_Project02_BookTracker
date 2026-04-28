package com.example.BookTracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.BookTracker.MAIN_ACTIVITY_USER_ID";
    boolean isAdmin;

    Button button_Reading;
    Button button_ToRead;
    Button button_Read;


    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logInUser();

        if(loggedInUserId == -1) {
//            Intent intent = LoginActivity.LoginIntentFactory(getApplication());
//            startActivity(intent);
        }
    }

    private void logInUser(){
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId); 
        return intent; 
    }
}