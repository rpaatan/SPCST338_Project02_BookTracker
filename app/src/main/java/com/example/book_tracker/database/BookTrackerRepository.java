package com.example.book_tracker.database;

import android.app.Application;
import android.util.Log;

import com.example.book_tracker.database.entities.BookTracker;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BookTrackerRepository {
    private final BookTrackerDAO bookTrackerDAO;
    private ArrayList<BookTracker> allLogs;

    public BookTrackerRepository(Application application){
        BookTrackerDatabase db = BookTrackerDatabase.getDatabase(application);
        this.bookTrackerDAO = db.booktrackerDAO();
        this.allLogs = (ArrayList<BookTracker>) this.bookTrackerDAO.getAllRecords();
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
}
