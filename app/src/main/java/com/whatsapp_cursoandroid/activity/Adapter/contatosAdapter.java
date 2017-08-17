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
import android.widget.Toast;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 15/08/2017.
 */

public class contatosAdapter extends ArrayAdapter<Contato> {

    private ArrayList<Contato> arrayContato;
    private Context context ;

    public contatosAdapter(@NonNull Context context, @NonNull ArrayList<Contato> objects) {
        super(context, 0, objects);
        this.arrayContato = objects;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(arrayContato != null ){
            //prepara view para visualizacao
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_contatos,parent,false);

            //instancia objetos da view
            TextView imagemContato = (TextView)view.findViewById(R.id.imagem_contato);
            TextView nomeContato = (TextView)view.findViewById(R.id.nome_contato);
            TextView emailContato = (TextView)view.findViewById(R.id.email_contato);

            //recupera a primeira letra do nome da pessoa;
            String primeiraLetraNome = arrayContato.get(position).getNome().substring(0,1).toUpperCase();
            imagemContato.setText(primeiraLetraNome);

            nomeContato.setText(arrayContato.get(position).getNome());
            emailContato.setText(arrayContato.get(position).getEmail());

        }

        return view;

    }

    public ArrayList<Contato> getArrayContato() {
        return arrayContato;
    }

    public void setArrayContato(ArrayList<Contato> arrayContato) {
        this.arrayContato = arrayContato;
    }
}
