package com.whatsapp_cursoandroid.activity.Helper;

import android.util.Base64;
import android.util.Base64InputStream;

import java.io.InputStream;

/**
 * Created by Admin on 04/08/2017.
 */

public class Base64ToString {

    public static String criptografa(String baseToString){
        return Base64.encodeToString(baseToString.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }

    public static String descriptografa(String stringToBase) {
        if (stringToBase != null) {
            return new String(Base64.decode(stringToBase.getBytes(), Base64.DEFAULT));
        }
        return "";
    }


}
