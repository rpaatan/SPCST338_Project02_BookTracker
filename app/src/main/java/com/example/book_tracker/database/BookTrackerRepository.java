package com.example.book_tracker.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.book_tracker.database.entities.BookTracker;
import com.example.book_tracker.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookTrackerRepository {
    private final BookTrackerDAO bookTrackerDAO;
    private final UserDAO userDAO;
    private ArrayList<BookTracker> allLogs;

    private static BookTrackerRepository repository;

    public BookTrackerRepository(Application application){
        BookTrackerDatabase db = BookTrackerDatabase.getDatabase(application);
        this.bookTrackerDAO = db.booktrackerDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<BookTracker>) this.bookTrackerDAO.getAllRecords();
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

    public ArrayList<BookTracker> getAllLogs() {
        Future<ArrayList<BookTracker>> future = BookTrackerDatabase.databaseWriterExecutor.submit(
                new Callable<ArrayList<BookTracker>>() {
                    @Override
                    public ArrayList<BookTracker> call() throws Exception {
                        return (ArrayList<BookTracker>) bookTrackerDAO.getAllRecords();
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

    public void insertBook(BookTracker bookTracker){
        BookTrackerDatabase.databaseWriterExecutor.execute(()-> {
            bookTrackerDAO.insert(bookTracker);
        });
    }
    //TODO: add a deletebook from database - need to do it in BookDAO too

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

    public LiveData<List<BookTracker>> getAllLogsByUserIdLiveData(int loggedInUserId){
        return bookTrackerDAO.getRecordsByUserIdLiveData(loggedInUserId);
    }
}
