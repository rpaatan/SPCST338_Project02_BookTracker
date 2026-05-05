package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.databinding.ActivityReadBinding;
import com.example.book_tracker.databinding.ActivityReadBinding;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    // TODO: replace with a list of titles from the DAO.
    private ArrayList<String> Read_TitleList;
    private ActivityReadBinding binding;
    private RecyclerView recyclerView;
    private BookTrackerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerList;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        repository = BookTrackerRepository.getRepository(getApplication());

        repository.getAllReadBooks().observe(this, books -> {
            Read_TitleList = new ArrayList<>();
            for (ReadBook book : books) {
                Read_TitleList.add(book.getTitle());
            }
            setAdapter();
        });

        binding.recyclerBackButton.setOnClickListener(view -> {
            startActivity(LandingPage.landingPageIntentFactory(this));
            finish();
        });
    }
    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(Read_TitleList, title -> {
            Intent intent = new Intent(ReadActivity.this, ReadBookDetailActivity.class);
            intent.putExtra("title", title);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    public static Intent ReadActivityIntentFactory(Context applicationContext) {
        Intent intent = new Intent(applicationContext, ReadActivity.class);
        return intent;
    }
}