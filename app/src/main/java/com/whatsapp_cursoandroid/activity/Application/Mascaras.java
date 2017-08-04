package com.whatsapp_cursoandroid.activity.Application;

import android.text.InputType;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

/**
 * Created by Admin on 03/08/2017.
 */

public class Mascaras {


    public static void mascaraCodigoPostal(EditText codigoPostal){
        SimpleMaskFormatter smf = new SimpleMaskFormatter ("+NN");
        MaskTextWatcher mtw = new MaskTextWatcher(codigoPostal,smf);
        codigoPostal.addTextChangedListener(mtw);
        codigoPostal.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public static void mascaraDDD(EditText ddd){
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN");
        MaskTextWatcher mtw = new MaskTextWatcher(ddd,smf);
        ddd.addTextChangedListener(mtw);
        ddd.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public static void mascaraTelefone(EditText telefone){
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(telefone,smf);
        telefone.addTextChangedListener(mtw);
        telefone.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

}
