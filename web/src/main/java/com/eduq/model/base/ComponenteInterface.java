package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.ComponenteCurricular;

public interface ComponenteInterface {
	void save(ComponenteCurricular componente);

    void update(ComponenteCurricular componente);

    void delete(Long id);

    ComponenteCurricular findById(Long id);
    
    List<ComponenteCurricular> findByEscola(Long escolaId);
    
    List<ComponenteCurricular> findByNotBoletim(Long escolaId, Long boletimId);

    List<ComponenteCurricular> findAll();	
}
