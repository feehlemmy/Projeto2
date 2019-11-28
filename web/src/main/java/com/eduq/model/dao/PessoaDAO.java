package com.eduq.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.PessoaInterface;
import com.eduq.model.entity.Pessoa;

@Repository
public class PessoaDAO extends BaseDAO<Pessoa, Long> implements PessoaInterface {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void updatePessoaAluno(Pessoa pessoa) {
		Query query = manager.createNativeQuery("UPDATE pessoa SET nome = ?, cpf = ? WHERE id = ?")
				.setParameter(1, pessoa.getNome())
				.setParameter(2, pessoa.getCpf())
				.setParameter(3, pessoa.getId());
		query.executeUpdate();
	}
}
