package com.example.Samar.capstonetwo.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.activities.DetailActivity;
import com.example.Samar.capstonetwo.adapters.RedditAdapter;
import com.example.Samar.capstonetwo.loaders.SubRedditLoader;
import com.example.Samar.capstonetwo.model.Data_;


import java.util.ArrayList;
import java.util.List;
public class HomeFragment  extends Fragment implements RedditAdapter.RedditAdapterViewHolderOnClickHandler,SubRedditLoader.onRedditDeliver {

    private RecyclerView mRecyclerView;
    private RedditAdapter recipesAdapter;
    // private RecipesAdapter recipesAdapter;
    private static final int SUBREDDIT_LOADER_ID = 0;
    private ImageView imageView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mLayoutManagerSavedState;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        imageView = (ImageView) rootView.findViewById(R.id.image);

        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
        }

        new SubRedditAsynkTask(this,getActivity()).execute();
        return rootView;

    }

    public void loadAdapterWithData(ArrayList<Data_> dataList) {


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
        recipesAdapter = new RedditAdapter(getContext(), this, dataList);
        recipesAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(recipesAdapter);


    }

    @Override
    public void onRedditDeliver(List<Data_> children) {

        if (children != null) {
            loadAdapterWithData((ArrayList) children);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(SUBREDDIT_LOADER_ID, null, new SubRedditLoader(this, getActivity())).forceLoad();

        if (mLayoutManagerSavedState != null) {
            mLayoutManager.onRestoreInstanceState(mLayoutManagerSavedState);

        }

    }

    @Override
    public void onClick(Data_ child) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("Object", child);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Call some material design APIs here
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    null);
            startActivity(intent, options.toBundle());

            //Reenter activity
            Explode enterTransition = new Explode();
            Transition transition = enterTransition.setDuration(300);
            getActivity().getWindow().setReenterTransition(transition);


            //Slide exitTransition = new Slide();
            //  exitTransition.setDuration(100);
            // exitTransition.setSlideEdge(Gravity.TOP);
            // getActivity().getWindow().setReenterTransition(exitTransition);
        } else {
            startActivity(intent);
        }


    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("State", mLayoutManager.onSaveInstanceState());
    }
}




