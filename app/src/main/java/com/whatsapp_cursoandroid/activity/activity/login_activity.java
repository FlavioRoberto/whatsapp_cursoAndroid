package com.whatsapp_cursoandroid.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Application.invocaProgressDialog;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Model.Usuario;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

public class login_activity extends AppCompatActivity {

    private EditText editTextEmail, editTextSenha;
    private Usuario usuario ;
    private Button btnLogar;
    private FirebaseAuth auth;
    private View snackView;
    private Snackbar snackBar;
    private  boolean logou = false;
    private Preferencias preferencias;
    private Snackbar snackbar;
    private invocaProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instanciando compontes
        editTextEmail = (EditText)findViewById(R.id.EditTextEmail);
        editTextSenha = (EditText)findViewById(R.id.EditTextSenha);
        btnLogar = (Button)findViewById(R.id.BtnLogin);
        snackView = (View)findViewById(R.id.snackView);
        progressDialog = new invocaProgressDialog(login_activity.this);
        preferencias = new Preferencias(login_activity.this);
        usuario = new Usuario();

        verificaUsuarioLogado();

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(editTextEmail.getText().toString());
                usuario.setSenha(editTextSenha.getText().toString());

                logarUsuario();
            }
        });
    }

    private boolean verificaCampoEmailSenha(){
        if(editTextEmail.getText().toString().isEmpty() || editTextSenha.getText().toString().isEmpty()){

            if(editTextEmail.getText().toString().isEmpty()){
                editTextEmail.setError("Email vazio");
            }

            if(editTextSenha.getText().toString().isEmpty()){
                editTextSenha.setError("Senha vazia");
            }

            return  false;
        }else {
            return  true;
        }
    }

    private void verificaUsuarioLogado(){

        auth = ConfiguracaoFirebase.getFirebaseAuth();
        if(auth.getCurrentUser() != null){
            progressDialog.show("aguarde","Logando...");
            if(auth.getCurrentUser().isEmailVerified()){
                redirecionaApp();
                progressDialog.dimiss();
            }else {
                invocaSnackBar(auth.getCurrentUser());
            }
            progressDialog.dimiss();
        }
    }

    private void logarUsuario(){
      if(verificaCampoEmailSenha()) {
          progressDialog.show("Aguarde", "Realizando Login...");
          auth = ConfiguracaoFirebase.getFirebaseAuth();
          auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()) {
                       logou = true;
                      //pega o dados do usuario logado
                      retornaNomeUsuarioLogado();
                      preferencias.putEmail(usuario.getEmail());
                      preferencias.putNome(usuario.getNome());
                      verificaUsuarioLogado();
                  } else {
                      String erro;
                      try {
                          throw task.getException();
                      } catch (FirebaseAuthInvalidUserException e) {
                          erro = "E-mail inválido";
                          logou = false;
                      } catch (FirebaseAuthInvalidCredentialsException e) {
                          erro = "Senha inválida";
                          logou = false;
                      } catch (Exception e) {
                          erro = "Erro a logar\n" + e.getMessage();
                          e.printStackTrace();
                          logou = false;
                      }
                      progressDialog.dimiss();
                      logou = false;
                      Toast.makeText(login_activity.this, erro, Toast.LENGTH_LONG).show();
                  }
              }
          });

      }
    }

    private void redirecionaApp(){

        Intent intent = new Intent(login_activity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void invocaSnackBar(final FirebaseUser user){
         snackbar = Snackbar
                .make(snackView, "E-mail nao validado, Por favor verifique seu e-mail.",
                        Snackbar.LENGTH_LONG).setAction("Reenviar E-mail", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnCompleteListener(login_activity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(login_activity.this,"E-mail de validação reenviado!",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(login_activity.this,"Erro\n"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
        snackbar.show();
    }

    private void  retornaNomeUsuarioLogado() {
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        if (auth != null) {
            DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference().child("usuarios")
                    .child(Base64ToString.criptografa(editTextEmail.getText().toString()));
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        usuario.setNome(dataSnapshot.getValue(Usuario.class).getNome());
                        Toast.makeText(login_activity.this,"Nome:"+dataSnapshot.getValue(Usuario.class).getNome(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

    public void redirecionaCadastro(View view){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);
    }
}
