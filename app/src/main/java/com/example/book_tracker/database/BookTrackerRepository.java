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
    private ArrayList<ToReadBook> allLogs;

    private static BookTrackerRepository repository;

    public BookTrackerRepository(Application application){
        BookTrackerDatabase db = BookTrackerDatabase.getDatabase(application);
        this.bookDAO = db.bookDAO();
        this.userDAO = db.userDAO();
    }

    public static BookTrackerRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<BookTrackerRepository> future = BookTrackerDatabase.databaseWriterExecutor.submit(
                new Callable<BookTrackerRepository>() {
                    @Override
                    public BookTrackerRepository call() throws Exception {
                        return new BookTrackerRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d("DAC_BOOKTRACKER", "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<ToReadBook> getAllLogs() {
        Future<ArrayList<ToReadBook>> future = BookTrackerDatabase.databaseWriterExecutor.submit(
                new Callable<ArrayList<ToReadBook>>() {
                    @Override
                    public ArrayList<ToReadBook> call() throws Exception {
                        return (ArrayList<ToReadBook>) bookDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.i("DAC_BOOKTRACKER", "Problem when getting all Books in the repository");
        }
        return null;
    }

    public void insertBook(ToReadBook toReadBook){
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            bookDAO.insert(toReadBook);
        });
    }

    public void insertBook(ReadBook readBook){
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            bookDAO.insert(readBook);
        });
    }

    public void deleteBook(ToReadBook toReadBook){
        BookTrackerDatabase.databaseWriterExecutor.execute(()->{
            bookDAO.delete(toReadBook);
        });
    }

    public void deleteBook(ReadBook readBook){
        BookTrackerDatabase.databaseWriterExecutor.execute(()->{
            bookDAO.delete(readBook);
        });
    }

    public void insertUser(User... user){
        BookTrackerDatabase.databaseWriterExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public ToReadBook getBookByTitle(String bookTitle){
        return bookDAO.getBookByTitle(bookTitle);
    }

    public LiveData<List<ToReadBook>> getAllLogsByUsername(String loggedInUsername){
        return bookDAO.getAllToReadBooks();
    }

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }
    public void deleteUser(User user) {
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            userDAO.delete(user);
        });
    }

    public ReadBook getReadBookByTitle(String bookTitle) {
        return bookDAO.getReadBookByTitle(bookTitle);
    }

    public void updateBook(ReadBook book) {
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            bookDAO.update(book);
        });
    }

    public LiveData<List<ReadBook>> getAllReadBooks() {
        return bookDAO.getAllReadBooks();
    }
}
