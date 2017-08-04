package com.whatsapp_cursoandroid.activity.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Admin on 04/08/2017.
 */

public class ConfiguracaoFirebase {
    private static DatabaseReference firebaseReference;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getDatabaseReference(){
       if(firebaseReference == null) {
           firebaseReference = FirebaseDatabase.getInstance().getReference();
       }
        return firebaseReference;
    }

    public static FirebaseAuth getFirebaseAuth(){

        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }

}
