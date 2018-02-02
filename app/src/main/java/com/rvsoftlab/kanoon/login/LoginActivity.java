package com.rvsoftlab.kanoon.login;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rvsoftlab.kanoon.BaseActivity;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.adapter.ViewPagerItemAdapter;
import com.rvsoftlab.kanoon.helper.Constant;
import com.rvsoftlab.kanoon.smsverifycatcher.OnSmsCatchListener;
import com.rvsoftlab.kanoon.smsverifycatcher.SmsVerifyCatcher;
import com.rvsoftlab.kanoon.views.PinEditText;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {
    private SmsVerifyCatcher smsVerifyCatcher;
    private ViewPager viewPager;
    private ViewPagerItemAdapter adapter;
    private Activity mActivity = this;
    private Menu toolMenu;

    private Button btnStepOne;
    private EditText editMobile;

    private TextView txtMobile;
    private ImageButton btnEdit;
    private PinEditText editOtp;
    private Button btnResend;
    private ProgressBar progressBar;

    private ImageView userImage;
    private EditText editUserName;

    private AsyncHttpClient httpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region GENERAL SETUP
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }
        //endregion

        //region SMS VERIFICATION
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener() {
            @Override
            public void onSmsCatch(String code) {
                showProgress(true);
                editOtp.setText(code);
            }
        });
        smsVerifyCatcher.setPatseExpression("[^0-9]");
        smsVerifyCatcher.setPhoneNumberFilter(Constant.SMS_SENDER);
        //endregion

        //region VIEW PAGER
        viewPager = findViewById(R.id.login_viewpager);
        viewPager.setOffscreenPageLimit(2);
        adapter = new ViewPagerItemAdapter(mActivity);
        adapter.addView(R.id.step_one,"Step One");
        adapter.addView(R.id.step_two,"Step Two");
        adapter.addView(R.id.step_three,"Step Three");
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (toolMenu!=null){
                    switch (position){
                        case 0:
                            toolMenu.findItem(R.id.action_join).setVisible(true);
                            toolMenu.findItem(R.id.action_next).setVisible(false);
                            break;
                        case 1:
                            toolMenu.findItem(R.id.action_join).setVisible(false);
                            toolMenu.findItem(R.id.action_next).setVisible(true);
                            break;
                        case 2:
                            toolMenu.findItem(R.id.action_join).setVisible(false);
                            toolMenu.findItem(R.id.action_next).setVisible(true);
                            break;
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregion

        //region VARIABLE INIT
        httpClient = new AsyncHttpClient();

        btnStepOne = findViewById(R.id.btn_step_one);
        editMobile = findViewById(R.id.edit_mobile);

        txtMobile = findViewById(R.id.txt_mobile);
        btnEdit = findViewById(R.id.btn_edit);
        editOtp = findViewById(R.id.edit_otp);
        btnResend = findViewById(R.id.btn_resend);
        progressBar = findViewById(R.id.progress);

        userImage = findViewById(R.id.user_image);
        editUserName = findViewById(R.id.edit_user_name);
        //endregion

        //region LISTENERS
        btnStepOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStepOneOk()){
                    registerLoginUser();
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0,false);
            }
        });
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //endregion
    }

    private void showProgress(boolean isCancel) {
        final int[] sec = {0};
        final int oneMin = 60 * 1000; // 1 minute in milli seconds
        final int[] total = {0};
        progressBar.setProgress(0);
        progressBar.setMax(60*1000);

        CountDownTimer timer = new CountDownTimer(oneMin,1000){
            @Override
            public void onTick(long timePassed) {
                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", total[0]++*100);
                animation.setDuration(500); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
            }

            @Override
            public void onFinish() {

            }
        };
        if (!isCancel){
            timer.start();
        }else {
            timer.cancel();
        }
    }
    private void registerLoginUser() {
        RequestParams param = new RequestParams();
        param.put("tag","AUTH");
        param.put("mobile",editMobile.getText().toString());
        httpClient.post(Constant.API,param,new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                showProgress();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                hideProgress();
                Log.d(TAG,response.toString());
                showProgress(false);
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                hideProgress();
                Log.d(TAG,errorResponse.toString());
                showProgress(true);
            }
        });
    }

    private boolean isStepOneOk() {
        boolean isOk;
        if (TextUtils.isEmpty(editMobile.getText().toString())){
            isOk = false;
            editMobile.setError("Please Enter Mobile Number");
            editMobile.requestFocus();
        }else if (editMobile.getText().toString().length()<10){
            isOk = false;
            editMobile.setError("Please Enter Valid Mobile Number");
            editMobile.requestFocus();
        }else {
            isOk = true;
        }
        return isOk;
    }


    //region OPTION MENU
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_join);
        View joinView = menuItem.getActionView();
        Button join = joinView.findViewById(R.id.btn_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        MenuItem nextItem = menu.findItem(R.id.action_next);
        View nextView = nextItem.getActionView();
        Button next = nextView.findViewById(R.id.btn_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem()==1){

                }else if (viewPager.getCurrentItem()==2){

                }
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login,menu);
        toolMenu = menu;
        toolMenu.findItem(R.id.action_next).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    //endregion

    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
}
