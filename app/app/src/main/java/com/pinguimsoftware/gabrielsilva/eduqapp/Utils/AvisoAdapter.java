package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Aviso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvisoAdapter extends RecyclerView.Adapter<AvisoViewHolder> {
    private final List<Aviso> mAvisos;

    public AvisoAdapter (List<Aviso> avisos){
        mAvisos = avisos;
    }

    @NonNull
    @Override
    public AvisoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AvisoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_aviso, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AvisoViewHolder holder, int position) {
        holder.titulo.setText(mAvisos.get(position).getTituloAviso());
        holder.conteudo.setText(mAvisos.get(position).getConteudo());
        holder.data.setText(mAvisos.get(position).getDataAviso());
    }

    @Override
    public int getItemCount() {
        return mAvisos != null ? mAvisos.size() : 0;
    }

    public void updateList(Aviso aviso){
        insertItem(aviso);
    }

    private void insertItem(Aviso aviso) {
        mAvisos.add(aviso);
        notifyItemChanged(getItemCount());
    }
}
