package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.ComponenteCurricular;

public interface ComponenteServiceInterface {
	void salvar(ComponenteCurricular componente);
	
	void editar(ComponenteCurricular componente);
	
	void excluir(Long id);
	
	ComponenteCurricular buscarPorId(Long id);
	
	List<ComponenteCurricular> buscarPorEscola(Long escolaId);
	
	List<ComponenteCurricular> findByNotBoletim(Long escolaId, Long boletimId);
	
	List<ComponenteCurricular> buscarTodos();
}
