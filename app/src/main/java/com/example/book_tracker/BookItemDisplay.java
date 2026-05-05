package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.databinding.ActivityBookItemDisplayBinding;

public class BookItemDisplay extends AppCompatActivity {

    private ActivityBookItemDisplayBinding binding;
    private BookTrackerRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        binding = ActivityBookItemDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = BookTrackerRepository.getRepository(getApplication());
        assert repository != null;
        binding.displayBookItemTextView.setText(repository.getBookByTitle(title).toString());

        binding.markReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: make it move to the Read table
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LandingPage.landingPageIntentFactory(getApplicationContext()));
            }
        });
    }

    static Intent bookItemDisplayIntentFactory(Context context){
        return new Intent(context, BookItemDisplay.class);
    }
}
