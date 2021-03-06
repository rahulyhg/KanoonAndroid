package com.rvsoftlab.kanoon.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by RVishwakarma on 2/1/2018.
 */

public class SessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "knpref";
    private Context _contex;
    private final String USERNAME = "username";

    public SessionManager(Context context){
        _contex = context;
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void putPermissionStatus(String permission, boolean status){
        editor.putBoolean(permission,status);
        editor.apply();
    }

    public boolean getPermissionStatus(String permission){
        return pref.getBoolean(permission,false);
    }

    public void setIsUsernameSet(boolean isUsernameSet){
        editor.putBoolean(USERNAME,isUsernameSet);
        editor.apply();
    }

    public boolean isUsernameSet(){
        return pref.getBoolean(USERNAME,false);
    }
}

