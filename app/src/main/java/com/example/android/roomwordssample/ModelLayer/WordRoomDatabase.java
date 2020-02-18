package com.example.android.roomwordssample.ModelLayer;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.roomwordssample.Entities.Word;
import com.example.android.roomwordssample.Entities.WordDao;

import java.io.InputStream;

/**
 * The database for this app. Each entity represents a table
 */

@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    //Provide and abstract getter method for each @Dao
    //This handle lets you call SQL methods with the WordDao class
    public abstract WordDao wordDao();

    //Singleton instance variable for the database
    private static WordRoomDatabase INSTANCE;

    //For a real app, must implement a non-destructive migration strategy
    //Or destroy dbase each time schema changes with .fallbackToDestructiveMigration()
    //See https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    //Use singleton pattern to create an instance of the database
    public static WordRoomDatabase getDatabse (final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    //Create Database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addMigrations(MIGRATION_1_2)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Delete all content and repopulate the database whenever the app is started.
     */

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                //Start a new AsyncTask to add content to the database
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsyncTask(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        String[] words = {"dolphin", "crocodile", "cobra"};

        public PopulateDbAsyncTask(WordRoomDatabase instance) {
            mDao = instance.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Delete and recreate the dbase each time the activity is created
            mDao.deleteAll();
            for (int i = 0; i <= words.length - 1; i++) {
                Word word = new Word(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }
}
