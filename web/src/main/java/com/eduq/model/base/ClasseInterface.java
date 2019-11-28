package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Classe;

public interface ClasseInterface {
	void save(Classe classe);

    void update(Classe classe);
    
    void updateAlunos(Classe classe, Long idAluno);

    void delete(Long id);
    
    void removeAluno(Long id);

    Classe findById(Long id);
    
    List<Classe> findByEscola(Long escolaId);
    
    List<Classe> findByClasse(Long escolaId);
    
    List<Classe> findByProfessor(Long escolaId, Long professorId);

    List<Classe> findAll();
    
    List<Classe> findByPeriodo(Long escolaId, String periodo);
    
    List<Object[]> findByAluno(Long id);
}
