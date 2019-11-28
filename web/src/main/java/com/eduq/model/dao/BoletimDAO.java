package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.BoletimInterface;
import com.eduq.model.entity.Boletim;
import com.eduq.model.entity.BoletimAluno;
import com.eduq.model.entity.BoletimAlunoComponente;
import com.eduq.model.entity.BoletimComponente;
import com.eduq.model.entity.ComponenteCurricular;

@Repository
public class BoletimDAO extends BaseDAO<Boletim, Long> implements BoletimInterface{
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Boletim> findByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT B.id, C.nome, B.bimestre, B.data_boletim, B.individual_criado "
				+ "FROM boletim B "
				+ "INNER JOIN classe C on C.id = B.classe_id "
				+ "WHERE B.escola_id = ? ORDER BY B.id")
				.setParameter(1, escolaId);
		
		return query.getResultList();
	}

	@Override
	public void saveComponente(BoletimComponente boletimComponente) {
		Query query = manager.createNativeQuery("INSERT INTO boletim_componente (componente_id, boletim_id) VALUES (?, ?)")
				.setParameter(1, boletimComponente.getComponenteId())
				.setParameter(2, boletimComponente.getBoletimId());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComponenteCurricular> findByBoletim(Long boletimId) {
		Query query = manager.createNativeQuery("SELECT BC.id, C.nome, C.tipo_avaliacao "
				+ "FROM boletim_componente BC "
				+ "INNER JOIN componente C on C.id = BC.componente_id "
				+ "WHERE BC.boletim_id = ? ORDER BY BC.id")
				.setParameter(1, boletimId);
		
		return query.getResultList();
	}

	@Override
	public void removeComponente(Long id) {
		Query query = manager.createNativeQuery("DELETE FROM boletim_componente WHERE id = ?")
				.setParameter(1, id);
		query.executeUpdate();
	}

	@Override
	public void updateBoletim(Boletim boletim) {
		Query query = manager.createNativeQuery("UPDATE boletim "
				+ "SET bimestre = ?, classe_id = ?, observacao = ? "
				+ "WHERE id = ?")
				.setParameter(1, boletim.getBimestre())
				.setParameter(2, boletim.getIdClasse())
				.setParameter(3, boletim.getObservacao())
				.setParameter(4, boletim.getId());
		query.executeUpdate();
	}

	@Override
	public void saveAll(List<BoletimAluno> boletins) {
		for (BoletimAluno ba : boletins) {
			manager.persist(ba);
		}
		
		manager.flush();
		manager.clear();
	}

	@Override
	public void generatedBoletim(Long id) {
		Query query = manager.createNativeQuery("UPDATE boletim "
				+ "SET individual_criado = 'Sim' "
				+ "WHERE id = ?")
				.setParameter(1, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findComponentesBoletim(Long id) {
		Query query = manager.createNativeQuery("SELECT BC.id, BC.componente_id, BC.boletim_id FROM boletim_componente BC WHERE BC.boletim_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}

	@Override
	public void saveAllBoletins(List<BoletimAlunoComponente> boletins) {
		for (BoletimAlunoComponente bac : boletins) {
			manager.persist(bac);
		}
		
		manager.flush();
		manager.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBoletimAlunos(Long id) {
		Query query = manager.createNativeQuery("SELECT BA.id, BA.boletim_id, BA.aluno_id "
				+ "FROM boletim_aluno BA "
				+ "WHERE BA.boletim_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllComponentesBoletim(Long id) {
		Query query = manager.createNativeQuery("SELECT BAC.id, BAC.boletim_componente_id, C.nome, C.tipo_avaliacao, BAC.conceito FROM boletim_aluno_componente BAC "
				+ "INNER JOIN boletim_componente BC ON BC.id = BAC.boletim_componente_id "
				+ "INNER JOIN componente C ON C.id = BC.componente_id "
				+ "INNER JOIN boletim_aluno BA ON BA.id = BAC.boletim_aluno_id "
				+ "WHERE BA.aluno_id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}

	public void updateComponenteAlunoBoletins(BoletimAlunoComponente componente) {
		Query query = manager.createNativeQuery("UPDATE boletim_aluno_componente "
				+ "SET conceito = ? "
				+ "WHERE id = ?")
				.setParameter(1, componente.getConceito())
				.setParameter(2, componente.getId());
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findHalfBoletimFirebase(Long id) {
		Query query = manager.createNativeQuery("SELECT P.nome, A.matricula, B.id AS boletim_id, B.situacao, BA.id AS boletim_aluno_id, A.id AS aluno_id "
				+ "FROM boletim_aluno_componente BAC "
				+ "INNER JOIN boletim_aluno BA ON BA.id = BAC.boletim_aluno_id "
				+ "INNER JOIN aluno A ON A.id = BA.aluno_id "
				+ "INNER JOIN boletim B ON B.id = BA.boletim_id "
				+ "INNER JOIN boletim_componente BC ON BC.boletim_id = B.id "
				+ "INNER JOIN componente C ON C.id = BC.componente_id "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "WHERE BAC.id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findCompleteBoletimFirebase(Long id) {
		Query query = manager.createNativeQuery("SELECT C.nome, C.media_aprovacao, BAC.conceito "
				+ "FROM boletim_aluno_componente BAC "
				+ "INNER JOIN boletim_componente BC ON BAC.boletim_componente_id = BC.id "
				+ "INNER JOIN componente C ON C.id = BC.componente_id "
				+ "INNER JOIN boletim_aluno BA ON BA.id = BAC.boletim_aluno_id "
				+ "WHERE BA.id = ?")
				.setParameter(1, id);
		
		return query.getResultList();
	}
}
