package com.whatsapp_cursoandroid.activity.Model;

import com.google.firebase.database.DatabaseReference;
import com.whatsapp_cursoandroid.activity.config.ConfiguracaoFirebase;

/**
 * Created by Admin on 04/08/2017.
 */

public class Usuario {
    private String nome, email, telefone, id, status;

    public Usuario() {
    }


    public void create(){
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        databaseReference.child("usuarios").child(getId()).setValue(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
