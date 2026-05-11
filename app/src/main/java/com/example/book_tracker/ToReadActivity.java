package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ToReadBook;
import com.example.book_tracker.databinding.ActivityToReadBinding;

import java.util.ArrayList;

public class ToReadActivity extends AppCompatActivity {
    private ArrayList<String> toRead_TitleList;
    private ActivityToReadBinding binding;
    private RecyclerView recyclerView;
    private BookTrackerRepository repository;
    private int userId;

    public static final String USER_ID_KEY = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityToReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerView = binding.recyclerList;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        repository = BookTrackerRepository.getRepository(getApplication());

        binding.addBookButton.setOnClickListener(view -> {
            Toast.makeText(ToReadActivity.this, "Add clicked userId = " + userId, Toast.LENGTH_SHORT).show();

            Intent intent = BookItem.bookItemIntentFactory(ToReadActivity.this, userId);
            startActivity(intent);
        });

        binding.recyclerBackButton.setOnClickListener(view -> {
            finish();
        });

        repository.getAllToReadBooks(userId).observe(this, books -> {
            toRead_TitleList = new ArrayList<>();

            if (books != null) {
                for (ToReadBook book : books) {
                    toRead_TitleList.add(book.getTitle());
                }
            }

            setAdapter();
        });
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(toRead_TitleList, title -> {
            startActivity(BookItemDisplay.bookItemDisplayIntentFactory(ToReadActivity.this, title, userId));
        });

        recyclerView.setAdapter(adapter);
    }

    public static Intent ToReadActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ToReadActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}
