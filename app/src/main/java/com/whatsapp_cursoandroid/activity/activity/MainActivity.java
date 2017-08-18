package com.whatsapp_cursoandroid.activity.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Adapter.SlidingTabAdapter;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Helper.SlidingTabLayout;
import com.whatsapp_cursoandroid.activity.Application.funcaoToolbar;
import com.whatsapp_cursoandroid.activity.Helper.geraNotificacao;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;
import com.whatsapp_cursoandroid.activity.config.TestService;

public class MainActivity extends AppCompatActivity {



    private Button btnDeslogar;
    private FirebaseAuth auth;
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Preferencias preferencias;


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getBaseContext(), TestService.class));

        //invoca componentes
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        toolbar = (Toolbar)findViewById(R.id.ToolbarId);
        slidingTabLayout = (SlidingTabLayout)findViewById(R.id.stl_tabs);
        viewPager = (ViewPager)findViewById(R.id.vp_tabs);
        preferencias = new Preferencias(getApplicationContext());



        //preparando Toolbar
        toolbar.setTitle("Whatsapp");
        setSupportActionBar(toolbar);


        //preparando ViewPager
        SlidingTabAdapter adapter = new SlidingTabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        //distribui o titulo proporcionalmente na tela
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.colorAccent));
        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        funcaoToolbar.selecionaMenu(this,item.getItemId());
        return super.onOptionsItemSelected(item);
    }

}

