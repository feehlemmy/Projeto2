package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.ChamadaInterface;
import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Chamada;

@Repository
public class ChamadaDAO extends BaseDAO<Chamada, Long> implements ChamadaInterface {
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByEscola(Long idEscola) {
		Query query = manager.createNativeQuery("SELECT C.id, C.data_chamada, CL.nome, CL.ano_classe, CL.id as id_classe, C.realizada FROM chamada C "
				+ "INNER JOIN classe CL on CL.id = C.classe_id "
				+ "WHERE C.escola_id = ? AND C.realizada = 'Sim' ORDER BY C.id")
				.setParameter(1, idEscola);
		
		return query.getResultList();
	}

	@Override
	public void saveAll(List<AlunoChamada> alunos) {
		for (AlunoChamada ac : alunos) {
			manager.persist(ac);
		}
		
		manager.flush();
		manager.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByProfessor(Long idProfessor) {
		Query query = manager.createNativeQuery("SELECT C.id, C.data_chamada, CL.nome, CL.ano_classe, CL.id as id_classe, C.realizada FROM chamada C "
				+ "INNER JOIN classe CL on CL.id = C.classe_id "
				+ "WHERE C.classe_id = CL.id AND C.realizada = 'Sim' AND CL.professor_id = ? ORDER BY C.id")
				.setParameter(1, idProfessor);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByEscolaNotDone(Long idEscola) {
		Query query = manager.createNativeQuery("SELECT C.id, C.data_chamada, CL.nome, CL.ano_classe, CL.id as id_classe, C.realizada FROM chamada C "
				+ "INNER JOIN classe CL on CL.id = C.classe_id "
				+ "WHERE C.escola_id = ? AND C.realizada = 'Não' ORDER BY C.id")
				.setParameter(1, idEscola);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByProfessorNotDone(Long idProfessor) {
		Query query = manager.createNativeQuery("SELECT C.id, C.data_chamada, CL.nome, CL.ano_classe, CL.id as id_classe, C.realizada FROM chamada C "
				+ "INNER JOIN classe CL on CL.id = C.classe_id "
				+ "WHERE C.classe_id = CL.id AND CL.professor_id = ? AND C.realizada = 'Não' ORDER BY C.id")
				.setParameter(1, idProfessor);
		
		return query.getResultList();
	}

	@Override
	public void finishChamada(Long id) {
		Query query = manager.createNativeQuery("UPDATE chamada SET realizada='Sim' WHERE id = ?")
				.setParameter(1, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByChamada(Long id) {
		Query query = manager.createNativeQuery("SELECT AL.id as id_aluno_chamada, A.id as aluno_id, P.nome, AL.status "
				+ "FROM aluno_chamada AL "
				+ "INNER JOIN aluno A on A.id = AL.aluno_id "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "WHERE AL.chamada_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByAlunoChamada(Long id) {
		Query query = manager.createNativeQuery("SELECT AC.id as id_chamada, P.nome, C.data_chamada, AC.status "
				+ "FROM aluno_chamada AC "
				+ "INNER JOIN aluno A on A.id = AC.aluno_id "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "INNER JOIN chamada C on C.id = AC.chamada_id "
				+ "WHERE AC.id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}

	@Override
	public void updatePresenca(AlunoChamada aluno) {
		Query query = manager.createNativeQuery("UPDATE aluno_chamada "
				+ "SET status = ?, falta = ? "
				+ "WHERE id = ?")
				.setParameter(1, aluno.getStatus())
				.setParameter(2, aluno.getFalta())
				.setParameter(3, aluno.getId());
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Chamada> findByPeriodo(Long idEscola, String data, String periodo) {
		Query query = manager.createNativeQuery("SELECT C.id, C.data_chamada, CL.nome "
				+ "FROM chamada C "
				+ "INNER JOIN classe CL ON CL.id = C.classe_id "
				+ "WHERE C.escola_id = ? AND C.data_chamada = ? AND CL.turno = ?")
				.setParameter(1, idEscola)
				.setParameter(2, data)
				.setParameter(3, periodo);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFaltasAluno(Long id) {
		Query query = manager.createNativeQuery("SELECT COUNT(status) FROM aluno_chamada AC WHERE AC.status = 'Ausente' AND AC.aluno_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}
}
