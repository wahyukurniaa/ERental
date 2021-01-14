package com.wahyukurnia.erental;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int Private_mode=0;

    private static final String PREF_NAME = "data_app";

    public PrefManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Private_mode);
        editor = pref.edit();
    }

    public void setIdUser(String idUser){
        editor.putString("id_user", idUser);
        editor.apply();
    }
    public String getIdUser(){
        return pref.getString("id_user","");
    }

    public void setLoginStatus(boolean isLogin){
        editor.putBoolean("Login", isLogin);
        editor.apply();
    }
    public boolean getLoginStatus(){
        return pref.getBoolean("Login",false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context,LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
