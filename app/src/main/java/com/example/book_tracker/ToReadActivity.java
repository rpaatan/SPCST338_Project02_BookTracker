package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_tracker.databinding.ActivityDisplayRecyclerBinding;

import java.util.ArrayList;

public class ToReadActivity extends AppCompatActivity {
    // TODO: replace with a list of titles from the DAO.
    private ArrayList<String> toRead_TitleList;

    private ActivityDisplayRecyclerBinding binding;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDisplayRecyclerBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_display_recycler);

        recyclerView = findViewById(R.id.recyclerList);

        toRead_TitleList = new ArrayList<>();
        setTitlesTemp();

        setAdapter();
    }

    private void setAdapter() {
        RecyclerAdapter adapter = new RecyclerAdapter(toRead_TitleList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);
    }

    public void setTitlesTemp() {
        toRead_TitleList.add("unread title 1");
        toRead_TitleList.add("unread title 2");
        toRead_TitleList.add("unread title 3");
        toRead_TitleList.add("unread title 4");
        toRead_TitleList.add("unread title 5");
        toRead_TitleList.add("unread title 6");
        toRead_TitleList.add("unread title 7");

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
