package com.example.android.roomwordssample.Entities;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//LiveData is a lifecycle library class for data observation. Use a return value of LiveData<T> to
//have app respond to data changes

//Room uses the DAO to issue queries to its database. Room is a database layer on top of an SQLite
//database. Room takes care of tasks that used to be handled by SQLiteOpenHelper.

//Usually you only need one instance of Room for the entire app.

//Room does not allow queries on the main thread. LiveData applies this rule, running queries
//asynchronously on a background thread.

//Room class must be abstract and extend RoomDatabase

//The Dao wraps method calls to database queries. The result returned by the Dao is a observed LiveData

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Word word);

    @Query("DELETE FROM word_table")
    void deleteAll ();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
