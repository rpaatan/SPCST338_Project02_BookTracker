package com.example.book_tracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.book_tracker.database.BookTrackerRepository;
import com.example.book_tracker.database.entities.User;
import com.example.book_tracker.databinding.ActivityAdminPageBinding;
import com.example.book_tracker.viewHolders.UserAdapter;

import java.util.List;

public class AdminPage extends AppCompatActivity {
    private ActivityAdminPageBinding binding;
    private BookTrackerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = BookTrackerRepository.getRepository(getApplication());

        // Set layout manager for RecyclerView
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up adapter for RecyclerView
        UserAdapter adapter = new UserAdapter(user -> {
            repository.deleteUser(user);
        });

        binding.userRecyclerView.setAdapter(adapter);

        LiveData<List<User>> usersLiveData = repository.getAllUsers();

        usersLiveData.observe(this, users -> {
            adapter.setUsers(users);
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the shared preferences to indicate the user is logged out
                updateSharedPreference("", false);

                // Go back to the MainActivity
                Intent intent = new Intent(AdminPage.this, MainActivity.class);
                // Avoid user to go back to landing page using back button
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void updateSharedPreference(String username, boolean isAdmin){
        SharedPreferences sharedPreferences = getApplicationContext()
                .getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putBoolean("isAdmin", isAdmin);
        editor.putBoolean("isLoggedIn", false);
        editor.apply();
    }
}