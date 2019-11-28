package com.pinguimsoftware.gabrielsilva.eduqapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pinguimsoftware.gabrielsilva.eduqapp.Utils.EventoAdapter;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoChamada;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.AlunoFirebase;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Chamada;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.Evento;
import com.pinguimsoftware.gabrielsilva.eduqapp.model.entity.ResponsavelFirebase;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
	private String alunoNome;
	private String cpfResponsavel;
	List<AlunoChamada> alunoChamadaList  = new ArrayList<>();
	private FirebaseFirestore db = FirebaseFirestore.getInstance();
	private List<AlunoFirebase> students = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_calendar);
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		alunoNome = intent.getStringExtra("alunoNome");
		cpfResponsavel = intent.getStringExtra("cpfResponsavel");

		DocumentReference docRef = db.collection("responsavel").document(cpfResponsavel);
		docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
			@Override
			public void onComplete(@NonNull Task<DocumentSnapshot> task) {
				if (task.isSuccessful()){
					DocumentSnapshot document = task.getResult();
					ResponsavelFirebase responsavel = document.toObject(ResponsavelFirebase.class);
					students = responsavel.getAlunos();

					AlunoChamada ac = new AlunoChamada();

					for (AlunoFirebase af : students){
						if (af.getNome().equals(alunoNome)){
							ac.setAlunoId(af.getId());
							getAlunoChamada(ac);
						}
					}
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void getAlunoChamada(AlunoChamada aluno) {
		db.collection("aluno_chamada")
			.whereEqualTo("alunoId", aluno.getAlunoId())
			.get()
			.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
				@Override
				public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
					if (!queryDocumentSnapshots.isEmpty()) {
						for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
							AlunoChamada ac = document.toObject(AlunoChamada.class);
							alunoChamadaList.add(ac);
						}

						getChamadaData(alunoChamadaList);
					}
				}
			});
	}

	private void getChamadaData(List<AlunoChamada> listAlunoChamada) {
		for (AlunoChamada ac : listAlunoChamada){
			db.collection("chamada")
				.whereEqualTo("id", ac.getChamada_id())
				.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
				@Override
				public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
					if (!queryDocumentSnapshots.isEmpty()) {
						for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
							Chamada chamada = document.toObject(Chamada.class);

							setChamadaData(chamada, ac);
						}
					}
				}
			});
		}
	}

	private void setChamadaData(Chamada chamada, AlunoChamada ac) {
		CalenderEvent calendarEvent;
		String dataString;

		dataString = (chamada.getDataChamada());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		calendarEvent = findViewById(R.id.calendarView);

		try {
			Date dataChamada = format.parse(dataString);
			if(ac.getFalta() != 0) {
				Event event = new Event(dataChamada.getTime(), "A", Color.RED);
				calendarEvent.addEvent(event);
				calendarEvent.removeEvent(event);
				calendarEvent.addEvent(event);
			}else {
				Event event = new Event(dataChamada.getTime(), "P", Color.BLUE);
				calendarEvent.addEvent(event);
				calendarEvent.removeEvent(event);
				calendarEvent.addEvent(event);
			}
		} catch(ParseException e) {
			e.printStackTrace();
		}
	}
}