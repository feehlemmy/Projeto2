package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.AvisoInterface;
import com.eduq.model.base.BaseDAO;
import com.eduq.model.entity.Aviso;

@Repository
public class AvisoDAO extends BaseDAO<Aviso, Long> implements AvisoInterface {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByEscola(Long idEscola) {
		Query query = manager.createNativeQuery("SELECT A.id, A.ano_classe, A.conteudo_aviso, A.data_aviso, A.classe_id, A.tipo_aviso, A.titulo_aviso FROM Aviso A "
				+ "WHERE A.escola_id = ?")
				.setParameter(1, idEscola);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByProfessor(Long idProfessor) {
		Query query = manager.createNativeQuery("SELECT A.id, A.ano_classe, A.conteudo_aviso, A.data_aviso, A.classe_id, A.tipo_aviso, A.titulo_aviso "
				+ "FROM aviso A "
				+ "WHERE A.escola_id = ?")
				.setParameter(1, idProfessor);
		
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aviso> findByDay(Long idEscola, String data) {
		Query query = manager.createNativeQuery("SELECT * FROM Aviso A "
				+ "WHERE A.escola_id = ? AND A.data_aviso = ?")
				.setParameter(1, idEscola)
				.setParameter(2, data);
		
		return query.getResultList();
	}
}
