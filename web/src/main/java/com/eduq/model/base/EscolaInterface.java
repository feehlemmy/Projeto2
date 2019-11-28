package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Escola;

public interface EscolaInterface{
	void save(Escola escola);

    void update(Escola escola);

    void delete(Long id);

    Escola findById(Long id);
    
    List<Escola> readByLogin(Integer codigoMec, String senha);
    
    List<Escola> readByCodigo(Integer codigoMec);
    
    void updateSenhaEscola(Escola escola);
    
    void finishCreateEscola(Escola escola);
    
    List<Object[]> findRequisicoes(Long id);

    List<Escola> findAll();	
}
