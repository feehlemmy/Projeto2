package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Escola;

public interface EscolaServiceInterface {
	void salvar(Escola escola);
	
	void editar(Escola escola);
	
	void excluir(Long id);
	
	Escola buscarPorId(Long id);
	
	Escola buscarPorCodigo(Integer codigoMecEscola) throws Exception;
	
	void updateSenhaEscola(Escola escola);
	
	void completarCadastroEscola(Escola escola);
	
	List<Object[]> buscarRequisicoes(Long id);
	
	List<Escola> buscarTodos();
}
