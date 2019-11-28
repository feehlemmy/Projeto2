package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Evento;

public interface EventoServiceInterface {
	
	void salvar(Evento evento);
	
	void editar(Evento evento);
	
	void excluir(Long id);
	
	Evento buscarPorId(Long id);
	
	List<Evento> buscarPorEscola(Long escolaId);
	
	List<Evento> buscarTodos();
	
	List<Evento> buscarPorDia(Long escolaId, String data);
}
