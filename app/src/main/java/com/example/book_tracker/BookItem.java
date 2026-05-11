package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ToReadBook;
import com.example.book_tracker.databinding.ActivityBookItemAddBinding;

public class BookItem extends AppCompatActivity {

    private String mTitle;
    private String mAuthor;
    private int mPageCount;
    private String mPublishDate;

    private int loggedInUserId;

    private ActivityBookItemAddBinding binding;
    private BookTrackerRepository repository;

    public static final String USER_ID_KEY = "USER_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookItemAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        binding.addBookButton.setOnClickListener(view -> {
            insertBookItem();
        });
    }

    private void insertBookItem() {
        getInformationFromDisplay();

        if (mTitle == null || mTitle.trim().isEmpty()) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (loggedInUserId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(
                this,
                "Saving: " + mTitle + " userId: " + loggedInUserId,
                Toast.LENGTH_SHORT
        ).show();

        ToReadBook book = new ToReadBook(
                mTitle,
                mAuthor,
                loggedInUserId,
                mPageCount,
                mPublishDate
        );

        repository.insertBook(book);

        Toast.makeText(this, "Book saved", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void getInformationFromDisplay() {
        mTitle = binding.titleInputEditText.getText().toString().trim();
        mAuthor = binding.authorInputEditText.getText().toString().trim();
        mPublishDate = binding.publishDateInputEditText.getText().toString().trim();

        try {
            String pageCountText = binding.pageCountInputEditText.getText().toString().trim();

            if (pageCountText.isEmpty()) {
                mPageCount = 0;
            } else {
                mPageCount = Integer.parseInt(pageCountText);
            }

        } catch (NumberFormatException e) {
            Log.d("DAC_BOOKTRACKER", "Error reading page count.");
            mPageCount = 0;
        }
    }

    static Intent bookItemIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, BookItem.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}
