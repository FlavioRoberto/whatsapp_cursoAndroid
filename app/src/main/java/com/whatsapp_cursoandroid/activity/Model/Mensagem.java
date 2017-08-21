package com.whatsapp_cursoandroid.activity.Model;

/**
 * Created by Admin on 17/08/2017.
 */

public class Mensagem {

    private String mensagem, idUsuario, estado;

    public Mensagem() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
