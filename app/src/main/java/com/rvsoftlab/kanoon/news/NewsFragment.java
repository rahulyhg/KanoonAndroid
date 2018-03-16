package com.rvsoftlab.kanoon.news;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.adapter.ViewPagerFragmentAdapter;
import com.rvsoftlab.kanoon.helper.Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    public NewsFragment() {
        // Required empty public constructor
    }

    private Activity mActivity;
    private ViewPager viewPager;
    private ViewPagerFragmentAdapter adapter;
    private List<Fragment> arrayList;
    private AsyncHttpClient httpClient;
    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news, container, false);

        viewPager = view.findViewById(R.id.news_viewPager);
        arrayList = new ArrayList<>();

        httpClient = new AsyncHttpClient();

        populateList();
        return view;
    }

    private void populateList() {
        if(Helper.isNetworkAvailable(mActivity)){

        }
    }

}
