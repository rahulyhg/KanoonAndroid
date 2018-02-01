package com.rvsoftlab.kanoon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.rvsoftlab.kanoon.helper.ProgressDialog;

/**
 * Created by RVishwakarma on 1/31/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    public void showProgress(){
        progressDialog.show();
    }

    public void hideProgress(){
        progressDialog.dismiss();
    }
}
