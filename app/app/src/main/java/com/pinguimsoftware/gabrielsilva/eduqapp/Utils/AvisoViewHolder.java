package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.View;
import android.widget.TextView;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class AvisoViewHolder extends RecyclerView.ViewHolder {
    public TextView titulo;
    public TextView conteudo;
    public TextView data;

    public AvisoViewHolder(View view){
        super(view);

        titulo = (TextView) view.findViewById(R.id.titulo_aviso);
        conteudo = (TextView) view.findViewById(R.id.conteudo_aviso);
        data = (TextView) view.findViewById(R.id.data_aviso);
    }
}
