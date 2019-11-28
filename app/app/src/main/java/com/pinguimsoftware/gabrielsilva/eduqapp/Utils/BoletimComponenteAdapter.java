package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.BoletimComponenteFirebase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoletimComponenteAdapter extends RecyclerView.Adapter<BoletimComponenteViewHolder> {
    private final List<BoletimComponenteFirebase> mBoletins;

    public BoletimComponenteAdapter (List<BoletimComponenteFirebase> boletins){ mBoletins = boletins; }

    @NonNull
    @Override
    public BoletimComponenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoletimComponenteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BoletimComponenteViewHolder holder, int position) {
        holder.nomeComponente.setText(mBoletins.get(position).getNomeComponente());
        holder.mediaComponente.setText(mBoletins.get(position).getMediaAprovacao());
        holder.notaComponente.setText(mBoletins.get(position).getConceito());
    }

    @Override
    public int getItemCount() { return mBoletins != null ? mBoletins.size() : 0; }

    public void updateList(BoletimComponenteFirebase boletim){
        insertItem(boletim);
    }

    private void insertItem(BoletimComponenteFirebase boletim) {
        mBoletins.add(boletim);
        notifyItemChanged(getItemCount());
    }
}
