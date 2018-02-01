package com.rvsoftlab.kanoon.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.rvsoftlab.kanoon.BaseActivity;
import com.rvsoftlab.kanoon.R;
import com.rvsoftlab.kanoon.helper.Constant;
import com.rvsoftlab.kanoon.smsverifycatcher.OnSmsCatchListener;
import com.rvsoftlab.kanoon.smsverifycatcher.SmsVerifyCatcher;

public class LoginActivity extends BaseActivity {
    private SmsVerifyCatcher smsVerifyCatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //region SMS VERIFICATION
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener() {
            @Override
            public void onSmsCatch(String code) {

            }
        });
        smsVerifyCatcher.setPatseExpression("[^0-9]");
        smsVerifyCatcher.setPhoneNumberFilter(Constant.SMS_SENDER);
        //endregion
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

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
