package com.example.book_tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.book_tracker.database.entities.BookTracker;

import java.util.List;

@Dao
public interface BookTrackerDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookTracker bookTracker);

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE)
    List<BookTracker> getAllRecords();

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE + " WHERE userId = :loggedInUserId")
    List<BookTracker> getRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + BookTrackerDatabase.BOOK_TRACKER_TABLE + " WHERE userId = :loggedInUserId")
    LiveData<List<BookTracker>> getRecordsByUserIdLiveData(int loggedInUserId);
}
