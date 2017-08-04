package com.whatsapp_cursoandroid.activity.Application;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Admin on 04/08/2017.
 */

public class invocaProgressDialog {


    private static ProgressDialog progressDialog;
    private Activity activity;

    public invocaProgressDialog(Activity activity){
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
    }

    public void show(String mensagem,String titulo){
        if(progressDialog != null) {
            progressDialog.setMessage(mensagem);
            progressDialog.setTitle(titulo);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    public void dimiss(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
