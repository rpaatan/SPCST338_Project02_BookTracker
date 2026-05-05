package com.example.book_tracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.book_tracker.databinding.ActivityRatingBinding;

public class RatingActivity extends AppCompatActivity {
    private ActivityRatingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.saveButton.setOnClickListener(view -> {
            float rating = binding.ratingBar.getRating();
            String review = binding.reviewEditText.getText().toString();

            Toast.makeText(this, "Saved rating: " + rating, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
