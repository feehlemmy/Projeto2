package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Evento;

public interface EventoInterface {
	void save(Evento evento);

    void update(Evento evento);

    void delete(Long id);
    
    Evento findById(Long id);
    
    List<Evento> findAll();
    
    List<Evento> findByEscola(Long escolaId);
    
    List<Evento> findByDay(Long escolaId, String data);
}
