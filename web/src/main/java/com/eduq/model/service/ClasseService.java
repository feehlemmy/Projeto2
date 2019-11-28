package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.ClasseServiceInterface;
import com.eduq.model.dao.AlunoDAO;
import com.eduq.model.dao.ClasseDAO;
import com.eduq.model.dao.FirebaseDAO;
import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Classe;

@Service
@Transactional(readOnly = false)
public class ClasseService implements ClasseServiceInterface {
	
	@Autowired
	private ClasseDAO dao;
	
	@Autowired
	private AlunoDAO alunoDao;
	@Autowired
	private FirebaseDAO firebase;

	@Override
	public void save(Classe classe) {
		try {
			dao.save(classe);
			firebase.saveClasse(classe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Classe classe) {
		try {
			dao.update(classe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Classe findById(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Classe> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Classe> findByEscola(Long escolaId) {
		return dao.findByEscola(escolaId);
	}

	@Override
	public List<Classe> findByProfessor(Long escolaId, Long professorId) {
		return dao.findByProfessor(escolaId, professorId);
	}
	
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorAlunoSemClasse(Long escolaId) {
		return alunoDao.findByNoClass(escolaId);
	}
	
	@Transactional(readOnly = true)
	public List<Classe> buscarPorClasse(Long escolaId) {
		return dao.findByClasse(escolaId);
	}

	@Override
	public void updateAlunos(Classe classe) {
		Aluno aluno = new Aluno();

		Long idAluno = Long.parseLong(classe.getDescricao());
		aluno.setId(idAluno);
		
		try {
			dao.updateAlunos(classe, idAluno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeAluno(Long id) {
		dao.removeAluno(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Classe> buscarClassePorPeriodo(Long escolaId, String periodo) {
		return dao.findByPeriodo(escolaId, periodo);
	}

	@Override
	@Transactional(readOnly = true)
	public Classe findByAluno(Long id) {
		Classe classe = new Classe();
		Object[] aux = null;
		
		List<Object[]> classes = dao.findByAluno(id);;
		aux = classes.get(0);
		
		classe.setNome(aux[0].toString());
		classe.setDescricao(aux[1].toString());
		
		return classe;
	}
}
