package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.AlunoServiceInterface;
import com.eduq.model.dao.AlunoDAO;
import com.eduq.model.dao.PessoaDAO;
import com.eduq.model.entity.Aluno;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Pessoa;

@Service
@Transactional(readOnly = false)
public class AlunoService implements AlunoServiceInterface {
	
	@Autowired
	private AlunoDAO dao;
	

	@Autowired
	private PessoaDAO pessoaDAO;
	
	@Override
	public void salvar(Aluno aluno) {
		dao.save(aluno);
	}

	@Override
	public void editar(Aluno aluno) {
		dao.update(aluno);
	}

	@Override
	public void excluir(Long id) {
		dao.excludeAluno(id);
	}

	@Override
	public void updateAluno(Aluno aluno) {
		Pessoa pessoa = new Pessoa();
		
		pessoa.setCpf(aluno.getCpf());
		pessoa.setNome(aluno.getNome());
		pessoa.setId(aluno.getPessoaId());
		
		dao.updateAluno(aluno);
		pessoaDAO.updatePessoaAluno(pessoa);
	}

	@Override
	@Transactional(readOnly = true)
	public Aluno buscarPorId(Long id) {
		Aluno aluno = new Aluno();
		List<Object[]> listAluno = dao.searchById(id);
        Object[] result = listAluno.get(0);
        
		aluno.setNome(result[0].toString());
		aluno.setCpf(result[1].toString());
		aluno.setId(Long.parseLong(result[2].toString()));
		aluno.setCodigoGeradoEscola(result[3].toString());
		aluno.setCpfResponsavel(result[4].toString());
		aluno.setDataMatricula(result[5].toString());
		aluno.setDataNascimento(result[6].toString());
		aluno.setMatricula(result[7].toString());
		aluno.setNomeMae(result[8].toString());
		aluno.setNomePai(result[9].toString());
		aluno.setNomeResponsavel(result[10].toString());
		aluno.setSituacao(result[11].toString());
		aluno.setPessoaId(Long.parseLong(result[12].toString()));
		
		return aluno; 
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarTodos() {
		return dao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorEscola(Long escolaId) {
		return dao.findByEscola(escolaId);
	}
	
	public String gerarCodigoEscola(Escola escola, String cpf) {
		String codigoGerado;
		Integer codigoEscola;
		
		codigoEscola = escola.getCodigoMec();
		codigoGerado = generateSecurityKey(codigoEscola, cpf);
		
		return codigoGerado;
	}
	
	private String generateSecurityKey(Integer codigo, String cpf){
		String inicioCpf;
		String tmpValor = String.valueOf(codigo);
		String securityKey = new String();
		char[] letras = null;
		
		// Quebra do código da escola nos 5 primeiros dígitos
		for (int i = 3; i < tmpValor.length(); i++) {
        	securityKey = securityKey + tmpValor.substring(i, i+1);
        }
       
        // Quebra do CPF do professor nos 
		letras = cpf.toCharArray();
		inicioCpf = "" + letras[0];
		inicioCpf = inicioCpf + letras[1];
		inicioCpf = inicioCpf + letras[2];
        
		securityKey = securityKey + inicioCpf;
		
        return securityKey;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorSemClasse(Long escolaId) {
		return dao.findByNoClass(escolaId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorClasse(Long id) {
		return dao.findByClass(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarPorClasseBoletim(Long id) {
		return dao.findByClasseBoletim(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorNome(String nome, Long id) {
		return dao.findByName(nome, id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorProfessor(Long id) {
		return dao.findByProfessor(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> buscarPorNomeProfessor(String nome, Long id) {
		return dao.findByNameProfessor(nome, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Pessoa buscarDadosResponsavel(String codigoAluno) {
		Pessoa responsavel = new Pessoa();
		List<Object[]> aux = dao.findDataResponsavel(codigoAluno);
		
		Object[] result = aux.get(0);
		responsavel.setNome(result[0].toString());
		responsavel.setTelefone(result[1].toString());
		responsavel.setEmail(result[2].toString());
		responsavel.setEndereco(result[3].toString());
		responsavel.setMunicipio(result[4].toString());
		
		return responsavel;
	}
}
