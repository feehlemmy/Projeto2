package com.pinguimsoftware.gabrielsilva.eduqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.BoletimComponenteAdapter;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.BoletimComponenteFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.BoletimFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;

import java.util.List;

public class ReportCardActivity extends AppCompatActivity {
	private RecyclerView recyclerView;
	private RecyclerView.Adapter mAdpter;
	private RecyclerView.LayoutManager layoutManager;
	private FirebaseFirestore db = FirebaseFirestore.getInstance();
	private TextView nomeAluno;
	private TextView matriculaAluno;
	private TextView situacaoAluno;
	private List<AlunoFirebase> students = null;
	private List<BoletimComponenteFirebase> boletins = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_card);

		Intent intent = getIntent();
		String cpfResponsavel = intent.getStringExtra("cpfResponsavel");
		String alunoNome = intent.getStringExtra("alunoNome");

		nomeAluno = findViewById(R.id.lbl_nome_boletim);
		matriculaAluno = findViewById(R.id.lbl_matricula_aluno);
		situacaoAluno = findViewById(R.id.lbl_situacao_aluno);
		recyclerView = (RecyclerView) findViewById(R.id.list_componentes);
		layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		nomeAluno.setText(alunoNome);

		DocumentReference docRef = db.collection("responsavel").document(cpfResponsavel);
		docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()){
					DocumentSnapshot document = task.getResult();
					ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);
					students = responsavel.getAlunos();

					for (AlunoFirebase af : students){
						if (af.getNome().equals(alunoNome)){
							matriculaAluno.setText(af.getMatricula());
							loadBoletim(af.getId());
						}
					}
				}
			}
		});
	}

	private void loadBoletim(Long idAluno) {
		db.collection("boletim").whereEqualTo("alunoId", idAluno).get()
			.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
				@Override
				public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
					if (!queryDocumentSnapshots.isEmpty()) {
						for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
							BoletimFirebase boletimFirebase = document.toObject(BoletimFirebase.class);

							boletins = boletimFirebase.getComponentes();
							situacaoAluno.setText(boletimFirebase.getSituacao());

							mAdpter = new BoletimComponenteAdapter(boletins);
							recyclerView.setAdapter(mAdpter);
							recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
						}

					}
				}
			});
	}
}
