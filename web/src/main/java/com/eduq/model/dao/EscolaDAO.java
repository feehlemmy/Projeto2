package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.EscolaInterface;
import com.eduq.model.entity.DiaLetivo;
import com.eduq.model.entity.Escola;

@Repository
public class EscolaDAO extends BaseDAO<Escola, Long> implements EscolaInterface {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Escola> readByLogin(Integer codigoMec, String senha) {
		
		String jpql = new StringBuilder("SELECT E FROM Escola E ")
				.append("WHERE E.codigoMec = ?1 AND E.senha = ?2 AND E.cep IS NOT NULL")
				.toString();
		return createQuery(jpql, codigoMec, senha);
	}

	@Override
	public List<Escola> readByCodigo(Integer codigoMecEscola) {
		String jpql = new StringBuilder("SELECT E FROM Escola E ")
				.append("WHERE E.codigoMec = ?1")
				.toString();
		return createQuery(jpql, codigoMecEscola);
	}

	@Override
	public void updateSenhaEscola(Escola escola) {
		Query query = manager.createNativeQuery("UPDATE escola SET senha=?, confirmacao=? WHERE id = ?")
				.setParameter(1, escola.getSenha())
				.setParameter(2, escola.getConfirmacao())
				.setParameter(3, escola.getId());
		query.executeUpdate();
	}

	@Override
	public void finishCreateEscola(Escola escola) {
		Query query = manager.createNativeQuery("UPDATE escola SET telefone = ?, endereco = ?, uf = ?, municipio = ?, cep = ? WHERE codigo_mec = ?")
				.setParameter(1, escola.getTelefone())
				.setParameter(2, escola.getEndereco())
				.setParameter(3, escola.getUf())
				.setParameter(4, escola.getMunicipio())
				.setParameter(5, escola.getCep())
				.setParameter(6, escola.getCodigoMec());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findRequisicoes(Long id) {
		Query query = manager.createNativeQuery("SELECT R.id, P.nome, R.tipo_requisicao, R.requisicao_aluno_id, R.observacao FROM requisicao R "
				+ "INNER JOIN responsavel RP ON RP.id = R.requisicao_responsavel_id "
				+ "INNER JOIN pessoa P ON P.id = RP.pessoa_id "
				+ "WHERE R.escola_id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}
	
	public void saveAllDiasLetivos(List<DiaLetivo> dias) {		
		for (DiaLetivo dl : dias) {
			manager.persist(dl);
		}
		
		manager.flush();
		manager.clear();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findDiasLetivosByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT DL.id, DL.escola_id, DL.dia FROM dia_letivo DL "
				+ "WHERE DL.escola_id = ?")
				.setParameter(1, escolaId);
		return query.getResultList();
	}
}
