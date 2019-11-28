package com.pinguimsoftware.gabrielsilva.eduqapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.EventoAdapter;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Evento;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListEventosActivity extends AppCompatActivity {
    private List<Evento> eventos = new ArrayList<Evento>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdpter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<AlunoFirebase> students = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        Intent intent = getIntent();
        String cpfResponsavel = intent.getStringExtra("cpfResponsavel");

        recyclerView = (RecyclerView) findViewById(R.id.list_eventos);
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

                    loadEventos(students);
                }
            }
        });
    }

    private void loadEventos(List<AlunoFirebase> alunos) {
        long[] aux = new long[alunos.size()];

        for (int i = 0; i < alunos.size(); i++) { aux[i] = alunos.get(i).getEscolaId(); }

        // Início da remoção dos valores repetidos
        long unicos[] = new long[aux.length];
        int qtd = 0;

        for (int i = 0; i < aux.length; i++){
            boolean existe = false;
            for (int j = 0; j < qtd; j++){
                if( unicos[j] == aux [i]){
                    existe = true;
                    break;
                }
            }

            if(!existe){ unicos[qtd++] = aux[i]; }
        }

        unicos = Arrays.copyOf(unicos, qtd);
        // Fim da remoção dos valores repetidos

        for (int i = 0; i < unicos.length; i++) {
            db.collection("evento").whereEqualTo("escolaId", unicos[i]).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Evento evento = document.toObject(Evento.class);
                                eventos.add(evento);

                                mAdpter = new EventoAdapter(eventos);
                                recyclerView.setAdapter(mAdpter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
                            }

                        }
                    }
                });
        }
    }
}
