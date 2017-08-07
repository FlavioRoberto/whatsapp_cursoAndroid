package com.whatsapp_cursoandroid.activity.Helper;

import android.app.Activity;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.activity.login_activity;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

/**
 * Created by Admin on 07/08/2017.
 */

public class funcaoToolbar {

    private static FirebaseAuth auth;

    public static void selecionaMenu(Activity activity,int id_itemMenu){

        switch (id_itemMenu){
            case R.id.menu_sair : logout(activity);
            case R.id.menu_buscar: buscar();
            case R.id.menu_configuracoes: configuracoes();
            case R.id.menu_contatos : contatos();
        }

    }

    private static void contatos() {
    }

    private static void configuracoes() {
    }

    public static void  logout(Activity activity){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signOut();
        Intent intent = new Intent(activity,login_activity.class);
        activity.finish();
        activity.startActivity(intent);
    }

    private static void buscar(){}

}
