package com.example.book_tracker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.book_tracker.database.entities.Book;
import com.example.book_tracker.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookTrackerRepository {
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private ArrayList<Book> allLogs;

    private static BookTrackerRepository repository;

    public BookTrackerRepository(Application application){
        BookTrackerDatabase db = BookTrackerDatabase.getDatabase(application);
        this.bookDAO = db.bookDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<Book>) this.bookDAO.getAllRecords();
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

    public ArrayList<Book> getAllLogs() {
        Future<ArrayList<Book>> future = BookTrackerDatabase.databaseWriterExecutor.submit(
                new Callable<ArrayList<Book>>() {
                    @Override
                    public ArrayList<Book> call() throws Exception {
                        return (ArrayList<Book>) bookDAO.getAllRecords();
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

    public void insertGymLog(Book book){
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            bookDAO.insert(book);
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

    public LiveData<List<Book>> getAllLogsByUserIdLiveData(int loggedInUserId){
        return bookDAO.getRecordsByUserIdLiveData(loggedInUserId);
    }
}
