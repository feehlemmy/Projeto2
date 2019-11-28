package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pinguimsoftware.gabrielsilva.eduqapp.R;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlunosAdapter extends RecyclerView.Adapter<AlunoViewHolder> {
    private final List<AlunoFirebase> mAlunos;

    public AlunosAdapter(List<AlunoFirebase> alunos){
        mAlunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlunoViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        holder.nome.setText(mAlunos.get(position).getNome());
        holder.codigoEscola.setText(mAlunos.get(position).getCodigoGeradoEscola());
    }

    @Override
    public int getItemCount() {
        return mAlunos != null ? mAlunos.size() : 0;
    }

    public void updateList(AlunoFirebase aluno){
        insertItem(aluno);
    }

    private void insertItem(AlunoFirebase aluno) {
        mAlunos.add(aluno);
        notifyItemChanged(getItemCount());
    }
}
