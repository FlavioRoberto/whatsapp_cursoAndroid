package com.whatsapp_cursoandroid.activity.Application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Model.Contato;
import com.whatsapp_cursoandroid.activity.Model.Usuario;
import com.whatsapp_cursoandroid.activity.activity.login_activity;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

/**
 * Created by Admin on 07/08/2017.
 */

public class funcaoToolbar {

    private static FirebaseAuth auth;
    private static DatabaseReference databaseReference;
    private static String identificadorContato;

    public static void selecionaMenu(Activity activity,int id_itemMenu){

        switch (id_itemMenu){
            case R.id.menu_sair : logout(activity);break;
            case R.id.menu_buscar: buscar();break;
            case R.id.menu_configuracoes: configuracoes(activity);break;
            case R.id.menu_contatos : cadstroContatos(activity);break;
        }

    }

    private static void cadstroContatos(final Activity activity) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        //preparando Dialog
        alertDialog.setTitle("Novo contato");
        alertDialog.setMessage("Email do usuario");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(activity);
        editText.setHint("Informe o e-mail");
        alertDialog.setView(editText);

        //configurando botoes
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailCOntato = editText.getText().toString();
                if(!emailCOntato.isEmpty()){
                    identificadorContato = Base64ToString.criptografa(emailCOntato);

                    //recuperando instancia do database
                    databaseReference = ConfiguracaoFirebase.getDatabaseReference().
                            child("usuarios").child(identificadorContato);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                                auth = ConfiguracaoFirebase.getFirebaseAuth();
                                if(auth.getCurrentUser() != null){

                                    //Reucuperar dados do contato
                                   Usuario contatoUser =   dataSnapshot.getValue(Usuario.class);

                                    //recupera usuario logado
                                    FirebaseUser userLogado = auth.getCurrentUser();

                                    //pega email do usuario logado e converte pra base 64
                                    String usuarioLogado = Base64ToString.criptografa(userLogado.getEmail());

                                    //passando valores pra classe contato
                                    Contato contato = new Contato();
                                    contato.setId(identificadorContato);
                                    contato.setEmail(contatoUser.getEmail());
                                    contato.setStatus(contatoUser.getStatus());
                                    contato.setNome(contatoUser.getNome());
                                    contato.setTelefone(contatoUser.getTelefone());

                                    databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("contatos")
                                            .child(usuarioLogado).child(identificadorContato);

                                    databaseReference.setValue(contato).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(activity,"Contato adicionado!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }

                            }else {

                                Toast.makeText(activity,"usuario não encontrado!",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {
                    Toast.makeText(activity,"Campo vazio!",Toast.LENGTH_SHORT).show();
                   }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private static void configuracoes(Activity context) {

    }

    public static void  logout(Activity activity){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signOut();
        Intent intent = new Intent(activity,login_activity.class);
        activity.finish();
        activity.startActivity(intent);
    }

    private static void buscar(){}

}
