package com.whatsapp_cursoandroid.activity.Model;

import java.util.Date;

/**
 * Created by Admin on 17/08/2017.
 */

public class Conversa {

    private  float peso;
    private String idUsuario;
    private Date dataMensagem;
    private boolean novasMensagens;
    private String mensagem;
    private String nome;

    public Conversa() {
    }


    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public Date getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(Date dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isNovasMensagens() {
        return novasMensagens;
    }

    public void setNovasMensagens(boolean novasMensagens) {
        this.novasMensagens = novasMensagens;
    }
}
