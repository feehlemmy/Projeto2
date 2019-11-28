package com.eduq.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.eduq.model.base.AlunoInterface;
import com.eduq.model.base.BaseDAO;
import com.eduq.model.entity.Aluno;

@Repository
public class AlunoDAO extends BaseDAO<Aluno, Long> implements AlunoInterface {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public void updateAluno(Aluno aluno) {
		Query query = manager.createNativeQuery("UPDATE aluno "
				+ "SET data_nascimento = ?, data_matricula = ?, cpf_responsavel = ?, matricula = ?, nome_mae = ?, nome_pai = ?, nome_responsavel = ?, situacao = ?, justificativa = ? "
				+ "WHERE id = ?")
				.setParameter(1, aluno.getDataNascimento())
				.setParameter(2, aluno.getDataMatricula())
				.setParameter(3, aluno.getCpfResponsavel())
				.setParameter(4, aluno.getMatricula())
				.setParameter(5, aluno.getNomeMae())
				.setParameter(6, aluno.getNomePai())
				.setParameter(7, aluno.getNomeResponsavel())
				.setParameter(8, aluno.getSituacao())
				.setParameter(9, aluno.getJustificativa())
				.setParameter(10, aluno.getId());
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> findByEscola(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT "
				+ "A.id, P.nome, P.cpf, A.data_nascimento, A.data_matricula, A.cpf_responsavel, A.matricula, A.nome_mae, A.nome_pai, A.nome_responsavel, A.situacao, A.justificativa, A.codigo_gerado_escola "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P ON P.id = A.pessoa_id "
				+ "WHERE A.escola_id = ?")
				.setParameter(1, escolaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> findByNoClass(Long escolaId) {
		Query query = manager.createNativeQuery("SELECT "
				+ "A.id, P.nome, P.cpf, A.data_nascimento, A.data_matricula, A.cpf_responsavel, A.matricula, A.nome_mae, A.nome_pai, A.nome_responsavel, A.situacao, A.justificativa, A.codigo_gerado_escola "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P ON P.id = A.pessoa_id "
				+ "LEFT JOIN aluno_classe AC ON AC.aluno_id = A.id "
				+ "WHERE A.escola_id = ? and AC.aluno_id is null").setParameter(1, escolaId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Aluno> findByClass(Long idClasse) {
		Query query = manager.createNativeQuery("SELECT A.id, P.nome, A.matricula, A.situacao "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P ON P.id = A.pessoa_id "
				+ "INNER JOIN aluno_classe AC ON AC.aluno_id = A.id "
				+ "WHERE AC.classe_id = ?").setParameter(1, idClasse);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> searchById(Long id) {
		Query query = manager.createNativeQuery("SELECT P.nome, P.cpf, A.id, A.codigo_gerado_escola, A.cpf_responsavel, A.data_matricula, A.data_nascimento, A.matricula, A.nome_mae, A.nome_pai, A.nome_responsavel, A.situacao, A.pessoa_id "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "WHERE A.id = ?")
				.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	public void excludeAluno(Long id) {
		Query query = manager.createNativeQuery("DELETE FROM aluno A WHERE A.id = ?").setParameter(1, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByClasseBoletim(Long id) {
		Query query = manager.createNativeQuery("SELECT A.id, P.nome, A.matricula, A.situacao "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P ON P.id = A.pessoa_id "
				+ "INNER JOIN aluno_classe AC ON AC.aluno_id = A.id "
				+ "WHERE AC.classe_id = ?").setParameter(1, id);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> findByName(String nome, Long id) {
		Query query = manager.createNativeQuery("SELECT "
				+ "A.id, P.nome, P.cpf, A.data_nascimento, A.data_matricula, A.cpf_responsavel, A.matricula, A.nome_mae, A.nome_pai, A.nome_responsavel, A.situacao, A.justificativa, A.codigo_gerado_escola "
				+ "FROM aluno A "
				+ "INNER JOIN pessoa P ON P.id = A.pessoa_id "
				+ "WHERE A.escola_id = ? AND P.nome ~ ?")
				.setParameter(1, id)
				.setParameter(2, nome);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> findByProfessor(Long id) {
		Query query = manager.createNativeQuery("SELECT A.id, P.nome, C.nome as classe_nome FROM aluno A "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "INNER JOIN aluno_classe AC on AC.aluno_id = A.id "
				+ "INNER JOIN classe C on C.id = AC.classe_id "
				+ "INNER JOIN professor PR on PR.id = C.professor_id "
				+ "WHERE PR.id = ? ORDER BY A.id")
				.setParameter(1, id);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aluno> findByNameProfessor(String nome, Long id) {
		Query query = manager.createNativeQuery("SELECT A.id, P.nome, C.nome as classe_nome FROM aluno A "
				+ "INNER JOIN pessoa P on P.id = A.pessoa_id "
				+ "INNER JOIN aluno_classe AC on AC.aluno_id = A.id "
				+ "INNER JOIN classe C on C.id = AC.classe_id "
				+ "INNER JOIN professor PR on PR.id = C.professor_id "
				+ "WHERE PR.id = ? AND P.nome ~ ? ORDER BY A.id")
				.setParameter(1, id)
				.setParameter(2, nome);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findDataResponsavel(String codigoAluno) {
		Query query = manager.createNativeQuery("SELECT P.nome, P.telefone, P.email, P.endereco, P.municipio FROM pessoa P "
				+ "INNER JOIN responsavel R ON R.pessoa_id = P.id "
				+ "INNER JOIN responsavel_aluno RA ON RA.responsavel_id = R.id "
				+ "WHERE RA.codigo_aluno = ?")
				.setParameter(1, codigoAluno);
		return query.getResultList();
	}
}
