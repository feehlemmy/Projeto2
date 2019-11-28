package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.EventoInterface;
import com.eduq.model.entity.Evento;

@Repository
public class EventoDAO extends BaseDAO<Evento, Long> implements EventoInterface{

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> findByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT * FROM evento E "
				+ "WHERE E.escola_id = ?")
				.setParameter(1, escolaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evento> findByDay(Long escolaId, String data) {
		Query query = manager.createNativeQuery("SELECT * FROM evento E "
				+ "WHERE E.escola_id = ? AND E.data_inicio = ?")
				.setParameter(1, escolaId)
				.setParameter(2, data);
		return query.getResultList();
	}
}
