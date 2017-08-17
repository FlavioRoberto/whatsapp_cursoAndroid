package com.whatsapp_cursoandroid.activity.Helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 23/07/2017.
 */

public class formataData {

    private static final String FORMATO_DATA = "dd/MM/yyyy",FORMATO_HORA = "HH:mm";

    public formataData() {
    }

    public static void stringPraData(String dataString, Date convertedDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_DATA);
         convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dataString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Date dataAtual(){
        Date dataFormatada = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dataFormatada);
        Date data_atual = cal.getTime();
        return data_atual;
    }

    public static String dataToStringHora(Date data) {

        if (data != null) {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(FORMATO_HORA);
            Date dataFormatada = data;
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataFormatada);
            Date data_atual = cal.getTime();

            return dateFormat.format(data_atual);
        }else {
            return "";
        }
    }

    public static String dataToStringDia(Date data) {

        if (data != null) {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(FORMATO_HORA);
            Date dataFormatada = data;
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataFormatada);
            Date data_atual = cal.getTime();

            return dateFormat.format(data_atual);
        }else {
            return "";
        }
    }
}
