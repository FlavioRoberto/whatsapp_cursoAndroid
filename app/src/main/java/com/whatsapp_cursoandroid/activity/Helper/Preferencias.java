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
    private String idUsuario;
    private final String EMAIL_USUARIO = "email", ID_USUARIO = "idUsuario";

    public Preferencias( Context context) {
        this.context = context;
        preferences =  context.getSharedPreferences("preferencias",context.MODE_PRIVATE);
    }

    public void putEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL_USUARIO,email);
        String idUsuario = Base64ToString.criptografa(email);
        editor.putString(ID_USUARIO,idUsuario);
        editor.apply();
    }

    public String getEmail(){
        String result = preferences.getString(EMAIL_USUARIO,"");

        if(result != null){
            return result;
        }

        return "";
    }

    public String getIdUsuario(){
        String result = preferences.getString(ID_USUARIO,"");

        if(result != null){
            return result;
        }

        return "";
    }
}
