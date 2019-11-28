package com.eduq.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.EmailInterface;
import com.eduq.model.entity.Email;

@Repository
public class EmailDAO extends BaseDAO<Email, Long> implements EmailInterface{

	@Override
	public List<Email> findByNotSend() {
		String jpql = new StringBuilder("SELECT E FROM Email E ")
				.append("WHERE E.enviado = 'nao' AND E.descricaoDuvida IS NOT NULL AND E.caracteristica = 'duvida'")
				.toString();
		return createQuery(jpql);
	}

	@Override
	public List<Email> findByNotSendProfessor() {
		String jpql = new StringBuilder("SELECT E FROM Email E ")
				.append("WHERE E.enviado = 'nao' AND E.emailProfessor IS NOT NULL AND E.codigoMec IS NOT NULL AND E.caracteristica = 'cadastro'")
				.toString();
		return createQuery(jpql);	
	}

	@Override
	public List<Email> findByNotSendEscola() {
		String jpql = new StringBuilder("SELECT E FROM Email E ")
				.append("WHERE E.enviado = 'nao' AND E.codigoMec IS NOT NULL AND E.senhaEscola IS NULL AND E.caracteristica = 'cadastro'")
				.toString();
		return createQuery(jpql);
	}

	@Override
	public List<Email> findByNotSendSenha() {
		String jpql = new StringBuilder("SELECT E FROM Email E ")
				.append("WHERE E.enviado = 'nao' AND E.senhaEscola IS NOT NULL AND E.caracteristica = 'senha'")
				.toString();
		return createQuery(jpql);
	}

	@Override
	public List<Email> findByNotSendSenhaProfessor() {
		String jpql = new StringBuilder("SELECT E FROM Email E ")
				.append("WHERE E.enviado = 'nao' AND E.emailProfessor IS NOT NULL AND E.senhaProfessor IS NOT NULL AND E.caracteristica = 'senha'")
				.toString();
		return createQuery(jpql);
	}
}
