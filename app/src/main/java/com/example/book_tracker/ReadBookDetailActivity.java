package com.example.book_tracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.databinding.ActivityReadBookDetailBinding;

public class ReadBookDetailActivity extends AppCompatActivity {
    private ActivityReadBookDetailBinding binding;
    private BookTrackerRepository repository;
    private ReadBook book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadBookDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        String bookTitle = getIntent().getStringExtra("title");
        book = repository.getReadBookByTitle(bookTitle);

        if (book == null) {
            binding.displayTitleTextView.setText("Book not found.");
            binding.saveButton.setEnabled(false);
            return;
        }

        binding.displayTitleTextView.setText(book.toString());

        binding.saveButton.setOnClickListener(view -> {
            book.setRating(binding.ratingBar.getRating());
            book.setReview(binding.reviewEditText.getText().toString());

            repository.updateBook(book);
            finish();
        });
    }
}
