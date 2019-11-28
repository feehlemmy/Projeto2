package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.View;
import android.widget.TextView;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class BoletimComponenteViewHolder extends RecyclerView.ViewHolder {
    public TextView nomeComponente;
    public TextView mediaComponente;
    public TextView notaComponente;

    public BoletimComponenteViewHolder(View view){
        super(view);

        nomeComponente = (TextView) view.findViewById(R.id.nome_componente);
        mediaComponente = (TextView) view.findViewById(R.id.media_componente);
        notaComponente = (TextView) view.findViewById(R.id.nota_componente);
    }
}
