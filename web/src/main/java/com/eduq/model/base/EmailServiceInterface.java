package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Email;

public interface EmailServiceInterface {
	void salvar(Email email);
	
	void editar(Email email);
	
	void excluir(Long id);
	
	Email buscarPorId(Long id);
	
	List<Email> buscarTodos();
}
