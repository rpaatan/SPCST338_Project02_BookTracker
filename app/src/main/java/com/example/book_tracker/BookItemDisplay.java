package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.database.entities.ToReadBook;
import com.example.book_tracker.databinding.ActivityBookItemDisplayBinding;

public class BookItemDisplay extends AppCompatActivity {
    private ActivityBookItemDisplayBinding binding;
    private BookTrackerRepository repository;
    private ToReadBook toReadBook;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra("title");

        binding = ActivityBookItemDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        toReadBook = repository.getBookByTitle(title);

        if(toReadBook == null){
            binding.displayBookItemTextView.setText("Book not found.");
            binding.markReadButton.setEnabled(false);
            return;
        }

        binding.displayBookItemTextView.setText(toReadBook.toString());

        binding.markReadButton.setOnClickListener(view -> {
            ReadBook readBook = new ReadBook(
                    0,
                    toReadBook.getTitle(),
                    toReadBook.getAuthor(),
                    toReadBook.getPageCount(),
                    toReadBook.getPublishDate()
            );

            repository.insertBook(readBook);
            repository.deleteBook(toReadBook);

            finish();
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
