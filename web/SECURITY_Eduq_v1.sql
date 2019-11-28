\connect eduq_v2

/* USUÁRIOS */
CREATE ROLE responsavel WITH ENCRYPTED PASSWORD '01responsavel@Eduq';
CREATE ROLE professor WITH ENCRYPTED PASSWORD '02professor@Eduq';
CREATE ROLE escola WITH ENCRYPTED PASSWORD '03escola@Eduq';

/* GRUPO DE USUÁRIOS */
CREATE GROUP usuarios_eduq;
ALTER GROUP usuarios_eduq ADD USER responsavel;
ALTER GROUP usuarios_eduq ADD USER professor;
ALTER GROUP usuarios_eduq ADD USER escola;

/* PERMISSÔES */
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM responsavel;
REVOKE INSERT, DELETE ON professor FROM professor;
REVOKE INSERT, UPDATE, DELETE ON aluno, escola, classe, componente, responsavel FROM professor;
REVOKE CREATE ON DATABASE eduq_v2 FROM usuarios_eduq;

GRANT SELECT ON View_Responsavel_Aluno, View_Responsavel_Boletim, View_Responsavel_Chamada TO responsavel;
GRANT SELECT, INSERT, UPDATE ON aviso, requisicao TO responsavel;
GRANT SELECT, UPDATE ON pessoa TO responsavel;

/* RESPONSÁVEL */
/* View para acessar dados do aluno, escola e classe */
CREATE OR REPLACE VIEW View_Responsavel_Aluno AS
SELECT
    A.matricula,
    A.codigo_gerado_escola,
    A.situacao,
    P.nome AS aluno_nome,
    E.escola_nome,
    E.telefone,
    E.endereco,
    E.municipio,
    E.UF,
    C.nome AS classe_nome,
    C.turno,
    C.ano_classe
FROM aluno A
    INNER JOIN pessoa P
        ON P.id = A.pessoa_id
    INNER JOIN escola E
        ON E.id = A.escola_id
    INNER JOIN aluno_classe CA
        ON CA.aluno_id = A.id
    INNER JOIN classe C
        ON C.id = CA.classe_id;

/* View para acessar dados da chamada e presença */
CREATE OR REPLACE VIEW View_Responsavel_Chamada AS
SELECT
    C.nome AS classe_nome,
    C.turno,
    C.ano_classe,
    CH.data_chamada,
    AC.presenca,
    A.id AS id_aluno
FROM chamada CH
    INNER JOIN aluno_chamada AC
        ON AC.chamada_id = CH.id
    INNER JOIN aluno A
        ON A.id = AC.aluno_id
    INNER JOIN classe C
        ON C.id = CH.classe_id;

/* View para acessar dados do boletim */
CREATE OR REPLACE VIEW View_Responsavel_Boletim AS
SELECT
    C.nome AS componente_nome,
    B.data_boletim,
    B.bimestre,
    B.situacao,
    B.observacao,
    BC.conceito,
    BC.faltas
FROM boletim B
    INNER JOIN aluno_classe AC
        ON AC.id = B.id_classe_aluno
    INNER JOIN boletim_componente BC
        ON BC.boletim_id = B.id
    INNER JOIN componente C
        ON C.id = BC.componente_id;