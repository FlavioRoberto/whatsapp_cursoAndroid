package com.whatsapp_cursoandroid.activity.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.activity.Model.Usuario;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;


/**
 * Created by Admin on 03/08/2017.
 */

public class Preferencias {

    private SharedPreferences preferences ;
    private Context context;
    private final String EMAIL_USUARIO = "email", ID_USUARIO = "idUsuario",NOME_USUARIO="nomeUSER";

    public Preferencias( Context context) {
        this.context = context;
        preferences =  context.getSharedPreferences("preferencias", Context.MODE_PRIVATE);
    }

    public void putEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(EMAIL_USUARIO,email);
        editor.apply();
        String idUsuario = Base64ToString.criptografa(email);
        editor.putString(ID_USUARIO,idUsuario);
        editor.apply();
    }

    public void putNome(String nome){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NOME_USUARIO,nome);
        editor.commit();
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

    public String getNomeUsuario(){
        return  preferences.getString(NOME_USUARIO,"");

    }

}
