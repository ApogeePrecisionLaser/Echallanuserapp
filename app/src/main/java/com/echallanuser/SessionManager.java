package com.echallanuser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.echallanuser.database.DatabaseOperation;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "IPMS_Pref";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_MOBILE = "phone";
    public static final String KEY_PASSWORD = "password";

    DatabaseOperation dbTask;

    public SessionManager(Context context){
        this._context = context;
        dbTask=new DatabaseOperation(_context);
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */
    public void createLoginSession(String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_MOBILE, phone);
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));

        return user;
    }

    public boolean checkLogin(){
        boolean islogin=false;
        if(!this.isLoggedIn()){
//
        }else {
            islogin=true;


        }
        return islogin;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
