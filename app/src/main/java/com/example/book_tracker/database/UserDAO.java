package com.example.book_tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.book_tracker.database.entities.BookTracker;
import com.example.book_tracker.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + BookTrackerDatabase.USER_TABLE)
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + BookTrackerDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + BookTrackerDatabase.USER_TABLE)
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + BookTrackerDatabase.USER_TABLE)
    LiveData<User> getUserByUserId(int userId);
}
