package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Pessoa;

public interface PessoaServiceInterface {
	
	void salvar(Pessoa pessoa);
	
	void editar(Pessoa pessoa);
	
	void excluir(Long id);
	
	Pessoa buscarPorId(Long id);
	
	void editarPessoaAluno(Pessoa pessoa);
	
	List<Pessoa> buscarTodos();
}
