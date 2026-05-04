package com.example.book_tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.book_tracker.database.entities.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE)
    List<Book> getAllRecords();

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE + " WHERE userId = :loggedInUserId")
    List<Book> getRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE + " WHERE userId = :loggedInUserId")
    LiveData<List<Book>> getRecordsByUserIdLiveData(int loggedInUserId);
}
