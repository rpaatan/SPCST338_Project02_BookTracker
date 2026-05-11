package com.example.book_tracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.databinding.ActivityRatingBinding;

public class RatingActivity extends AppCompatActivity {
    private ActivityRatingBinding binding;
    private BookTrackerRepository repository;
    private ReadBook book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        String title = getIntent().getStringExtra("title");
        int userId = getIntent().getIntExtra("USER_ID", -1);

        book = repository.getReadBookByTitle(title, userId);

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

            Toast.makeText(this, "Rating saved", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
