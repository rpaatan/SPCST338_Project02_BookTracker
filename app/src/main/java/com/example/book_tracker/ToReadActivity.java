package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityToReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerList;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        repository = BookTrackerRepository.getRepository(getApplication());

        repository.getAllLogsByUsername("").observe(this, books -> {
            toRead_TitleList = new ArrayList<>();
            for (ToReadBook book : books) {
                toRead_TitleList.add(book.getTitle());
            }
            setAdapter();
        });

        binding.recyclerBackButton.setOnClickListener(view -> {
            finish();
        });

        binding.addBookButton.setOnClickListener(view -> {
            startActivity(BookItem.bookItemIntentFactory(this));
        });
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(toRead_TitleList, title -> {
            Intent intent = new Intent(ToReadActivity.this, BookItemDisplay.class);
            intent.putExtra("title", title);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    public static Intent ToReadActivityIntentFactory(Context context) {
        return new Intent(context, ToReadActivity.class);
    }
}
