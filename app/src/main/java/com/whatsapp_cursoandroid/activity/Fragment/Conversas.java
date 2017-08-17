package com.whatsapp_cursoandroid.activity.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Adapter.conversaAdapter;
import com.whatsapp_cursoandroid.activity.Adapter.mensagensAdapter;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Model.Conversa;
import com.whatsapp_cursoandroid.activity.Model.Mensagem;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Conversas extends Fragment {

    private ArrayList<Conversa> listMensagem;
    private ListView listViewMensagem;
    private conversaAdapter  adapter;
    private EditText novaMensagem;

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
        listViewMensagem = (ListView)view.findViewById(R.id.lista_conversas);

        //preparando ListView
        adapter = new conversaAdapter(getActivity(),listMensagem);
        listViewMensagem.setAdapter(adapter);

        retornaConversas();

        return view;
    }


    private void retornaConversas(){
        Preferencias preferencias = new Preferencias(getActivity());
        String idUsuarioLogado = preferencias.getIdUsuario();
        Query databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("conversas").
                child(idUsuarioLogado).orderByChild("peso");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMensagem.clear();
                for (DataSnapshot conversaSanapshot : dataSnapshot.getChildren()){
                    if(conversaSanapshot != null) {
                        Conversa conversa = conversaSanapshot.getValue(Conversa.class);
                        listMensagem.add(conversa);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
