package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.ChamadaServiceInterface;
import com.eduq.model.dao.ChamadaDAO;
import com.eduq.model.dao.FirebaseDAO;
import com.eduq.model.entity.AlunoChamada;
import com.eduq.model.entity.Chamada;

@Service
@Transactional(readOnly = false)
public class ChamadaService implements ChamadaServiceInterface{
	
	@Autowired
	private ChamadaDAO dao;
	
	@Autowired
	private FirebaseDAO firebase;

	@Override
	public void salvar(Chamada chamada) {
		dao.save(chamada);
	}

	@Override
	public void editar(Chamada chamada) {
		dao.update(chamada);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Chamada buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorEscola(Long idEscola) {
		return dao.findByEscola(idEscola);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chamada> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public void salvarTodos(List<AlunoChamada> alunos) {
		dao.saveAll(alunos);
		firebase.saveAlunoChamada(alunos);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorProfessor(Long idProfessor) {
		return dao.findByProfessor(idProfessor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorEscolaNaoRealizada(Long idEscola) {
		return dao.findByEscolaNotDone(idEscola);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorProfessorNaoRealizada(Long idProfessor) {
		return dao.findByProfessorNotDone(idProfessor);
	}

	@Override
	public void finalizarChamada(Long id) {
		dao.finishChamada(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorChamada(Long id) {
		return dao.findByChamada(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarAlunoChamada(Long id) {
		return dao.findByAlunoChamada(id);
	}

	@Override
	public void editarPresenca(AlunoChamada aluno) {
		dao.updatePresenca(aluno);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chamada> buscarPorPeriodo(Long idEscola, String data, String periodo) {
		return dao.findByPeriodo(idEscola, data, periodo);
	}

	@Override
	public List<Object[]> getFaltasAluno(Long id) {
		return dao.getFaltasAluno(id);
	}
}
