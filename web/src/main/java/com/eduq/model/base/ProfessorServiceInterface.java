package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Professor;

public interface ProfessorServiceInterface {
	void salvar(Professor professor);
	
	void editar(Professor professor);
	
	void excluir(Long id);
	
	void updateProfessor(Professor professor);
	
	Professor buscarPorCodigo(String codigoProfessor) throws Exception;
	
	void updateSenhaProfessor(Professor professor);
	
	Professor buscarPorId(Long id);
	
	List<Object[]> buscarRequisicoes(Long id);
	
	List<Professor> buscarTodos();
}
