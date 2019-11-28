package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Classe;

public interface ClasseServiceInterface {
	void save(Classe classe);

    void update(Classe classe);
    
    void updateAlunos(Classe classe);

    void delete(Long id);
    
    void removeAluno(Long id);

    Classe findById(Long id);
    
    List<Classe> findByEscola(Long escolaId);
    
    List<Classe> findByProfessor(Long escolaId, Long professorId);

    List<Classe> findAll();
    
    List<Classe> buscarClassePorPeriodo(Long escolaId, String periodo);
    
    Classe findByAluno(Long id);
}
