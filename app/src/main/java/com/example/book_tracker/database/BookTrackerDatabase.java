package com.example.book_tracker.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.book_tracker.database.entities.BookTracker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookTracker.class}, version = 1, exportSchema = false)
public abstract class BookTrackerDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "BookTracker_database";
    public static final String BOOK_TRACKER_TABLE = "bookTrackerTable";

    private static volatile BookTrackerDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static BookTrackerDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    BookTrackerDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i("DAC_BOOKTRACKER", "DATABASE CREATED");
        }
    };

    public abstract BookTrackerDAO booktrackerDAO();
}
