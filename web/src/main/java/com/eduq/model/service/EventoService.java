package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.EventoServiceInterface;
import com.eduq.model.dao.EventoDAO;
import com.eduq.model.dao.FirebaseDAO;
import com.eduq.model.entity.Evento;

@Service
@Transactional(readOnly = false)
public class EventoService implements EventoServiceInterface{

	@Autowired
	private EventoDAO dao;
	
	@Autowired
	private FirebaseDAO firebase;
	
	@Override
	public void salvar(Evento evento) {
		dao.save(evento);
		firebase.saveEvento(evento);
	}

	@Override
	public void editar(Evento evento) {
		dao.update(evento);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Evento buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Evento> buscarPorEscola(Long escolaId) {
		return dao.findByEscola(escolaId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Evento> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public List<Evento> buscarPorDia(Long escolaId, String data) {
		return dao.findByDay(escolaId, data);
	}
}
