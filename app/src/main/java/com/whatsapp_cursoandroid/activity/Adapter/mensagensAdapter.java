package com.whatsapp_cursoandroid.activity.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsapp_cursoandroid.R;
import com.whatsapp_cursoandroid.activity.Helper.Base64ToString;
import com.whatsapp_cursoandroid.activity.Helper.Preferencias;
import com.whatsapp_cursoandroid.activity.Model.Mensagem;

import java.util.ArrayList;

/**
 * Created by Admin on 17/08/2017.
 */

public class mensagensAdapter extends ArrayAdapter<Mensagem> {

    private ArrayList<Mensagem> listaMensagem;
    private Context context;

    public mensagensAdapter(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context,0, objects);
        this.context = context;
        this.listaMensagem = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(!listaMensagem.isEmpty()){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //recupera dados usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuario = preferencias.getIdUsuario();

            //recupera Mensagem
            Mensagem mensagem = listaMensagem.get(position);

            if(mensagem.getIdUsuario().equals(idUsuario)){
                view = inflater.inflate(R.layout.item_mensagem_direita, parent, false);
            }else {
                //monta visualizacao da mensagem
                view = inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }

            //invocar os componentes da view
            TextView textMensagem;
            ImageView imagemVisualizado, imagemRecebido;
            //instanciar os componentes
            textMensagem = (TextView)view.findViewById(R.id.tv_mensagem);
            imagemRecebido = (ImageView)view.findViewById(R.id.imageRecebido);
            imagemVisualizado = (ImageView)view.findViewById(R.id.imageVisualizado);

            String texto = mensagem.getMensagem();
            if(texto.length()<=3) {
               textMensagem.setTextSize(100);
            }

            //funcao pra copiar texto do textView
            copiaTextoDoTextView(textMensagem);

            //seta texto pro textView
            textMensagem.setText(mensagem.getMensagem());

            preparaImagemConfiramcao(imagemVisualizado,imagemRecebido,mensagem);
        }

        return view;

    }


    public  void copiaTextoDoTextView (final TextView yourTextView) {
        yourTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(yourTextView.getText());
                Toast.makeText(context,"Texto Copiado",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preparaImagemConfiramcao(ImageView imagemVisualizado, ImageView imagemRecebido, Mensagem mensagem){
        int size = 32;

        //prepara icones de imagens de estado da conversa
        String estado = mensagem.getEstado();
        if(estado!= null) {
            if(estado.equals("Recebido")){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imagemVisualizado.getLayoutParams();
                params.height = size;
                params.width = size;
                imagemVisualizado.setImageResource(R.drawable.ic_action_tick);

            }
            if(estado.equals("Enviado")){
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imagemRecebido.getLayoutParams();
                params.height = size;
                params.width = size;
                imagemRecebido.setImageResource(R.drawable.ic_action_tick);
            }

        }

    }
}
