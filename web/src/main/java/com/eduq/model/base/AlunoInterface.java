package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Aluno;

public interface AlunoInterface {
	void save(Aluno aluno);

    void update(Aluno aluno);

    void delete(Long id);
    
    void excludeAluno(Long id);
    
    void updateAluno(Aluno aluno);

    Aluno findById(Long id);
    
    List<Object[]> findDataResponsavel(String codigoAluno);
    
    List<Object[]> searchById(Long id);
    
    List<Aluno> findByEscola(Long escolaId);
    
    List<Aluno> findByClass(Long idClasse);
    
    List<Aluno> findByNoClass(Long escolaId);
    
    List<Aluno> findByProfessor(Long id);
    
    List<Aluno> findByName(String nome, Long id);
    
    List<Aluno> findByNameProfessor(String nome, Long id);

    List<Aluno> findAll();
    
    List<Object[]> findByClasseBoletim(Long id);
}
