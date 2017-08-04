package com.whatsapp_cursoandroid.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Button btnDeslogar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDeslogar = (Button)findViewById(R.id.BtnDeslogar);

        auth = ConfiguracaoFirebase.getFirebaseAuth();

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });

    }
}
