package com.example.book_tracker.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.book_tracker.database.BookTrackerDatabase;

import java.util.Objects;

@Entity(tableName = BookTrackerDatabase.TO_READ_TABLE)
public class ToReadBook {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String author;

    public int userId;
    private int pageCount;
    private String publishDate;

    public ToReadBook(String title, String author, int userId, int pageCount, String publishDate) {
        this.title = title;
        this.author = author;
        this.userId = userId;
        this.pageCount = pageCount;
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return title + '\n' +
                "By: " + author + '\n' +
                "Page: " + pageCount +
                "\nPublished: " + publishDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ToReadBook toReadBook = (ToReadBook) o;
        return id == toReadBook.id && userId == toReadBook.userId && pageCount == toReadBook.pageCount && Objects.equals(title, toReadBook.title) && Objects.equals(author, toReadBook.author) && Objects.equals(publishDate, toReadBook.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, userId, pageCount, publishDate);
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
