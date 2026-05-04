package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.User;
import com.example.book_tracker.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private BookTrackerRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUser();
            }
        });

    }
    //check if the user already exists then adds them to database
    private void checkUser(){
        String username = binding.userNameSignUpEditText.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username shouldn't be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            //if the user doesn't exist in the database, add it
            if(user == null){
                String password = binding.passwordSignUpEditText.getText().toString();
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                    // Go to Login
                    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                    finish();
            }else{
                toastMaker(String.format("%s is already taken", username));
                //TODO: what does this do?
                binding.userNameSignUpEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignUpActivity.class);
    }
}
