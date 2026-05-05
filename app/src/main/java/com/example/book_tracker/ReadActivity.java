package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_tracker.databinding.ActivityDisplayRecyclerBinding;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {
    // TODO: replace with a list of titles from the DAO.
    private ArrayList<String> Read_TitleList;

    private ActivityDisplayRecyclerBinding binding;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayRecyclerBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_display_recycler);

        recyclerView = findViewById(R.id.recyclerList);

        Read_TitleList = new ArrayList<>();
        setTitlesTemp();

        setAdapter();
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(Read_TitleList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    public void setTitlesTemp() {
        Read_TitleList.add("read title 1");
        Read_TitleList.add("read title 2");
        Read_TitleList.add("read title 3");
        Read_TitleList.add("read title 4");
        Read_TitleList.add("read title 5");
        Read_TitleList.add("read title 6");
        Read_TitleList.add("read title 7");

    }

    // TODO: unsure of what's wrong here, should take you back to the landing page.
//    binding.recyclerBackButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            startActivity(LandingPage.landingPageIntentFactory(getApplicationContext()));
//        }
//    });

    public static Intent ToReadyActivityIntentFactory(Context applicationContext) {
        Intent intent = new Intent(applicationContext, ToReadActivity.class);
        return intent;
    }
}