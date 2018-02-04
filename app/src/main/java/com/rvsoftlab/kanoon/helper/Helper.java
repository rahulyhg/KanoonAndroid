package com.rvsoftlab.kanoon.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RVishwakarma on 2/2/2018.
 */

public class Helper {
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else
        {
            if(connectivity != null)
            {

                NetworkInfo[] info = connectivity.getAllNetworkInfo();

                if (info != null) {

                    for (int i = 0; i < info.length; i++) {

                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                            return true;

                        }

                    }
                }
            }

        }

        return false;
    }

    public static boolean isResponseOk(Activity activity, JSONObject response){
        boolean isOk;
        try {
            if (response.getBoolean("success")){
                isOk = true;
            }else {
                isOk = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            isOk = false;
        }
        return isOk;
    }
}
