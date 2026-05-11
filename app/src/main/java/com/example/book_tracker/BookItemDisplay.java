package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        int userId = getIntent().getIntExtra("USER_ID", -1);
        toReadBook = repository.getBookByTitle(title, userId);

        if(toReadBook == null){
            binding.displayBookItemTextView.setText("Book not found.");
            binding.markReadButton.setEnabled(false);
            return;
        }

        binding.displayBookItemTextView.setText(toReadBook.toString());

        binding.markReadButton.setOnClickListener(view -> {
            ReadBook readBook = new ReadBook(
                    userId,
                    toReadBook.getTitle(),
                    toReadBook.getAuthor(),
                    toReadBook.getPageCount(),
                    toReadBook.getPublishDate()
            );

            repository.insertBook(readBook);
            repository.deleteBook(toReadBook);

            Toast.makeText(this, "Book marked as read", Toast.LENGTH_SHORT).show();

            startActivity(ReadActivity.ReadActivityIntentFactory(BookItemDisplay.this, userId));
            finish();

            finish();
        });

        binding.backButton.setOnClickListener(view -> {
            finish();
        });
    }

    static Intent bookItemDisplayIntentFactory(Context context, String title, int userId){
        Intent intent = new Intent(context, BookItemDisplay.class);
        intent.putExtra("title", title);
        intent.putExtra("USER_ID", userId);
        return intent;
    }
}
