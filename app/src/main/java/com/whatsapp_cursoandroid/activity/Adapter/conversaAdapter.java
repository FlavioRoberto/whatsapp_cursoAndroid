package com.whatsapp_cursoandroid.activity.Adapter;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Helper.formataData;
import com.whatsapp_cursoandroid.activity.Helper.geraNotificacao;
import com.whatsapp_cursoandroid.activity.Model.Conversa;
import com.whatsapp_cursoandroid.activity.Model.pushNotification;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

import java.util.ArrayList;

/**
 * Created by Admin on 17/08/2017.
 */

public class conversaAdapter extends ArrayAdapter<Conversa> {
    private Context context;
    private ArrayList<Conversa> listConversas;
    private String conversaAdd="";


    public conversaAdapter(@NonNull Context context, @NonNull ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.context= context;
        this.listConversas = objects;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(!listConversas.isEmpty() && listConversas != null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_conversas,parent,false);


            Conversa conversa = listConversas.get(position);
            TextView imagemContato = (TextView)view.findViewById(R.id.imagem_contato);
            TextView nomeContato = (TextView)view.findViewById(R.id.nome_contato);
            TextView novaConversa = (TextView)view.findViewById(R.id.novas_mensagens);
            TextView mensagemConversa = (TextView)view.findViewById(R.id.email_contato);
            TextView dataMensagem = (TextView)view.findViewById(R.id.horario_mensagem);

            if(conversa != null) {
                //recupera a primeira letra do nome da pessoa;
                String nome = conversa.getNome();
                if(nome != null) {
                    String primeiraLetraNome = conversa.getNome().substring(0, 1).toUpperCase();
                    imagemContato.setText(primeiraLetraNome);
                }
                //recupera data da mensagem
                java.util.Date date = conversa.getDataMensagem();

                //se a data nao for nula atualoza no no
                if(date  != null) {
                    if (date.equals(formataData.dataAtual())) {
                        dataMensagem.setText(formataData.dataToStringHora(conversa.getDataMensagem()));
                    } else {
                        dataMensagem.setText(formataData.dataToStringDia(conversa.getDataMensagem()));
                    }
                }
                nomeContato.setText(conversa.getNome());
                mensagemConversa.setText(conversa.getMensagem());

                if(conversa.isNovasMensagens()){
                    novaConversa.setText("Novas mensagens");
                }else {
                    novaConversa.setText("");
                }


            }
        }

        return view;
    }


}
