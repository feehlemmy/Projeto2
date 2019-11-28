package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Pessoa;

public interface AlunoServiceInterface {
	void salvar(Aluno aluno);
	
	void editar(Aluno aluno);
	
	void excluir(Long id);
	
	void updateAluno(Aluno aluno);
	
	Aluno buscarPorId(Long id);
	
	Pessoa buscarDadosResponsavel(String codigoAluno);
	
	List<Aluno> buscarPorEscola(Long escolaId);
	
	List<Object[]> buscarPorClasseBoletim(Long id);
	
	List<Aluno> buscarPorSemClasse(Long escolaId);
	
	List<Aluno> buscarPorProfessor(Long id);
	
	List<Aluno> buscarPorClasse(Long id);
	
	List<Aluno> buscarPorNome(String nome, Long id);
	
	List<Aluno> buscarPorNomeProfessor(String nome, Long id);
	
	List<Aluno> buscarTodos();
}
