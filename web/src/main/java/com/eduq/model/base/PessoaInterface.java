package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Pessoa;

public interface PessoaInterface {
	void save(Pessoa pessoa);

    void update(Pessoa pessoa);

    void delete(Long id);

    Pessoa findById(Long id);
    
    void updatePessoaAluno(Pessoa pessoa);

    List<Pessoa> findAll();	
}
