package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.BoletimInterface;
import com.eduq.model.dao.BoletimDAO;
import com.eduq.model.entity.Boletim;
import com.eduq.model.entity.BoletimAluno;
import com.eduq.model.entity.BoletimAlunoComponente;
import com.eduq.model.entity.BoletimComponente;
import com.eduq.model.entity.ComponenteCurricular;

@Service
@Transactional(readOnly = false)
public class BoletimService implements BoletimInterface{
	
	@Autowired
	private BoletimDAO dao;
	
	@Override
	public void save(Boletim boletim) {
		dao.save(boletim);
	}

	@Override
	public void update(Boletim boletim) {
		dao.update(boletim);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Boletim findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public List<Boletim> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Boletim> findByEscola(Long escolaId) {
		return dao.findByEscola(escolaId);
	}

	@Override
	public void saveComponente(BoletimComponente boletimComponente) {
		dao.saveComponente(boletimComponente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ComponenteCurricular> findByBoletim(Long boletimId) {
		return dao.findByBoletim(boletimId);
	}

	@Override
	public void removeComponente(Long id) {
		dao.removeComponente(id);
	}

	@Override
	public void updateBoletim(Boletim boletim) {
		dao.updateBoletim(boletim);
	}

	@Override
	public void saveAll(List<BoletimAluno> boletins) {
		dao.saveAll(boletins);
	}

	@Override
	public void generatedBoletim(Long id) {
		dao.generatedBoletim(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findComponentesBoletim(Long id) {
		return dao.findComponentesBoletim(id);
	}

	@Override
	public void saveAllBoletins(List<BoletimAlunoComponente> boletins) {
		dao.saveAllBoletins(boletins);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findBoletimAlunos(Long id) {
		return dao.findBoletimAlunos(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> findAllComponentesBoletim(Long id) {
		return dao.findAllComponentesBoletim(id);
	}

	public void updateComponenteAlunoBoletins(List<BoletimAlunoComponente> boletins) {
		BoletimAlunoComponente aux = null;
		
		for(int i = 0; i < boletins.size(); i++) {
			aux = new BoletimAlunoComponente();
			aux = boletins.get(i);
			
			dao.updateComponenteAlunoBoletins(aux);
		}
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> findHalfBoletimFirebase(Long id) {
		return dao.findHalfBoletimFirebase(id);
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> findCompleteBoletimFirebase(Long id) {
		return dao.findCompleteBoletimFirebase(id);
	} 
}
