package com.example.book_tracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.database.entities.ToReadBook;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insert(ReadBook readBook);

    @Insert
    void insert(ToReadBook toReadBook);

    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE + " WHERE userId = :userId")
    List<ToReadBook> getAllRecords(int userId);

    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE + " WHERE userId = :userId")
    LiveData<List<ToReadBook>> getAllToReadBooks(int userId);

    @Query("SELECT * FROM " + BookTrackerDatabase.TO_READ_TABLE + " WHERE title = :bookTitle AND userId = :userId LIMIT 1")
    ToReadBook getBookByTitle(String bookTitle, int userId);

    @Query("SELECT * FROM " + BookTrackerDatabase.READ_TABLE + " WHERE title = :bookTitle AND userId = :userId LIMIT 1")
    ReadBook getReadBookByTitle(String bookTitle, int userId);

    @Query("SELECT * FROM " + BookTrackerDatabase.READ_TABLE + " WHERE userId = :userId")
    LiveData<List<ReadBook>> getAllReadBooks(int userId);

    @Delete
    void delete(ToReadBook toReadBook);

    @Delete
    void delete(ReadBook readBook);

    @Update
    void update(ReadBook readBook);
}
