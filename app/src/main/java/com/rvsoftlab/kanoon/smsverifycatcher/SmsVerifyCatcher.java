package com.rvsoftlab.kanoon.smsverifycatcher;

import android.Manifest;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.rvsoftlab.kanoon.helper.Constant;
import com.rvsoftlab.kanoon.helper.PermissionUtil;

/**
 * Created by RVishwakarma on 2/1/2018.
 */

public class SmsVerifyCatcher {
    private Activity activity;
    private Fragment fragment;
    private OnSmsCatchListener onSmsCatchListener;
    private SmsReceiver smsReceiver;
    private String phoneNumber;
    private String parseExpression;
    private PermissionUtil permission;
    public SmsVerifyCatcher(Activity activity, OnSmsCatchListener onSmsCatchListener) {
        this.activity = activity;
        this.onSmsCatchListener = onSmsCatchListener;
        this.smsReceiver = new SmsReceiver();
        this.smsReceiver.setCallback(this.onSmsCatchListener);
        permission = new PermissionUtil(activity);
    }

    public SmsVerifyCatcher(Activity activity, Fragment fragment, OnSmsCatchListener onSmsCatchListener) {
        this(activity, onSmsCatchListener);
        this.fragment = fragment;
        permission = new PermissionUtil(activity);
    }

    private void registerReceiver() {
        this.smsReceiver = new SmsReceiver();
        this.smsReceiver.setCallback(this.onSmsCatchListener);
        this.smsReceiver.setPhoneNumberFilter(this.phoneNumber);
        this.smsReceiver.setParseExpresion(this.parseExpression);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.activity.registerReceiver(this.smsReceiver, intentFilter);
    }

    public void setPhoneNumberFilter(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPatseExpression(String regexp) {
        this.parseExpression = regexp;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
    public boolean isStoragePermissionGranted(Activity activity, Fragment fragment) {
        final boolean[] isGranted = new boolean[1];
        permission.checkAndAskPermission(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, Constant.PERMISSION_CODE.SMS, new PermissionUtil.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                isGranted[0] = true;
                registerReceiver();
            }

            @Override
            public void onPermissionDenied() {
                isGranted[0] = false;
            }
        });
        return isGranted[0];
    }

    public void onStart() {
        if(isStoragePermissionGranted(this.activity, this.fragment)) {
            this.registerReceiver();
        }

    }

    public void onStop() {
        try {
            this.activity.unregisterReceiver(this.smsReceiver);
        } catch (IllegalArgumentException var2) {
            var2.printStackTrace();
        }

    }
}
