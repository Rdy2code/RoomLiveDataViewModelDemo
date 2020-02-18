package com.example.android.roomwordssample.ModelLayer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.roomwordssample.Entities.Word;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    //region Member Variables
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;
    //endregion

    //Constructor to get a reference to the Repository and get a list of words from the repository
    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //Getter method that hides implementation from the UI. The list of words observed by the UI
    //So the UI does not interact directly with the Repository. The ViewModel interacts with the
    //Repo throught the methods exposed by the Repo
    public LiveData<List<Word>> getmAllWords() {
        return mAllWords;
    }

    public void insert (Word word) {
        mRepository.insert(word);
    }


}
