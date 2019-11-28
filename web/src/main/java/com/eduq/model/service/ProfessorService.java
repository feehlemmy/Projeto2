package com.eduq.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.ProfessorServiceInterface;
import com.eduq.model.dao.ProfessorDAO;
import com.eduq.model.entity.Escola;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;

@Service
@Transactional(readOnly = false)
public class ProfessorService implements ProfessorServiceInterface {
	
	@Autowired
	private ProfessorDAO dao;
	
	@Override
	public void salvar(Professor professor) {
		dao.save(professor);
	}

	@Override
	public void editar(Professor professor) {
		dao.updateProfessor(professor);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Professor buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Professor> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Pessoa> buscarProfessorPorEscola(Long escolaId) {
		return dao.findByProfessor(escolaId);
	}
	
	@Transactional(readOnly = true)
    public Professor verifyLogin(String codigoGerado, String senha) throws Exception{
		Professor professor = new Professor();
		List<Object[]> professorLogin = dao.readByLogin(codigoGerado, senha);
        Object[] result = professorLogin.get(0);
        
        if(professorLogin.get(0) == null) {
        	throw new Exception("Professor não cadastrado.");
        } else {
        	professor.setId(Long.parseLong(result[0].toString()));
        	professor.setNome(result[1].toString());
        	professor.setCpf(result[2].toString());
        	professor.setEmail(result[4].toString());
        	professor.setCep(result[3].toString()); // ID Pessoa
        	professor.setEscolaId(Long.parseLong(result[5].toString()));
        	professor.setCodigoGeradoEscola(result[6].toString());
        	professor.setSenhaGerada(result[7].toString());
        	return professor;
        }
    }
	
	public String gerarCodigoEscola(Escola escola, String cpf) {
		String codigoGerado;
		Integer codigoEscola;
		
		codigoEscola = escola.getCodigoMec();
		codigoGerado = generateSecurityKey(codigoEscola, cpf);
		
		return codigoGerado;
	}
	
	public String gerarSenhaAleatoria(){
        int maxCharacters = 8;
        String[] characters = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
       
        StringBuilder senhaAleatoria = new StringBuilder();

        for (int i = 0; i < maxCharacters; i++) {
            int posicao = (int) (Math.random() * characters.length);
            senhaAleatoria.append(characters[posicao]);
        }
        
        return senhaAleatoria.toString();
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
	
	public String generateSecurityKeySenha(){
        int maxCharacters = 8;
        String[] characters = { "a", "1", "b", "2", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z","!","#","$","%" };
       
        StringBuilder securityKey = new StringBuilder();

        for (int i = 0; i < maxCharacters; i++) {
            int posicao = (int) (Math.random() * characters.length);
            securityKey.append(characters[posicao]);
        }
        
        return securityKey.toString();
	}

	@Override
	public void updateProfessor(Professor professor) {
		dao.updateProfessor(professor);
	}

	@Override
	@Transactional(readOnly = true)
	public Professor buscarPorCodigo(String codigoProfessor) throws Exception {
		Professor professor = new Professor();
        List<Object[]> listProfessor = dao.findByCodigo(codigoProfessor);
        Object[] result = listProfessor.get(0);
        
        if(listProfessor.get(0) == null) {
        	throw new Exception("Professor não cadastrado.");
        } else {
        	professor.setId(Long.parseLong(result[0].toString()));
        	professor.setNome(result[1].toString());
        	professor.setCpf(result[2].toString());
        	professor.setCodigoGeradoEscola(result[3].toString());
        	professor.setEmail(result[4].toString());
        	return professor;
        }
	}

	@Override
	public void updateSenhaProfessor(Professor professor) {
		dao.updateSenhaProfessor(professor);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscarRequisicoes(Long id) {
		return dao.findRequisicoes(id);
	}
}
