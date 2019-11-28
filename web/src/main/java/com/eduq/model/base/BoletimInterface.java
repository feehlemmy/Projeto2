package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Boletim;
import com.eduq.model.entity.BoletimAluno;
import com.eduq.model.entity.BoletimAlunoComponente;
import com.eduq.model.entity.BoletimComponente;
import com.eduq.model.entity.ComponenteCurricular;

public interface BoletimInterface {
	void save(Boletim boletim);

    void update(Boletim boletim);
    
    void updateBoletim(Boletim boletim);
    
    void generatedBoletim(Long id);

    void delete(Long id);
    
    Boletim findById(Long id);
    
    List<Boletim> findAll();
    
    List<Boletim> findByEscola(Long escolaId);
    
    List<ComponenteCurricular> findByBoletim(Long boletimId);
    
    List<Object[]> findComponentesBoletim(Long id);
    
    List<Object[]> findBoletimAlunos(Long id);
    
    void saveComponente(BoletimComponente boletimComponente);
    
    void removeComponente(Long id);
    
    void saveAll(List<BoletimAluno> boletins);
    
    void saveAllBoletins(List<BoletimAlunoComponente> boletins);
    
    List<Object[]> findAllComponentesBoletim(Long id);
}
