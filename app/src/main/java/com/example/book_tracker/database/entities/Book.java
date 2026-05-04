package com.example.book_tracker.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.book_tracker.database.BookTrackerDatabase;

import java.util.Objects;

@Entity(tableName = BookTrackerDatabase.BOOK_TRACKER_TABLE)
public class Book {
    //TODO: make the whole Book Tracker POJO
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String title;
    private String author;
    private String isbn;
    private int pageCount;
    private String startDate;
    private String endDate;
    private String rating;
    private String review;

    public Book(int userId, String title, String author, String isbn, int pageCount, String startDate, String endDate, String rating, String review) {
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.pageCount = pageCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.review = review;
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
                "ISBN: " + isbn + '\n' +
                "Page: " + pageCount +
                "\nStarted: " + startDate + '\n' +
                "Ended: " + endDate + '\n' +
                "Rating: " + rating + '\n' +
                "Review: " + review;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && userId == book.userId && pageCount == book.pageCount && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && Objects.equals(startDate, book.startDate) && Objects.equals(endDate, book.endDate) && Objects.equals(rating, book.rating) && Objects.equals(review, book.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, author, isbn, pageCount, startDate, endDate, rating, review);
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
