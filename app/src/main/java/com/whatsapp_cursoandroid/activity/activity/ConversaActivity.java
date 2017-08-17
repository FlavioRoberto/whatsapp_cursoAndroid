package com.whatsapp_cursoandroid.activity.activity;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Adapter.mensagensAdapter;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Helper.formataData;
import com.whatsapp_cursoandroid.activity.Model.Contato;
import com.whatsapp_cursoandroid.activity.Model.Conversa;
import com.whatsapp_cursoandroid.activity.Model.Mensagem;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {
    private Contato contato;
    private Toolbar toolbar;
    private ImageView btnMensagem;
    private ListView listMensagens;
    private EditText textoMensagem;
    private ArrayList<Mensagem> mensagens;
    private mensagensAdapter adapter;
    private DatabaseReference databaseReference;
    private Preferencias preferencias;
    private int cont = 0;
    private String IdRemetente, IdDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        //pega valores passado da outra activity (Contato)
        pegaPutExtra();

        //instanciando componentes
        toolbar = (Toolbar)findViewById(R.id.ToolbarId);
        btnMensagem = (ImageView)findViewById(R.id.btnMensagem);
        textoMensagem = (EditText) findViewById(R.id.textoMensagem);
        listMensagens = (ListView)findViewById(R.id.listaConversa);
        mensagens = new ArrayList<>();
        preferencias = new Preferencias(getApplicationContext());
        IdRemetente = Base64ToString.criptografa(preferencias.getEmail());

     //preparano Toolbar
        toolbar.setTitle(contato.getNome());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        //metodo pra recuperar a mensagem do banco
        recuperaMensagem();

        //preparaListView
        adapter = new mensagensAdapter(getApplication(),mensagens);
        listMensagens.setAdapter(adapter);
        //modo de transferencia automatica do lsit view
        listMensagens.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        //move listView direto pro final da lista
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listMensagens.setSelection(adapter.getCount() - 1);
            }
        });
    }



    private void recuperaMensagem(){
        //recupera mensagens do banco
        databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("mensagens")
                .child(IdRemetente).child(contato.getId());
        //prepara event Listener
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mensagens.clear();
                //Recuperando Mensagens
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Mensagem mensagem = snapshot.getValue(Mensagem.class);
                    if(mensagem != null)
                        mensagens.add(mensagem);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //ao clicar em enviar
        btnMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textoMensagem.getText().toString().isEmpty()){
                    criaMensagem();
                }
            }
        });

    }

    private void criaMensagem() {

        if(!IdRemetente.isEmpty()) {
            Mensagem mensagem = new Mensagem();
            mensagem.setIdUsuario(IdRemetente);
            mensagem.setMensagem(textoMensagem.getText().toString());

            preparaMensagem(mensagem);
            preparaConversa(mensagem);
            textoMensagem.setText("");
        }
    }

    private void preparaMensagem(Mensagem mensagem){
        //salva mensagem remetente
        boolean envioRemetente = salvarMensagem(IdRemetente, contato.getId(), mensagem);

        if (!envioRemetente) {
            Toast.makeText(getApplicationContext(), "Erro ao salvar mensagem, tente novamente", Toast.LENGTH_SHORT).show();
        } else {
            //salva mensagem destinatario
            boolean envioDestinatario = salvarMensagem(contato.getId(), IdRemetente, mensagem);
            if (!envioDestinatario) {
                Toast.makeText(getApplicationContext(), "Erro ao enviar mensagem, tente novamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void preparaConversa(Mensagem mensagem){
        //salvar conversa remetente
        Conversa conversa = new Conversa();
        conversa.setIdUsuario(contato.getId());
        conversa.setNome(contato.getNome());
        conversa.setMensagem(mensagem.getMensagem());
        boolean retornoConversa = salvarConversa(IdRemetente, contato.getId(), conversa);

        if (!retornoConversa) {
            Toast.makeText(getApplicationContext(), "Erro ao salvar conversa, tente novamente", Toast.LENGTH_SHORT).show();
        } else {
            //salvar conversa destinatario
            conversa = new Conversa();
            conversa.setIdUsuario(preferencias.getIdUsuario());
            conversa.setNome(preferencias.getNomeUsuario());
            conversa.setMensagem(mensagem.getMensagem());
            boolean retornaConversa = salvarConversa(contato.getId(),IdRemetente,conversa);
            if(!retornaConversa){
                Toast.makeText(getApplicationContext(), "Erro ao eviar conversa, tente novamente", Toast.LENGTH_SHORT).show();

            }
        }

    }

    private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem Mensagem){
        try{
            databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("mensagens")
                    .child(idRemetente).child(idDestinatario);
            databaseReference.push().setValue(Mensagem);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarConversa(String idRemetente, String idDestinatario, Conversa conversa){

        conversa.setPeso( 1/(Float.parseFloat(String.valueOf(formataData.dataAtual().getTime()))));
        conversa.setDataMensagem(formataData.dataAtual());
        try{
            databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("conversas")
                    .child(idRemetente).child(idDestinatario);
            databaseReference.setValue(conversa);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void pegaPutExtra() {
        contato = new Contato();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            contato.setEmail(extra.getString("EmailContato"));
            contato.setTelefone(extra.getString("TelefoneContato"));
            contato.setNome(extra.getString("NomeContato"));
            contato.setStatus(extra.getString("ContatoStatus"));
            contato.setId(extra.getString("ID"));
        }
    }
}
