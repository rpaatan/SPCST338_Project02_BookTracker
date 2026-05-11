package com.example.book_tracker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.book_tracker.database.entities.ReadBook;
import com.example.book_tracker.database.entities.ToReadBook;
import com.example.book_tracker.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookTrackerRepository {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    private static BookTrackerRepository repository;

    public BookTrackerRepository(Application application){
        BookTrackerDatabase db = BookTrackerDatabase.getDatabase(application);
        this.bookDAO = db.bookDAO();
        this.userDAO = db.userDAO();
    }

    public static BookTrackerRepository getRepository(Application application){
        if(repository == null){
            repository = new BookTrackerRepository(application);
        }
        return repository;
    }

    public ArrayList<ToReadBook> getAllLogs(int userId) {
        Future<ArrayList<ToReadBook>> future =
                BookTrackerDatabase.databaseWriterExecutor.submit(() ->
                        new ArrayList<>(bookDAO.getAllRecords(userId))
                );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("DAC_BOOKTRACKER", "Problem getting all books in the repository");
        }
        return null;
    }

    public LiveData<List<ToReadBook>> getAllToReadBooks(int userId) {
        return bookDAO.getAllToReadBooks(userId);
    }

    public LiveData<List<ReadBook>> getAllReadBooks(int userId) {
        return bookDAO.getAllReadBooks(userId);
    }

    public ToReadBook getBookByTitle(String bookTitle, int userId){
        Future<ToReadBook> future =
                BookTrackerDatabase.databaseWriterExecutor.submit(() ->
                        bookDAO.getBookByTitle(bookTitle, userId));
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.i("DAC_BOOKTRACKER", "Problem getting to-read book by title");
        }
        return null;
    }

    public ReadBook getReadBookByTitle(String bookTitle, int userId){
        Future<ReadBook> future =
                BookTrackerDatabase.databaseWriterExecutor.submit(() ->
                        bookDAO.getReadBookByTitle(bookTitle, userId));
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i("DAC_BOOKTRACKER", "Problem getting read book by title");
        }
        return null;
    }

    public void insertBook(ToReadBook toReadBook) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            bookDAO.insert(toReadBook);
        });
    }

    public void insertBook(ReadBook readBook) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            bookDAO.insert(readBook);
        });
    }

    public void deleteBook(ToReadBook toReadBook) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            bookDAO.delete(toReadBook);
        });
    }

    public void deleteBook(ReadBook readBook) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            bookDAO.delete(readBook);
        });
    }

    public void updateBook(ReadBook book) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            bookDAO.update(book);
        });
    }

    public void insertUser(User... user) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void deleteUser(User user) {
        BookTrackerDatabase.databaseWriterExecutor.execute(() -> {
            userDAO.delete(user);
        });
    }
}
