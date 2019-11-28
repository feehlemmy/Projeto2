package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Evento;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventoAdapter extends RecyclerView.Adapter<EventoViewHolder> {
    private final List<Evento> mEventos;

    public EventoAdapter (List<Evento> eventos){
        mEventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_evento, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        holder.nomeEvento.setText(mEventos.get(position).getNomeEvento());
        holder.descricaoEvento.setText(mEventos.get(position).getDescricaoEvento());
        holder.dataEvento.setText(mEventos.get(position).getDataInicioEvento());
        holder.horarioEvento.setText(mEventos.get(position).getHoraInicioEvento());
    }

    @Override
    public int getItemCount() { return mEventos != null ? mEventos.size() : 0; }

    public void updateList(Evento evento){
        insertItem(evento);
    }

    private void insertItem(Evento evento) {
        mEventos.add(evento);
        notifyItemChanged(getItemCount());
    }
}
