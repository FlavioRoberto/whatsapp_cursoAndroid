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
    private final String EMAIL_USUARIO = "email", ID_USUARIO = "idUsuario",NOME_USUARIO="nomeUsuario";

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

    public void putNome() {
        final SharedPreferences.Editor editor = preferences.edit();
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        String Idusuario = getIdUsuario();
        if (Idusuario != null) {
         databaseReference.child("usuarios").child(Idusuario);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        editor.putString(NOME_USUARIO, usuario.getNome());
                        editor.apply();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public String getNomeUsuario(){
        String result = preferences.getString(NOME_USUARIO,"");

        if(result != null){
            return result;
        }

        return "";
    }
}
