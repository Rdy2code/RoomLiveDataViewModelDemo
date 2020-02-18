package com.example.android.roomwordssample.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.roomwordssample.Entities.Word;
import com.example.android.roomwordssample.R;

import java.util.List;

/**
 * Adapter caches data and populates the RecyclerView. The inner ViewHolder class holds and manages
 * the view(s) for a single list item
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private List<Word> mWords;
    private LayoutInflater mInflater;

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the list item layout
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        //Create an instance of the ViewHolder passing in the inflated view
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        //Bind the data to the view
        if (mWords != null) {
            Word currentWord = mWords.get(position);
            holder.mWordItemView.setText(currentWord.getWord());

        } else {
            holder.mWordItemView.setText("no word");
        }
    }

    public void setWords (List<Word> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        //This method must respond to situation where there are no words yet
        if (mWords != null) {
            return mWords.size();
        } else {
            return 0;
        }
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        private final TextView mWordItemView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            mWordItemView = itemView.findViewById(R.id.textview);
        }
    }
}
