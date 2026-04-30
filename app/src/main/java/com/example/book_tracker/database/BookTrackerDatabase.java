package com.example.book_tracker.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.book_tracker.database.entities.BookTracker;
import com.example.book_tracker.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BookTracker.class, User.class}, version = 1, exportSchema = false)
public abstract class BookTrackerDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "BookTrackerDatabase";
    public static final String USER_TABLE = "usertable";
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
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("DAC_BOOKTRACKER", "DATABASE CREATED");
            databaseWriterExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                User admin = new User("admin2", "admin2");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    public abstract BookTrackerDAO booktrackerDAO();

    public abstract UserDAO userDAO();
}
