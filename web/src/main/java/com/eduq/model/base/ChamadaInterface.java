package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Chamada;

public interface ChamadaInterface {
	
	void save(Chamada chamada);

    void update(Chamada chamada);
    
    void finishChamada(Long id);

    void delete(Long id);

    Chamada findById(Long id);
    
    List<Object[]> findByEscola(Long idEscola);
    
    List<Object[]> findByEscolaNotDone(Long idEscola);
    
    List<Object[]> findByProfessor(Long idProfessor);
    
    List<Object[]> findByProfessorNotDone(Long idProfessor);

    List<Chamada> findAll();
    
    List<Object[]> findByChamada(Long id);
    
    List<Object[]> findByAlunoChamada(Long id);
    
    void saveAll(List<AlunoChamada> alunos);
    
    void updatePresenca(AlunoChamada aluno);
    
    List<Chamada> findByPeriodo(Long idEscola, String data, String periodo);
    
    List<Object[]> getFaltasAluno(Long id);
}
