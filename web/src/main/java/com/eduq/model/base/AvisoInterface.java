package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Aviso;

public interface AvisoInterface {
	
	void save(Aviso aviso);
	
	void update(Aviso aviso);
	
	void delete(Long id);
	
	Aviso findById(Long id);
	
	List<Aviso> findAll();
	
	List<Object[]> findByEscola(Long idEscola);
	
	List<Object[]> findByProfessor(Long idProfessor);
	
	List<Aviso> findByDay(Long idEscola, String data);
}
