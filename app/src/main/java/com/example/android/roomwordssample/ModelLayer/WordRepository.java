package com.example.android.roomwordssample.ModelLayer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.android.roomwordssample.Entities.Word;
import com.example.android.roomwordssample.Entities.WordDao;

import java.util.List;

/**
 * Manage query threads and manage backends
 */

public class WordRepository {

    //Member variables for Dao and List of objects
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    //Constructor to get a handle to the database and initialize member variables
    public WordRepository (Application application) {

        //Get a handle to the database
        WordRoomDatabase db = WordRoomDatabase.getDatabse(application);

        //With the database, get a handle to the Dao
        this.mWordDao = db.wordDao();

        //With the Dao, call an SQL method
        mAllWords = mWordDao.getAllWords();

    }

    //LiveData wrapper that returns the words list in a LiveData object. LiveData notifies the observer
    //when the data changes. Room executes all queries on a background thread
    public LiveData<List<Word>> getAllWords () {
        return mAllWords;
    }

    public void insert (Word word) {
        //Construct a new asynctask for the insert method passing it a WordDao object
        //Then execute the task passing in a Word object
        new InsertAsyncTask (mWordDao).execute(word);
    }

    /**
     * Inner AsyncTask class for insertion method
     */
    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        public InsertAsyncTask (WordDao wordDao) {
            this.mAsyncTaskDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }


}
