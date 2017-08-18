package com.whatsapp_cursoandroid.activity.config;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Helper.geraNotificacao;
import com.whatsapp_cursoandroid.activity.Model.pushNotification;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

public class TestService extends Service
{
    private Preferencias preferencias;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // START_STICKY serve para executar seu serviço até que você pare ele, é reiniciado automaticamente sempre que termina
        recuperaNotificacao();
        return START_STICKY;
    }

    private void recuperaNotificacao() {
        preferencias = new Preferencias(getApplicationContext());
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("notificacao").child(preferencias.getIdUsuario());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
                    pushNotification notification = notificationSnapshot.getValue(pushNotification.class);
                    if (notification != null) {
                        geraNotificacao.notification(getApplicationContext(),
                                notification.getNomeContato(),
                                notification.getEmailContato(),
                                notification.getEmailRemetente(),
                                notification.getMensagem());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}