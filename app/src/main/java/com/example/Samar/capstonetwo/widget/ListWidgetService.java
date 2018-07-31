package com.example.Samar.capstonetwo.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.activities.FavouriteDetailActivity;
import com.example.Samar.capstonetwo.dataBase.MyContract;




public class ListWidgetService  extends  RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        Cursor mCursor;

        public ListRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (mCursor != null) mCursor.close();
            mCursor = mContext.getContentResolver().query(
                    MyContract.sunRedditEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onDestroy()
        {
            mCursor.close();
        }

        @Override
        public int getCount() {
            if (mCursor == null) return 0;
            return mCursor.getCount();
        }


        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0)
                return null;
            mCursor.moveToPosition(position);
            int authorIndex = mCursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_AUTHOR);
            int titleIndex = mCursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_TITLE);


            String author=mCursor.getString(authorIndex);
            String title=mCursor.getString(titleIndex);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.list_widget_item);


            views.setTextViewText(R.id.title, title);
            views.setTextViewText(R.id.author, author);

            // Fill in the onClick PendingIntent Template using the specific plant Id for each item individually
            Bundle extras = new Bundle();
            extras.putInt(FavouriteDetailActivity.EXTRA_CURSOR_POSITION,position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.linear_layout, fillInIntent);

            return views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1; // Treat all items in the GridView the same
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

