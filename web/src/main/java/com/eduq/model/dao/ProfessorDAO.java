package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.BaseDAO;
import com.eduq.model.base.ProfessorInterface;
import com.eduq.model.entity.Pessoa;
import com.eduq.model.entity.Professor;

@Repository
public class ProfessorDAO extends BaseDAO<Professor, Long> implements ProfessorInterface {
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> readByLogin(String codigoGerado, String senha) {
		Query query = manager.createNativeQuery("SELECT PR.id, P.nome, P.cpf, PR.pessoa_id, P.email, E.id as escola_id, PR.codigo_gerado_escola, PR.senha_gerada FROM professor PR " + 
				"INNER JOIN pessoa P ON P.id = PR.pessoa_id " +
				"INNER JOIN escola E ON E.id = PR.escola_id " + 
				"WHERE PR.codigo_gerado_escola = ? and PR.senha_gerada = ?")
				.setParameter(1, codigoGerado)
				.setParameter(2, senha);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findByProfessor(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT PR.pessoa_id, P.nome, PR.id FROM professor PR " + 
				"JOIN pessoa P ON P.id = PR.pessoa_id " + 
				"JOIN escola E ON E.id = PR.escola_id " +
				"WHERE E.id = ?")
				.setParameter(1, escolaId);
		return query.getResultList();
	}

	@Override
	public void updateProfessor(Professor professor) {
		Query query = manager.createNativeQuery("UPDATE pessoa SET cpf=?, nome=?, email=?, telefone=?, endereco=?, cep=?, municipio=?, uf=? WHERE id = ?")
				.setParameter(1, professor.getCpf())
				.setParameter(2, professor.getNome())
				.setParameter(3, professor.getEmail())
				.setParameter(4, professor.getTelefone())
				.setParameter(5, professor.getEndereco())
				.setParameter(6, professor.getCep())
				.setParameter(7, professor.getMunicipio())
				.setParameter(8, professor.getUf())
				.setParameter(9, professor.getId());
		query.executeUpdate();	
	}

	@Override
	public void updateSenhaProfessor(Professor professor) {
		Query query = manager.createNativeQuery("UPDATE professor SET senha_gerada=? WHERE codigo_gerado_escola = ?")
				.setParameter(1, professor.getSenhaGerada())
				.setParameter(2, professor.getCodigoGeradoEscola());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByCodigo(String codigoGerado) {
		Query query = manager.createNativeQuery("SELECT PR.pessoa_id, P.nome, P.cpf, PR.codigo_gerado_escola, P.email FROM professor PR " + 
				"JOIN pessoa P on P.id = PR.pessoa_id " + 
				"WHERE PR.codigo_gerado_escola = ?")
				.setParameter(1, codigoGerado);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findRequisicoes(Long id) {
		Query query = manager.createNativeQuery("SELECT R.id, P.nome, R.tipo_requisicao, R.requisicao_aluno_id, R.observacao FROM requisicao R "
				+ "INNER JOIN responsavel RP ON RP.id = R.requisicao_responsavel_id "
				+ "INNER JOIN pessoa P ON P.id = RP.pessoa_id "
				+ "INNER JOIN aluno A on A.id = R.requisicao_aluno_id "
				+ "INNER JOIN aluno_classe AC on AC.aluno_id = A.id "
				+ "INNER JOIN classe C ON C.id = AC.classe_id "
				+ "WHERE C.professor_id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}
}
