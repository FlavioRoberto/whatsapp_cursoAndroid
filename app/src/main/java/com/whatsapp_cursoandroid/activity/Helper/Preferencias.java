package com.whatsapp_cursoandroid.activity.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;


/**
 * Created by Admin on 03/08/2017.
 */

public class Preferencias {

    private SharedPreferences preferences ;
    private Context context;
    private final String EMAIL_USUARIO = "email";

    public Preferencias( Context context) {
        this.context = context;
        preferences =  context.getSharedPreferences("preferencias",context.MODE_PRIVATE);
    }

    public void putEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_USUARIO,email);
        editor.apply();
    }

    public String getEmail(){
        String result = preferences.getString(EMAIL_USUARIO,"");

        if(result != null){
            return result;
        }

        return "";
    }

}
