package com.rvsoftlab.kanoon.home;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rvsoftlab.kanoon.BaseActivity;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.adapter.ViewPagerFragmentAdapter;
import com.rvsoftlab.kanoon.adapter.ViewPagerItemAdapter;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewPager mainViewPager;
    //private BottomNavigationView navigation;
    private ViewPagerFragmentAdapter pagerAdapter;
    private ViewPagerItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View toolView = LayoutInflater.from(this).inflate(R.layout.toolbar_main_layout,null);
        toolView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(toolView);
        }

        //region VARIABLE DECLARATION
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
       //navigation = findViewById(R.id.bottom_navigation);

        mainViewPager = findViewById(R.id.main_view_pager);
        viewPager.setOffscreenPageLimit(1);
        //endregion
        setupMainViewPager();
        setupViewPager();
    }

    private void setupMainViewPager() {
        itemAdapter = new ViewPagerItemAdapter(this);
        itemAdapter.addView(R.id.main_view,"Main View");
        itemAdapter.addView(R.id.camera_view,"Camera View");
        mainViewPager.setAdapter(itemAdapter);
    }

    private void setupViewPager() {
        pagerAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new HomeFragment(),"Home");
        viewPager.setAdapter(pagerAdapter);
    }
}
