package com.example.Samar.capstonetwo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.model.Data_;
import com.squareup.picasso.Picasso;

import java.util.List;



public class RedditAdapter  extends RecyclerView.Adapter<RedditAdapter.RedditAdapterViewHolder> {

    private RedditAdapterViewHolderOnClickHandler mClickHandler;
    int Position;
    private List<Data_> mChildren;
    private Context mContext;


    public interface RedditAdapterViewHolderOnClickHandler {
        void onClick(Data_ child);
    }

    public RedditAdapter(Context context, RedditAdapterViewHolderOnClickHandler mClickHandler,List<Data_> data_s) {
        this.mClickHandler = mClickHandler;
        this.mContext = context;
        mChildren=data_s;
    }

    public class RedditAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView mTitleTextView;
        public ImageView mImageView;


        public RedditAdapterViewHolder(View view) {
            super(view);
            mTitleTextView = (TextView) view.findViewById(R.id.title);
            mImageView = (ImageView) view.findViewById(R.id.image);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Position = getAdapterPosition();
            Data_ child = mChildren.get(Position);
            mClickHandler.onClick(child);
        }
    }

    @Override
    public RedditAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recycle_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RedditAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RedditAdapter.RedditAdapterViewHolder holder, final int position) {
        final Data_ child = mChildren.get(position);
        holder.mTitleTextView.setText(child.getTitle());
        Picasso.with(mContext).load(child.getImage()).placeholder(R.drawable.reddit).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        if (null == mChildren)
            return 0;
        return mChildren.size();
    }



}

