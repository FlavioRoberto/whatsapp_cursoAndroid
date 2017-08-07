package com.whatsapp_cursoandroid.activity.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Button btnDeslogar;
    private FirebaseAuth auth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //invoca componentes
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        toolbar = (Toolbar)findViewById(R.id.ToolbarId);

        //preparando Toolbar
        toolbar.setTitle("Whatsapp");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
