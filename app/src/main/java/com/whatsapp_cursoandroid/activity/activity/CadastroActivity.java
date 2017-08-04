package com.whatsapp_cursoandroid.activity.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Model.Usuario;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

public class CadastroActivity extends AppCompatActivity {
    private EditText textNome, textEmail, textConfirmaEmail, textCelular, textSenha,textConfirmaSenha;
    private Button btnCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //instanciando componentes
        textNome = (EditText)findViewById(R.id.EditTextNome);
        textEmail = (EditText)findViewById(R.id.EditTextEmail);
        textConfirmaEmail = (EditText)findViewById(R.id.EditTextConfirmaEmail);
        textCelular = (EditText)findViewById(R.id.EditTextCelular);
        textSenha = (EditText)findViewById(R.id.EditTextSenha);
        textConfirmaSenha = (EditText)findViewById(R.id.EditTextConfirmaSenha);
        btnCadastrar = (Button)findViewById(R.id.BtnCadastrar);




        //ao clicar no botao
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validaCampos();
            }
        });



    }

    private void cadastraUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        usuario = new Usuario();
        usuario.setEmail(textEmail.getText().toString());
        usuario.setNome(textNome.getText().toString());
        usuario.setTelefone(textCelular.getText().toString());
        usuario.setStatus("Olá, eu utilizo o messeger do Flavão!");

        //
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),
                textSenha.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = task.getResult().getUser();
                        usuario.setId(user.getUid());
                        usuario.create();
                        user.sendEmailVerification();
                        Toast.makeText(CadastroActivity.this,"Cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(CadastroActivity.this,"Não foi possível cadastrar\n"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
            }
        });

    }

    private void validaCampos(){

        TextInputLayout inputSenha = (TextInputLayout)findViewById(R.id.inputSenha);
        TextInputLayout inputConfirmaSenha = (TextInputLayout)findViewById(R.id.inputConfirmaSenha);

        if(textNome.getText().toString().isEmpty() || textEmail.getText().toString().isEmpty() ||
                textConfirmaEmail.getText().toString().isEmpty() || textCelular.getText().toString().isEmpty() ||
                textSenha.getText().toString().isEmpty() || textConfirmaSenha.getText().toString().isEmpty()){

            if(textNome.getText().toString().isEmpty()){
                textNome.setEnabled(true);
                textNome.setError("Campo obrigatório");
            }

            if(textEmail.getText().toString().isEmpty()){
                textEmail.setEnabled(true);
                textEmail.setError("Campo obrigatório");
            }

            if(textConfirmaEmail.getText().toString().isEmpty()){
                textConfirmaEmail.setEnabled(true);
                textConfirmaEmail.setError("Campo obrigatório");
            }

            if(textCelular.getText().toString().isEmpty()){
                textCelular.setEnabled(true);
                textCelular.setError("Campo obrigatório");
            }

            if(textSenha.getText().toString().isEmpty()){
                inputSenha.setErrorEnabled(true);
                inputSenha.setError("Campo obrigatório");
            }

            if(textConfirmaSenha.getText().toString().isEmpty()){
                inputConfirmaSenha.setErrorEnabled(true);
                inputConfirmaSenha.setError("Campo obrigatório");
            }

        }else {

            Boolean erro = false;
            inputSenha.setErrorEnabled(false);
            inputConfirmaSenha.setErrorEnabled(false);

            if(!textEmail.getText().toString().equals(textConfirmaEmail.getText().toString())){
                textConfirmaEmail.setError("Email não corresponde");
                erro = true;
            }

            if(!textSenha.getText().toString().equals(textConfirmaSenha.getText().toString())){
                inputConfirmaSenha.setError("Senhas não correspondem");
                inputConfirmaSenha.setErrorEnabled(true);
                erro = true;
            }

            //metodo para cadastrar
            if(!erro){
                cadastraUsuario();
            }
        }
    }


}
