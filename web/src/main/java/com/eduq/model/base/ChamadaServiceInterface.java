package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Chamada;

public interface ChamadaServiceInterface {
	void salvar(Chamada chamada);

    void editar(Chamada chamada);
    
    void finalizarChamada(Long id);

    void excluir(Long id);

    Chamada buscarPorId(Long id);
    
    List<Object[]> buscarPorEscola(Long idEscola);
    
    List<Object[]> buscarPorProfessor(Long idProfessor);
    
    List<Object[]> buscarPorEscolaNaoRealizada(Long idEscola);
    
    List<Object[]> buscarPorProfessorNaoRealizada(Long idProfessor);

    List<Chamada> buscarTodos();
    
    List<Object[]> buscarPorChamada(Long id);
    
    List<Object[]> buscarAlunoChamada(Long id);
    
    void salvarTodos(List<AlunoChamada> alunos);
    
    void editarPresenca(AlunoChamada aluno);
    
    List<Chamada> buscarPorPeriodo(Long idEscola, String data, String periodo);
    
    List<Object[]> getFaltasAluno(Long id);
}
