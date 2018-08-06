package com.example.Samar.capstonetwo.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.activities.FavouriteDetailActivity;
import com.example.Samar.capstonetwo.adapters.CursorAdapter;
import com.example.Samar.capstonetwo.loaders.CursorLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment  implements CursorAdapter.FavouriteAdapterClickHandler,CursorLoader.onFavDeliver {


    RecyclerView mRecyclerView;
    CursorAdapter favouriteAdapter;
    private static final int LOADER_ID = 4;
    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mLayoutManagerSavedState;
    public FavouriteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view_favourite);
        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
        }

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager=new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);

        } else if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager=new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);

        } else {
            mLayoutManager=new GridLayoutManager(getActivity(), 4);
            mRecyclerView.setLayoutManager(mLayoutManager);

        }

        favouriteAdapter = new CursorAdapter(getActivity(), this);
        mRecyclerView.setAdapter(favouriteAdapter);
        favouriteAdapter.notifyDataSetChanged();

        getLoaderManager().initLoader(LOADER_ID, null, new CursorLoader(this, getActivity())).forceLoad();
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(LOADER_ID, null, new CursorLoader(this, getActivity())).forceLoad();
        if (mLayoutManagerSavedState != null) {
            mLayoutManager.onRestoreInstanceState(mLayoutManagerSavedState);

        }

    }




    @Override
    public void onClick(int position) {

        Intent intent = new Intent(getActivity(), FavouriteDetailActivity.class);
        intent.putExtra("cursorPosition",position);
        startActivity(intent);
    }

    @Override
    public void onDeliver(Cursor cursor) {
        favouriteAdapter.setFavData(cursor);


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("State", mLayoutManager.onSaveInstanceState());
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mLayoutManagerSavedState != null) {
            mLayoutManager.onRestoreInstanceState(mLayoutManagerSavedState);
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Retrieve list state and list/item positions
        if(savedInstanceState != null)
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
    }
}