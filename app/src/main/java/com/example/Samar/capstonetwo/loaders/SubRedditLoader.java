package com.example.Samar.capstonetwo.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.Samar.capstonetwo.model.Data_;
import com.example.Samar.capstonetwo.utility.NetworkFunctions;

import java.util.List;




public class SubRedditLoader implements LoaderManager.LoaderCallbacks<List<Data_>> {

    private onRedditDeliver redditDeliver;
    private Context mContext;
    private List<Data_> dataList;


    public SubRedditLoader(onRedditDeliver redditDeliver, Context context) {

        this.redditDeliver = redditDeliver;
        mContext = context;
    }





    @Override
    public Loader<List<Data_>> onCreateLoader(final int id, Bundle args) {
        return new AsyncTaskLoader<List<Data_>>(mContext) {

            @Override
            public List<Data_> loadInBackground() {

             try {
                NetworkFunctions networkFunctions = new NetworkFunctions(getContext());
                dataList = networkFunctions.redditNetwork();
                return dataList;
            } catch (Exception e)
            {
                return null;
            }
        }
        };
    }




    @Override
    public void onLoadFinished(Loader<List<Data_>> loader, List<Data_> data) {



            redditDeliver.onRedditDeliver(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Data_>> loader) {
        redditDeliver.onRedditDeliver(null);

    }

    public interface onRedditDeliver {
        void  onRedditDeliver(List<Data_> children);

    }

}
