package com.whatsapp_cursoandroid.activity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Model.Contato;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Contatos extends Fragment {

    private ListView listaContatos;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> contatos;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public Contatos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //instanciando componentes
        listaContatos = (ListView)view.findViewById(R.id.listaContato);
        contatos = new ArrayList<>();
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("contatos").child(retornaEmailBase64());

        //alimentando lista de contatos
        listaContatos();


        //ppreparando adapter
        adapter = new ArrayAdapter<>(getContext(),
                R.layout.list_contatos,
                contatos);

        //preparando listView
        listaContatos.setAdapter(adapter);

        return view;
    }

    private void listaContatos() {

        //preparando evento de escuta
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contatos.clear();
                for(DataSnapshot contatoSnapshot: dataSnapshot.getChildren()){
                    Contato contato = contatoSnapshot.getValue(Contato.class);
                    contatos.add(contato.getEmail());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

    private String retornaEmailBase64(){
       Preferencias preferencias = new Preferencias(getActivity());
        Toast.makeText(getActivity(),Base64ToString.criptografa(preferencias.getEmail()),Toast.LENGTH_LONG).show();
       return  Base64ToString.criptografa(preferencias.getEmail());
    }

    @Override
    public void onStart() {
        //adicionando valeu no query
        databaseReference.orderByChild("nome").addValueEventListener(valueEventListener);
        super.onStart();
    }

    @Override
    public void onDestroy() {
        databaseReference.removeEventListener(valueEventListener);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        databaseReference.removeEventListener(valueEventListener);
        super.onStop();
    }
}


