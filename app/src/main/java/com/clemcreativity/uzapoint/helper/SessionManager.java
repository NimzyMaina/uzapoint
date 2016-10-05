package com.clemcreativity.uzapoint.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by user on 5/6/2016.
 */
public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";


    // Shared preferences file name
    private static final String PREF_NAME = "UzaPoint";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn,String apikey,String fname,String lname) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_API_KEY,apikey);
        editor.putString(KEY_FNAME,fname);
        editor.putString(KEY_LNAME,lname);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public String getKeyApiKey(){
        return pref.getString(KEY_API_KEY,"");
    }
    public String getKeyFname(){
        return pref.getString(KEY_FNAME,"");
    }
    public String getKeyLname(){
        return pref.getString(KEY_LNAME,"");
    }
}
