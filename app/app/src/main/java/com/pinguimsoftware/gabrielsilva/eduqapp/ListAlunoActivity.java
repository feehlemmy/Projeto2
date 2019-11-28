package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.AlunosAdapter;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListAlunoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdpter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<AlunoFirebase> students = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        Intent intent = getIntent();
        String cpfResponsavel = intent.getStringExtra("cpfResponsavel");

        recyclerView = (RecyclerView) findViewById(R.id.list_alunos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DocumentReference docRef = db.collection("responsavel").document(cpfResponsavel);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);
                    students = responsavel.getAlunos();

                    mAdpter = new AlunosAdapter(students);
                    recyclerView.setAdapter(mAdpter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                }
            }
        });
    }
}
