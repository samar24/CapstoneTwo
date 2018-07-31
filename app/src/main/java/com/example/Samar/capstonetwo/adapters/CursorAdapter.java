package com.example.Samar.capstonetwo.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.dataBase.MyContract;
import com.squareup.picasso.Picasso;


public class CursorAdapter extends RecyclerView.Adapter<CursorAdapter.FavouriteViewHolder> {

    private Context context;
    private Cursor cursor;
    int position;

    private FavouriteAdapterClickHandler mClickHandler;

    public CursorAdapter(Context mContext, CursorAdapter.FavouriteAdapterClickHandler onClick) {
        this.context=mContext;
        this.mClickHandler=onClick;

    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycle_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new CursorAdapter.FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {

        cursor.moveToPosition(position);
        int titleIndex=cursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_TITLE);
        int imageIndex=cursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_IMAGE);
        String title=cursor.getString(titleIndex);
        String image=cursor.getString(imageIndex);

        holder.mTitleTextView.setText(title);
        Picasso.with(context).load(image).placeholder(R.drawable.reddit).into(holder.mImageView);


        //add tag
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }


    public class FavouriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public TextView mTitleTextView;
        public TextView mAuthorTextView;
        public ImageView mImageView;

        public FavouriteViewHolder(View view)
        {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.title);
            mAuthorTextView = (TextView) view.findViewById(R.id.author);
            mImageView = (ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            position = getAdapterPosition();

            mClickHandler.onClick(position);

        }
    }

    public interface FavouriteAdapterClickHandler
    {
        void onClick(int position);
    }

    public void setFavData(Cursor mCursor) {

        if (cursor == mCursor)
        {
            return;
        }
        Cursor temp = mCursor;
        cursor=mCursor;
        notifyDataSetChanged();



    }



}