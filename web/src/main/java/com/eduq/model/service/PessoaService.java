package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.PessoaServiceInterface;
import com.eduq.model.dao.PessoaDAO;
import com.eduq.model.entity.Pessoa;

@Service
@Transactional(readOnly = false)
public class PessoaService implements PessoaServiceInterface {
	
	@Autowired
	private PessoaDAO dao;

	@Override
	public void salvar(Pessoa pessoa) {
		dao.save(pessoa);
	}

	@Override
	public void editar(Pessoa pessoa) {
		dao.update(pessoa);	
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Pessoa buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pessoa> buscarTodos() {
		return dao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public void editarPessoaAluno(Pessoa pessoa) {
		// TODO Auto-generated method stub
	}
}
