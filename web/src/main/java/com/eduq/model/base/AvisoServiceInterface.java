package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Aviso;

public interface AvisoServiceInterface {

	void salvar(Aviso aviso);
	
	void editar(Aviso aviso);
	
	void excluir(Long id);
	
	Aviso buscarPorId(Long id);
	
	List<Aviso> buscarTodos();
	
	List<Object[]> buscarPorEscola(Long idEscola);
	
	List<Object[]> buscarPorProfessor(Long idProfessor);
	
	List<Aviso> buscarPorData(Long idEscola, String data);
}
