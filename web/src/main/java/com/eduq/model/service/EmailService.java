package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.EmailServiceInterface;
import com.eduq.model.dao.EmailDAO;
import com.eduq.model.entity.Email;

@Service
@Transactional(readOnly = false)
public class EmailService implements EmailServiceInterface{
	
	@Autowired
	private EmailDAO dao;
	
	@Override
	public void salvar(Email email) {
		try {
			dao.save(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Email email) {
		try {
			dao.update(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluir(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = true)
	public Email buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Email> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	public List<Email> buscarPorNaoEnviada(){
		return dao.findByNotSend();
	}
	
	@Transactional(readOnly = true)
	public List<Email> buscarPorNaoEnviadaProfessor(){
		return dao.findByNotSendProfessor();
	}
	
	@Transactional(readOnly = true)
	public List<Email> buscarPorNaoEnviadaEscola(){
		return dao.findByNotSendEscola();
	}
	
	@Transactional(readOnly = true)
	public List<Email> buscarPorNaoEnviadaSenhaEscola(){
		return dao.findByNotSendSenha();
	}

	@Transactional(readOnly = true)
	public List<Email> buscarPorNaoEnviadaSenhaProfessor() {
		return dao.findByNotSendSenhaProfessor();
	}
}
