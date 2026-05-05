package com.example.book_tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.database.entities.ToReadBook;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReadBook readBook);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToReadBook toReadBook);


    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE)
    List<ToReadBook> getAllRecords();

    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE + " WHERE username = :loggedInUsername")
    LiveData<List<ToReadBook>> getRecordsByUsername(String loggedInUsername);

    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE + " WHERE title = :bookTitle")
    ToReadBook getBookByTitle(String bookTitle);

    @Query("SELECT * FROM " + BookTrackerDatabase.READ_TABLE + " WHERE title = :bookTitle")
    ReadBook getReadBookByTitle(String bookTitle);

    @Delete
    void delete(ToReadBook toReadBook);

    @Delete
    void delete(ReadBook readBook);

}
