package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.View;
import android.widget.TextView;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class AlunoViewHolder extends RecyclerView.ViewHolder {
    public TextView nome;
    public TextView codigoEscola;

    public AlunoViewHolder(View view){
        super(view);

        nome = (TextView) view.findViewById(R.id.nome_item);
        codigoEscola = (TextView) view.findViewById(R.id.codigo_item);
    }
}
