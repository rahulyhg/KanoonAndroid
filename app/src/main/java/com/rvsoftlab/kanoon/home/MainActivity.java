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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.rvsoftlab.kanoon.BaseActivity;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.adapter.ViewPagerFragmentAdapter;
import com.rvsoftlab.kanoon.adapter.ViewPagerItemAdapter;

import java.io.File;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewPager mainViewPager;
    //private BottomNavigationView navigation;
    private ViewPagerFragmentAdapter pagerAdapter;
    private ViewPagerItemAdapter itemAdapter;


    private CameraView cameraView;
    private ImageButton btnCamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View toolView = LayoutInflater.from(this).inflate(R.layout.toolbar_main_layout,null);
        toolView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

        cameraView = findViewById(R.id.camera_preview);
        btnCamera = findViewById(R.id.btn_camera);
        cameraView.start();

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);
                Toast.makeText(MainActivity.this, "Image Taken", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
            }
        });
        setupMainViewPager();
        setupViewPager();


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraView != null) {
                    cameraView.capturePicture();
                }
            }
        });
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
