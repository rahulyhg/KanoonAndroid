package com.rvsoftlab.kanoon.news;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.rvsoftlab.kanoon.R;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsImageFragment extends Fragment {


    public NewsImageFragment() {
        // Required empty public constructor
    }

    public static NewsImageFragment newInstance(String url){
        NewsImageFragment fragment = new NewsImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("URL",url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            String url = getArguments().getString("URL");
            Glide.with(mActivity)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                    .into(newsImage);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    private Activity mActivity;
    private ImageView newsImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_image, container, false);

        newsImage = view.findViewById(R.id.news_image);

        return view;
    }

}
