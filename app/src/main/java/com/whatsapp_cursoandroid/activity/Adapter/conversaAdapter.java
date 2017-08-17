package com.whatsapp_cursoandroid.activity.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.formataData;
import com.whatsapp_cursoandroid.activity.Model.Conversa;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 17/08/2017.
 */

public class conversaAdapter extends ArrayAdapter<Conversa> {
    private Context context;
    private ArrayList<Conversa> listConversas;

    public conversaAdapter(@NonNull Context context, @NonNull ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.context= context;
        this.listConversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(!listConversas.isEmpty()){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_conversas,parent,false);

            //instanciando componentes
            Conversa conversa = listConversas.get(position);
            TextView imagemContato = (TextView)view.findViewById(R.id.imagem_contato);
            TextView nomeContato = (TextView)view.findViewById(R.id.nome_contato);
            TextView mensagemConversa = (TextView)view.findViewById(R.id.email_contato);
            TextView dataMensagem = (TextView)view.findViewById(R.id.horario_mensagem);
            if(conversa != null) {
                //recupera a primeira letra do nome da pessoa;
                if(!conversa.getNome().isEmpty()) {
                    String primeiraLetraNome = conversa.getNome().substring(0, 1).toUpperCase();
                    imagemContato.setText(primeiraLetraNome);
                }
                java.util.Date date = conversa.getDataMensagem();

                if(date.equals(formataData.dataAtual())){
                    dataMensagem.setText(formataData.dataToStringHora(conversa.getDataMensagem()));
                }else {
                    dataMensagem.setText(formataData.dataToStringDia(conversa.getDataMensagem()));
                }


                nomeContato.setText(conversa.getNome());
                mensagemConversa.setText(conversa.getMensagem());
            }
        }

        return view;
    }
}
