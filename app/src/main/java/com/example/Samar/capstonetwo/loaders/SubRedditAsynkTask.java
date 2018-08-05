package com.example.Samar.capstonetwo.loaders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.Samar.capstonetwo.model.Data_;
import com.example.Samar.capstonetwo.utility.NetworkFunctions;

import java.util.List;




public class SubRedditAsynkTask  extends AsyncTask<List<Data_>, Void, List<Data_>> {

    private onRedditDeliver redditDeliver;
    private Context mContext;
     List<Data_> dataList;
    private List<Data_> SavedDataCopy;

    public SubRedditAsynkTask(onRedditDeliver redditDeliver, Context context) {

        this.redditDeliver = redditDeliver;
        mContext = context;
    }
    @Override
    protected List<Data_> doInBackground(List<Data_>[] lists) {
        try {
            NetworkFunctions networkFunctions = new NetworkFunctions(mContext);
            dataList = networkFunctions.redditNetwork();

            return dataList;
        } catch (Exception e) {
            return null;

        }

    }

    public interface onRedditDeliver {
        void  onRedditDeliver(List<Data_> children);

    }
    @Override
    protected void onPostExecute(List<Data_> SavedData) {
        super.onPostExecute(SavedData);
        SavedDataCopy = SavedData;
        redditDeliver.onRedditDeliver(SavedDataCopy);
    }



}