package com.whatsapp_cursoandroid.activity.activity;

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
import com.whatsapp_cursoandroid.R;
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
    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instanciando compontes
        editTextEmail = (EditText)findViewById(R.id.EditTextEmail);
        editTextSenha = (EditText)findViewById(R.id.EditTextSenha);
        btnLogar = (Button)findViewById(R.id.BtnLogin);
        snackView = (View)findViewById(R.id.snackView);

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


    private void logarUsuario(){

        logou = false;
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    logou = true;
                }else {
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        erro = "E-mail inválido";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Senha inválida";
                    }
                    catch (Exception e) {
                        erro = "Erro a logar\n"+e.getMessage();
                        e.printStackTrace();
                    }

                    logou = false;
                    Toast.makeText(login_activity.this,erro,Toast.LENGTH_LONG).show();
                }
            }
        });

        final FirebaseUser user = auth.getCurrentUser();

        if(!user.isEmailVerified() && logou == true){
            invocaSnackBar(user);
        }else if(user.isEmailVerified() && logou == true) {
            Intent intent = new Intent(login_activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    private void invocaSnackBar(final FirebaseUser user){
         snackbar = Snackbar
                .make(snackView, "E-mail nao validado, Por favor veridique o e-mail de validação enviado para você.",
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

    public void redirecionaCadastro(View view){
        Intent intent = new Intent(this,CadastroActivity.class);
        startActivity(intent);
    }
}
