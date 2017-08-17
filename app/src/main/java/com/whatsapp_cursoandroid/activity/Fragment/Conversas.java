package com.whatsapp_cursoandroid.activity.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Adapter.mensagensAdapter;
import com.whatsapp_cursoandroid.activity.Model.Mensagem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Conversas extends Fragment {

    private ArrayList<Mensagem> listMensagem;
    private ListView listViewMensagem;

    public Conversas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_conversas, container, false);

        //instanciando componentes
        listMensagem = new ArrayList<>();
        listViewMensagem = (ListView)view.findViewById(R.id.lista_mensagem);


        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
