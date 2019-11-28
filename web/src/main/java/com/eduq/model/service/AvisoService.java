package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.AvisoServiceInterface;
import com.eduq.model.dao.AvisoDAO;
import com.eduq.model.dao.FirebaseDAO;
import com.eduq.model.entity.Aviso;

@Service
@Transactional(readOnly = false)
public class AvisoService implements AvisoServiceInterface{

	@Autowired
	private AvisoDAO dao;
	
	@Autowired
	private FirebaseDAO firebase;
	
	@Override
	public void salvar(Aviso aviso) {
		dao.save(aviso);
		firebase.saveAviso(aviso);
	}

	@Override
	public void editar(Aviso aviso) {
		dao.update(aviso);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	public Aviso buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aviso> buscarTodos() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorEscola(Long idEscola) {
		return dao.findByEscola(idEscola);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorProfessor(Long idProfessor) {
		return dao.findByProfessor(idProfessor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aviso> buscarPorData(Long idEscola, String data) {
		return dao.findByDay(idEscola, data);
	}

}
