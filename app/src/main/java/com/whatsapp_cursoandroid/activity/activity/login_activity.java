package com.whatsapp_cursoandroid.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.whatsapp_cursoandroid.R;

public class login_activity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void redirecionaCadastro(View view){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);
    }
}
