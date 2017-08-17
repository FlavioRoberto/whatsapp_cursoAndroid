package com.whatsapp_cursoandroid.activity.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Model.Mensagem;

import java.util.ArrayList;

/**
 * Created by Admin on 17/08/2017.
 */

public class mensagensAdapter extends ArrayAdapter<Mensagem> {

    private ArrayList<Mensagem> listaMensagem;
    private Context context;

    public mensagensAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context,0, objects);
        this.context = context;
        this.listaMensagem = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(!listaMensagem.isEmpty()){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //recupera dados usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuario = preferencias.getIdUsuario();

            //recupera Mensagem
            Mensagem mensagem = listaMensagem.get(position);

            if(mensagem.getIdUsuario().equals(idUsuario)){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else {
                //monta visualizacao da mensagem
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //invocar os componentes da view
            TextView textMensagem;
            //instanciar os componentes
            textMensagem = (TextView)view.findViewById(R.id.tv_mensagem);
            textMensagem.setText(mensagem.getMensagem());

        }

        return view;

    }
}
