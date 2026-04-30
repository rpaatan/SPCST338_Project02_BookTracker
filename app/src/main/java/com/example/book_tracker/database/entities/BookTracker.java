package com.example.book_tracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.book_tracker.database.BookTrackerDatabase;

@Entity(tableName = BookTrackerDatabase.BOOK_TRACKER_TABLE)
public class BookTracker {
    //TODO: make the whole Book Tracker POJO
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    public BookTracker(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
