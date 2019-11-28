package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.ComponenteServiceInterface;
import com.eduq.model.dao.ComponenteDAO;
import com.eduq.model.entity.ComponenteCurricular;

@Service
@Transactional(readOnly = false)
public class ComponenteService implements ComponenteServiceInterface{

	@Autowired
	private ComponenteDAO dao;
	
	@Override
	public void salvar(ComponenteCurricular componente) {
		try {
			dao.save(componente);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(ComponenteCurricular componente) {
		dao.update(componente);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ComponenteCurricular buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComponenteCurricular> buscarTodos() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComponenteCurricular> buscarPorEscola(Long escolaId) {
		return dao.findByEscola(escolaId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComponenteCurricular> findByNotBoletim(Long escolaId, Long boletimId) {
		return dao.findByNotBoletim(escolaId, boletimId);
	}

}
