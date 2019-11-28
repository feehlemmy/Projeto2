package com.eduq.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Aviso;
import com.eduq.model.entity.BoletimFirebase;
import com.eduq.model.entity.Chamada;
import com.eduq.model.entity.Classe;
import com.eduq.model.entity.Evento;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class FirebaseDAO {
	
	public void saveAviso(Aviso aviso) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("aviso").add(aviso);
	}

	public void saveBoletim(BoletimFirebase boletim) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("boletim").add(boletim);		
	}

	public void saveChamada(Chamada chamada) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("chamada").add(chamada);			
	}

	public void saveEvento(Evento evento) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("evento").add(evento);		
	}

	public void saveClasse(Classe classe) {
		Firestore db = FirestoreClient.getFirestore();
		db.collection("classe").add(classe);			
	}

	public void saveAlunoChamada(List<AlunoChamada> alunos) {
		Firestore db = FirestoreClient.getFirestore();
		for(AlunoChamada aux : alunos) {
			db.collection("aluno_chamada").add(aux);
		}
	}
}
