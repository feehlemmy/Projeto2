package com.eduq.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduq.model.base.EscolaServiceInterface;
import com.eduq.model.dao.EmailDAO;
import com.eduq.model.dao.EscolaDAO;
import com.eduq.model.entity.DiaLetivo;
import com.eduq.model.entity.Email;
import com.eduq.model.entity.Escola;

@Service
@Transactional(readOnly = false)
public class EscolaService implements EscolaServiceInterface {
	
	@Autowired
	private EscolaDAO dao;
	
	@Autowired
	private EmailDAO emailDao;
	
	@Override
	public void salvar(Escola escola) {
		Email email = new Email();
		
		email.setCodigoMec(escola.getCodigoMec());
		email.setEmailEscola(escola.getEmail());
		email.setNomeEscola(escola.getNome());
		email.setCaracteristica("cadastro");
		
		try {
			dao.save(escola);
			emailDao.save(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editar(Escola escola) {
		dao.update(escola);
	}

	@Override
	public void excluir(Long id) {
		dao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Escola buscarPorId(Long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Escola> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
    public Escola verifyLogin(Integer codigoMec, String senha) throws Exception{
        Escola escola = new Escola();
        List<Escola> escolaLogin = new ArrayList<Escola>();
        
        try {
        	escolaLogin = dao.readByLogin(codigoMec, senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(escolaLogin.get(0) == null) {
        	throw new Exception("Escola não cadastrada.");
        } else {
        	escola = escolaLogin.get(0);
        	return escola;
        }
    }
	
	@Override
	public void completarCadastroEscola(Escola escola) {
		dao.finishCreateEscola(escola);
	}

	@Override
	@Transactional(readOnly = true)
	public Escola buscarPorCodigo(Integer codigoMecEscola) throws Exception {
		Escola escola = new Escola();
        List<Escola> listEscola = new ArrayList<Escola>();
        
        try {
        	listEscola = dao.readByCodigo(codigoMecEscola);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(listEscola.get(0) == null) {
        	throw new Exception("Escola não cadastrada.");
        } else {
        	escola = listEscola.get(0);
        	return escola;
        }
        
	}
	
	@Override
	public void updateSenhaEscola(Escola escola) {
		dao.updateSenhaEscola(escola);
	}
	
	public String gerarSenhaAleatoria(Escola escola) {
		String senhaAleatoria;
		
		senhaAleatoria = generateSecurityKey();
		
		return senhaAleatoria;
	}
	
	private String generateSecurityKey(){
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
	@Transactional(readOnly = true)
	public List<Object[]> buscarRequisicoes(Long id) {
		return dao.findRequisicoes(id);
	}
	
	@Transactional(readOnly = false)
	public void salvarDiasetivos(List<DiaLetivo> dias) {
		dao.saveAllDiasLetivos(dias);
	}
	
	@Transactional(readOnly = true)
	public List<Object[]> getDiasLetivos(Long idEscola){
		return dao.findDiasLetivosByEscola(idEscola);
	}
}
