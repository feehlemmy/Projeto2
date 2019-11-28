package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.ComponenteInterface;
import com.eduq.model.entity.ComponenteCurricular;

@Repository
public class ComponenteDAO extends BaseDAO<ComponenteCurricular, Long> implements ComponenteInterface {
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ComponenteCurricular> findByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT C.id, C.nome, C.carga_horaria, C.tipo_avaliacao, C.media_aprovacao FROM componente C WHERE "
				+ "C.escola_id = ?")
				.setParameter(1, escolaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComponenteCurricular> findByNotBoletim(Long escolaId, Long boletimId) {
		Query query = manager.createNativeQuery("SELECT C.id, C.nome, C.carga_horaria, C.tipo_avaliacao, C.media_aprovacao "
				+ "FROM componente C "
				+ "INNER JOIN escola E ON E.id = C.escola_id "
				+ "INNER JOIN boletim B ON B.escola_id = E.id "
				+ "LEFT JOIN boletim_componente BC ON BC.componente_id = C.id "
				+ "WHERE E.id = ? AND B.id = ? AND BC.componente_id IS NULL ORDER BY C.id")
				.setParameter(1, escolaId)
				.setParameter(2, boletimId);
		return query.getResultList();
	}
}
