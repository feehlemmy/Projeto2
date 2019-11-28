package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.ClasseInterface;
import com.eduq.model.entity.Classe;

@Repository
public class ClasseDAO extends BaseDAO<Classe, Long> implements ClasseInterface {
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Classe> findByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("select C.id, C.nome, C.ano_classe, C.ano_letivo, C.turno from classe C "
				+ "inner join escola E on E.id = C.escola_id "
				+ "where E.id = ?")
				.setParameter(1, escolaId);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classe> findByProfessor(Long escolaId, Long professorId) {
		Query query = manager.createNativeQuery("SELECT C.id, C.nome, C.turno, C.ano_letivo, C.ano_classe FROM classe C "
				+ "INNER JOIN escola E ON E.id = C.escola_id "
				+ "INNER JOIN professor PR ON PR.escola_id = E.id "
				+ "WHERE E.id = ? AND PR.id = ? AND PR.id = C.professor_id")
				.setParameter(1, escolaId)
				.setParameter(2, professorId);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classe> findByClasse(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT C.id, C.nome as classe_nome, C.ano_classe, P.nome "
				+ "FROM classe C "
				+ "INNER JOIN pessoa P ON P.cpf = C.cpf_professor "
				+ "WHERE C.escola_id = ?")
				.setParameter(1, escolaId);
		
		return query.getResultList();
	}

	@Override
	public void updateAlunos(Classe classe, Long idAluno) {
		Query query = manager.createNativeQuery("INSERT INTO aluno_classe(classe_id, aluno_id) VALUES (?,?)")
				.setParameter(1, classe.getId())
				.setParameter(2, idAluno);
		query.executeUpdate();
	}

	@Override
	public void removeAluno(Long id) {
		Query query = manager.createNativeQuery("DELETE FROM aluno_classe "
				+ "WHERE aluno_id = ?")
				.setParameter(1, id);
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Classe> findByPeriodo(Long escolaId, String periodo) {
		Query query = manager.createNativeQuery("SELECT C.id, C.nome, C.ano_classe, C.ano_letivo, C.turno FROM classe C "
				+ "INNER JOIN escola E ON E.id = C.escola_id "
				+ "WHERE E.id = ? AND C.turno = ?")
				.setParameter(1, escolaId)
				.setParameter(2, periodo);
		
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findByAluno(Long id) {
		Query query = manager.createNativeQuery("SELECT C.nome, P.nome as nome_professor "
				+ "FROM Classe C INNER JOIN aluno_classe AC ON AC.classe_id = C.id "
				+ "INNER JOIN professor PR ON PR.id = C.professor_id "
				+ "INNER JOIN pessoa P ON P.id = PR.pessoa_id "
				+ "WHERE AC.aluno_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}
}
