package com.whatsapp_cursoandroid.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Application.Mascaras;

public class LoginActivity extends AppCompatActivity {
    private EditText codigoPostal, DDD, telefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instanciando componentes
        codigoPostal = (EditText)findViewById(R.id.EditTextCodigoPostal);
        DDD = (EditText)findViewById(R.id.EditTextDDD);
        telefone = (EditText)findViewById(R.id.EditTextTelefone);

        //aplicando mascaras
        aplicaMascara();


    }

    private void aplicaMascara(){
        Mascaras.mascaraCodigoPostal(codigoPostal);
        Mascaras.mascaraDDD(DDD);
        Mascaras.mascaraTelefone(telefone);
    }
}
