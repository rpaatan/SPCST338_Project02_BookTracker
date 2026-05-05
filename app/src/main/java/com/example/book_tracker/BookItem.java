package com.example.book_tracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.ToReadBook;
import com.example.book_tracker.databinding.ActivityBookItemAddBinding;
import com.example.book_tracker.databinding.ActivityBookItemDisplayBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookItem extends AppCompatActivity {
    private String mTitle;
    private String mAuthor;
    private int mPageCount;
    private String mPublishDate;

    private String username;

    private ActivityBookItemAddBinding binding;
    private BookTrackerRepository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookItemAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());
        username = getResources().getString(R.string.username);

        binding.addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationFromDisplay();
                insertBookItem();
                toDisplay();
            }
        });
    }

    private void insertBookItem(){
        if(mTitle.isEmpty()){
            return;
        }

        ToReadBook book = new ToReadBook(mTitle,mAuthor,mPageCount,mPublishDate);
        repository.insertBook(book);
    }

    private void getInformationFromDisplay(){
        mTitle = binding.titleInputEditText.getText().toString();
        mAuthor = binding.authorInputEditText.getText().toString();
        mPublishDate = binding.publishDateInputEditText.getText().toString();
        try{
            mPageCount = Integer.parseInt(binding.pageCountInputEditText.getText().toString());
        }catch(NumberFormatException e){
            Log.d("DAC_BOOKTRACKER", "Error reading value from Page Count edit text.");
        }
    }

    //sends to display activity
    private void toDisplay(){
        Intent intent = BookItemDisplay.bookItemDisplayIntentFactory(this);
        intent.putExtra("title", mTitle);
        startActivity(intent);
    }

    static Intent bookItemIntentFactory(Context context){
        return new Intent(context, BookItem.class);
    }
}
