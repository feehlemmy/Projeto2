package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.View;
import android.widget.TextView;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;

import androidx.recyclerview.widget.RecyclerView;

public class EventoViewHolder extends RecyclerView.ViewHolder {
    public TextView nomeEvento;
    public TextView descricaoEvento;
    public TextView dataEvento;
    public TextView horarioEvento;

    public EventoViewHolder(View view){
        super(view);

        nomeEvento = (TextView) view.findViewById(R.id.nome_evento);
        descricaoEvento = (TextView) view.findViewById(R.id.descricao_evento);
        dataEvento = (TextView) view.findViewById(R.id.data_evento);
        horarioEvento = (TextView) view.findViewById(R.id.horario_evento);
    }
}
