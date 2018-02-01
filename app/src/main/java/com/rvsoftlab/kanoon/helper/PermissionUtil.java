package com.rvsoftlab.kanoon.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by RVishwakarma on 2/1/2018.
 */

public class PermissionUtil {
    public interface OnPermissionListener{
        /**
         * Callbase Permission Granted
         */
        void onPermissionGranted();

        /**
         * Callback Permission Denied
         */
        void onPermissionDenied();
    }
    private static final int REQUEST_PERMISSION_SETTING = 1;
    private Activity activity;
    private int REQUEST_CODE;
    private OnPermissionListener callback;
    private SessionManager session;
    public PermissionUtil(Activity activity){
        this.activity = activity;
        session = new SessionManager(activity);
    }

    private boolean isPermissionNeeded(){
        return (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(Context context, String[] permission){
        boolean isPermissionGranted = false;
        for (String per : permission){
            isPermissionGranted = context.checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED;
        }
        return !isPermissionNeeded() || isPermissionGranted;
    }

    public void checkAndAskPermission(final String[] permission, int requestCode, OnPermissionListener listener){
        this.callback = listener;
        this.REQUEST_CODE = requestCode;
        if (!hasPermission(activity,permission)){
            if (shouldShowPermissionRational(activity,permission)){
                /**
                 * Show information about why permission needed
                 */
                new AlertDialog.Builder(activity)
                        .setTitle("Permission Needed!")
                        .setMessage("This feature needs special permission to perform\nPlease Grand Access to use this feature")
                        .setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity,permission,REQUEST_CODE);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }else if (session.getPermissionStatus(permission[0])){
                /**
                 * Previously Permission Request was canceled with 'Don't ask again'
                 * Redirect to setting after showing Information about why permission needed
                 */
                new AlertDialog.Builder(activity)
                        .setTitle("Permission Needed!")
                        .setMessage("Previously Permission Request was canceled with 'Don't ask again'\nPlease go to Setting and enable permission")
                        .setPositiveButton("GO TO SETTING", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                intent.setData(uri);
                                activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }else {
                /**
                 * Just Request Normal Permission
                 */
                session.putPermissionStatus(permission[0],true);
                ActivityCompat.requestPermissions(activity,permission,requestCode);
            }
        }else {
            this.callback.onPermissionGranted();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(REQUEST_CODE==requestCode){
            boolean valid = true;
            for (int grantResult : grantResults) {
                valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
            }
            if (valid) {
                callback.onPermissionGranted();
            }else {
                callback.onPermissionDenied();
            }
        }
    }

    private boolean shouldShowPermissionRational(Activity activity, String[] permission) {
        boolean shouldShow = false;
        for (String per : permission){
            shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(activity,per);
        }
        return shouldShow;
    }
}
