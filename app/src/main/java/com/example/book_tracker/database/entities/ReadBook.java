package com.example.book_tracker.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.book_tracker.database.BookTrackerDatabase;

import java.util.Objects;

@Entity(tableName = BookTrackerDatabase.READ_TABLE)
public class ReadBook {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String title;
    private String author;
    private int pageCount;
    private String startDate;
    private String endDate;

    public ReadBook(int userId, String title, String author, int pageCount, String startDate, String endDate) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.startDate = startDate;
        this.endDate = endDate;
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

    @NonNull
    @Override
    public String toString() {
        return title + '\n' +
                "By: " + author + '\n' +
                "Page: " + pageCount +
                "\nStarted: " + startDate + '\n' +
                "Ended: " + endDate + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReadBook readBook = (ReadBook) o;
        return id == readBook.id && userId == readBook.userId && pageCount == readBook.pageCount && Objects.equals(title, readBook.title) && Objects.equals(author, readBook.author) && Objects.equals(startDate, readBook.startDate) && Objects.equals(endDate, readBook.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, author, pageCount, startDate, endDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
