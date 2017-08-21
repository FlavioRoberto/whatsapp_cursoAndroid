package com.whatsapp_cursoandroid.activity.Helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.activity.ConversaActivity;
import com.whatsapp_cursoandroid.activity.activity.MainActivity;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

/**
 * Created by Admin on 17/08/2017.
 */

public class geraNotificacao {

    private static NotificationCompat.Builder mBuilder;
    private  static NotificationManager mNotifyMgr;
    private static Preferencias preferencias;
    private static Intent resultIntent ;
    private Context context;

    public static void notification(Context context, String nomeRemetente, String emailDestinatario, String emailRemetente, String mensagem) {

        if((ConversaActivity.aberta == null && MainActivity.aberta == null)){

            mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_action_search)
                            .setContentTitle(nomeRemetente)
                            .setContentText(mensagem);

            //prepara a tela que sera chamada ao clicar na notificaçao
           preparaIntent(context,nomeRemetente,emailRemetente);
            // Because clicking the notification opens a new ("special") aberta, there's
            // no need to create an artificial back stack.
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            context,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_ONE_SHOT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            //fecha notification ao clicar
            mBuilder.setAutoCancel(true);
            mBuilder.setDefaults(Notification.DEFAULT_ALL);

            int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
            mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

        }
    }

    private static void preparaIntent(Context context,String nomeRemetente,String emailRemetente){
        if(resultIntent == null && ConversaActivity.aberta == null) {
            resultIntent = new Intent(context, ConversaActivity.class);
        }

        resultIntent.putExtra("NomeContato", nomeRemetente);
        resultIntent.putExtra("EmailContato", emailRemetente);
        resultIntent.putExtra("TelefoneContato", "");
        resultIntent.putExtra("StatusCOntato", "");
        resultIntent.putExtra("ID", Base64ToString.criptografa(emailRemetente));
    }

    /*
    public static void apagaNotificacao(Context context){
         Preferencias preferencias = new Preferencias(context);
        ConfiguracaoFirebase.getDatabaseReference().child("notificacao").
                child(preferencias.getIdUsuario()).removeValue();
    }
    */

    public static void dimiss(){
        if(mNotifyMgr != null) {
            mNotifyMgr.cancelAll();
        }
    }

    public static void apagaNotificacaoEspecifica(Context context, String id){
        Preferencias preferencias = new Preferencias(context);
        ConfiguracaoFirebase.getDatabaseReference().child("notificacao").
                child(preferencias.getIdUsuario()).child(id).removeValue();

    }

}
