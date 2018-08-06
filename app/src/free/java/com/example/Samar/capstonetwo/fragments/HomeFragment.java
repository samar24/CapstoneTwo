package com.example.Samar.capstonetwo.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.activities.DetailActivity;
import com.example.Samar.capstonetwo.adapters.RedditAdapter;
import com.example.Samar.capstonetwo.loaders.SubRedditAsynkTask;
import com.example.Samar.capstonetwo.model.Data_;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment  extends Fragment implements RedditAdapter.RedditAdapterViewHolderOnClickHandler,SubRedditAsynkTask.onRedditDeliver {

    private RecyclerView mRecyclerView;
    private RedditAdapter recipesAdapter;

    private InterstitialAd mInterstitialAd;

    private ImageView imageView;
    private Intent intent;
    private RecyclerView.LayoutManager mLayoutManager;
    private Parcelable mLayoutManagerSavedState;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //interstitial ads
        mInterstitialAd=new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));
        AdRequest adRequestInterstitial = new AdRequest.Builder()
                .addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequestInterstitial);


    }

    @Override
    public void onResume() {
        super.onResume();
        AdRequest adRequestInterstitial = new AdRequest.Builder()
                .addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequestInterstitial);
        if (mLayoutManagerSavedState != null) {
         mLayoutManager.onRestoreInstanceState(mLayoutManagerSavedState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        imageView = (ImageView) rootView.findViewById(R.id.image);
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
        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
        }
        //banner ads
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        // mAdView.setAdListener(new myAdListener(getActivity()));
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //interstitial ads

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
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
        });




        new SubRedditAsynkTask(this,getActivity()).execute();

        return rootView;

    }

    public void loadAdapterWithData(ArrayList<Data_> dataList) {



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
      //  new SubRedditAsynkTask(this,getActivity()).execute();

    }


    @Override
    public void onClick(Data_ child) {

        intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("Object", child);
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else
        {
            AdRequest adRequestInterstitial = new AdRequest.Builder()
                    .addTestDevice(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mInterstitialAd.loadAd(adRequestInterstitial);
        }




    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("State", mLayoutManager.onSaveInstanceState());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Retrieve list state and list/item positions
        if(savedInstanceState != null)
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
        mRecyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mLayoutManagerSavedState = savedInstanceState.getParcelable("State");
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mLayoutManagerSavedState);
        }
    }
}


