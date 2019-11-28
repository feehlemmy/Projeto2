package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;

public interface ProfessorInterface {
	void save(Professor professor);

    void update(Professor professor);

    void delete(Long id);
    
    void updateProfessor(Professor professor);

    Professor findById(Long id);
    
    List<Object[]> readByLogin(String codigoGerado, String senha);
    
    List<Pessoa> findByProfessor(Long escolaId);
    
    List<Object[]> findByCodigo(String codigoGerado);
    
    List<Object[]> findRequisicoes(Long id);
    
    void updateSenhaProfessor(Professor professor);

    List<Professor> findAll();
}
