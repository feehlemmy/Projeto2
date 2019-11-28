package com.eduq.model.base;

import java.util.List;

import com.eduq.model.entity.Email;

public interface EmailInterface {
	void save(Email email);

    void update(Email email);

    void delete(Long id);

    Email findById(Long id);
    
    List<Email> findByNotSend();
    
    List<Email> findByNotSendProfessor();
    
    List<Email> findByNotSendEscola();
    
    List<Email> findByNotSendSenha();
    
    List<Email> findByNotSendSenhaProfessor();

    List<Email> findAll();
}
