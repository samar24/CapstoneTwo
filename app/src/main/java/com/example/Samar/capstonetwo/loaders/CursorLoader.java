package com.example.Samar.capstonetwo.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.example.Samar.capstonetwo.dataBase.MyContract;


public class CursorLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private onFavDeliver onFavDeliver;
    private Context mContext;


    public CursorLoader(onFavDeliver Deliver, Context context) {


        this.onFavDeliver= Deliver;
        this.mContext = context;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return  new AsyncTaskLoader<Cursor>(mContext) {
            Cursor mData = null;

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try {
                    return mContext.getContentResolver().query(MyContract.sunRedditEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

                } catch (Exception e) {
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mData = data;
                super.deliverResult(data);
            }

        };

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        onFavDeliver.onDeliver(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        onFavDeliver.onDeliver(null);

    }

    public interface onFavDeliver {
        void  onDeliver(Cursor cursor);
    }
}


