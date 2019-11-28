\connect eduq_v2

/* INSERTs PESSOA */
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Mario', '111.111.111-11', 'mario@email.com', '12345678', '(35)99999-1111', '37.540-000', 'Rua Prefeitura, 04', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Pedro', '222.222.222-22', 'pedro@email.com', '12345678', '(35)99999-2222', '37.540-000', 'Rua Câmara, 05', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Mateus', '333.333.333-33', 'mateus@email.com', '12345678', '(35)99999-3333', '37.540-000', 'Rua Praça, 06', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Tiago', '444.444.444-44', 'tiago@email.com', '12345678', '(35)99999-4444', '37.540-000', 'Rua Padaria, 07', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Felipe', '555.555.555-55', 'felipe@email.com', '12345678', '(35)99999-5555', '37.540-000', 'Rua Igreja, 08', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('André', '666.666.666-66', 'andre@email.com', '12345678', '(35)99999-6666', '37.540-000', 'Rua Hospital, 09', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Davi', '777.777.777-77', 'davi@email.com', '12345678', '(35)99999-7777', '37.540-000', 'Rua Zoológico, 10', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Samuel', '888.888.888-88', 'samuel@email.com', '12345678', '(35)99999-8888', '37.540-000', 'Rua Hotel, 11', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO pessoa (nome, cpf, email, senha, telefone, cep, endereco, municipio, uf) VALUES ('Tomé', '999.999.999-99', 'tome@email.com', '12345678', '(35)99999-9999', '37.540-000', 'Rua Jardins, 12', 'Santa Rita do Sapucaí', 'MG');

/* INSERTs ESCOLA */
INSERT INTO escola (codigo_mec, escola_nome, email, senha, confirmacao, telefone, diretor, cep, endereco, municipio, uf) VALUES (11111111, 'Escola 1', 'escola1@mail.com', '12345678', '12345678', '(35)3471-1111', 'José', '37540-000', 'Rua Escola 1, 01', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO escola (codigo_mec, escola_nome, email, senha, confirmacao, telefone, diretor, cep, endereco, municipio, uf) VALUES (22222222, 'Escola 2', 'escola2@mail.com', '12345678', '12345678', '(35)3471-2222', 'Maria', '37540-000', 'Rua Escola 2, 02', 'Santa Rita do Sapucaí', 'MG');
INSERT INTO escola (codigo_mec, escola_nome, email, senha, confirmacao, telefone, diretor, cep, endereco, municipio, uf) VALUES (33333333, 'Escola 3', 'escola3@mail.com', '12345678', '12345678', '(35)3471-3333', 'João', '37540-000', 'Rua Escola 3, 03', 'Santa Rita do Sapucaí', 'MG');

/* INSERTs ALUNO */
INSERT INTO aluno (pessoa_id, escola_id, codigo_gerado_escola, matricula, nome_mae, nome_pai, nome_responsavel, cpf_responsavel, data_nascimento, data_matricula, situacao) VALUES (1, 1, '12348765', '01_2019', 'Maria', 'João', 'Maria', '999.999.999-99', '01/12/2009', '15/08/2019', 'Ativo');
INSERT INTO aluno (pessoa_id, escola_id, codigo_gerado_escola, matricula, nome_mae, nome_pai, nome_responsavel, cpf_responsavel, data_nascimento, data_matricula, situacao) VALUES (2, 2, '22223333', '02_2019', 'Ana', 'Joaquim', 'Joaquim', '888.888.888-10', '04/07/2009', '15/08/2019', 'Ativo');
INSERT INTO aluno (pessoa_id, escola_id, codigo_gerado_escola, matricula, nome_mae, nome_pai, nome_responsavel, cpf_responsavel, data_nascimento, data_matricula, situacao) VALUES (3, 3, '95175331', '03_2019', 'Jéssica', 'Gabriel', 'Gabriel', '101.010.101-01', '27/03/2009', '15/08/2019', 'Ativo');

/* INSERTs PROFESSOR */
INSERT INTO professor (pessoa_id, escola_id, codigo_gerado_escola, senha_gerada) VALUES (4, 1, '12354678', 'senha123');
INSERT INTO professor (pessoa_id, escola_id, codigo_gerado_escola, senha_gerada) VALUES (5, 2, '12354678', 'senha123');
INSERT INTO professor (pessoa_id, escola_id, codigo_gerado_escola, senha_gerada) VALUES (6, 3, '12354678', 'senha123');

/* INSERTs RESPONSAVEL */
INSERT INTO responsavel (pessoa_id) VALUES (7);
INSERT INTO responsavel (pessoa_id) VALUES (8);
INSERT INTO responsavel (pessoa_id) VALUES (9);

/* INSERTs RESPONSAVEL_ALUNO */
INSERT INTO responsavel_aluno (responsavel_id, codigo_aluno) VALUES (1, '95175331');
INSERT INTO responsavel_aluno (responsavel_id, codigo_aluno) VALUES (2, '22223333');
INSERT INTO responsavel_aluno (responsavel_id, codigo_aluno) VALUES (3, '12348765');

/* INSERTs EVENTO */
INSERT INTO evento (escola_id, nome_evento, horario_inicio, data_inicio, data_fim, descricao_evento) VALUES (1, 'Reunião de Pais', '19:30', '15/08/2019', '15/08/2019', 'Reunião para entrega dos boletins');
INSERT INTO evento (escola_id, nome_evento, horario_inicio, data_inicio, data_fim, descricao_evento) VALUES (2, 'Viagem de formatura', '06:00', '02/12/2019', '07/12/2019', 'Viagem de fim de ano para formatura');
INSERT INTO evento (escola_id, nome_evento, horario_inicio, data_inicio, data_fim, descricao_evento) VALUES (3, 'Cantata de Natal', '19:30', '13/12/2019', '13/12/2019', 'Apresentação da cantata de natal');

/* INSERTs COMPONENTE */
INSERT INTO componente (nome, tipo_avaliacao, media_aprovacao, carga_horaria, escola_id) VALUES ('Matemática', 'Prova', '60', '240hrs', 1);
INSERT INTO componente (nome, tipo_avaliacao, media_aprovacao, carga_horaria, escola_id) VALUES ('Português', 'Prova', '60', '240hrs', 1);
INSERT INTO componente (nome, tipo_avaliacao, media_aprovacao, carga_horaria, escola_id) VALUES ('História', 'Prova e trabalho', '60', '180hrs', 1);

/* INSERTs CLASSE */
INSERT INTO classe (nome, turno, ano_classe, ano_letivo, escola_id, professor_id, descricao) VALUES ('Esmeralda 1', 'Matituno', '5 ano', '2019', 1, 1, 'Primeira turma de quinto ano');
INSERT INTO classe (nome, turno, ano_classe, ano_letivo, escola_id, professor_id, descricao) VALUES ('Esmeralda 2', 'Matituno', '5 ano', '2019', 1, 2, 'Segunda turma de quinto ano');
INSERT INTO classe (nome, turno, ano_classe, ano_letivo, escola_id, professor_id, descricao) VALUES ('Esmeralda 3', 'Vespertino', '5 ano', '2019', 1, 3, 'Terceira turma de quinto ano');

/* INSERTs ALUNO_CLASSE */
INSERT INTO aluno_classe (classe_id, aluno_id) VALUES (1, 1);
INSERT INTO aluno_classe (classe_id, aluno_id) VALUES (1, 2);
INSERT INTO aluno_classe (classe_id, aluno_id) VALUES (1, 3);

/* INSERTs CHAMADA */
INSERT INTO chamada (data_chamada, classe_id, escola_id) VALUES ('15/08/2019', 1, 1);
INSERT INTO chamada (data_chamada, classe_id, escola_id) VALUES ('14/08/2019', 1, 1);
INSERT INTO chamada (data_chamada, classe_id, escola_id) VALUES ('13/08/2019', 1, 1);

/* INSERTs ALUNO_CHAMADA */
INSERT INTO aluno_chamada (aluno_id, chamada_id, presenca) VALUES (1, 1, 1);
INSERT INTO aluno_chamada (aluno_id, chamada_id, presenca) VALUES (2, 1, 0);
INSERT INTO aluno_chamada (aluno_id, chamada_id, presenca) VALUES (2, 1, 1);

/* INSERTs AVISO */
INSERT INTO aviso (ano_classe, conteudo_aviso, data_aviso, classe_id, escola_id, tipo_aviso, titulo_aviso) VALUES ('5 ano', 'Prova de matemática', '15/08/2019', 1, 1, 'Prova', 'Prova de Matamática agendada');
INSERT INTO aviso (ano_classe, conteudo_aviso, data_aviso, classe_id, escola_id, tipo_aviso, titulo_aviso) VALUES ('4 ano', 'Prova de matemática', '16/08/2019', 1, 2, 'Prova', 'Prova de Matamática agendada');
INSERT INTO aviso (ano_classe, conteudo_aviso, data_aviso, classe_id, escola_id, tipo_aviso, titulo_aviso) VALUES ('3 ano', 'Prova de matemática', '17/08/2019', 1, 3, 'Prova', 'Prova de Matamática agendada');

/* INSERTs REQUISICAO */
INSERT INTO requisicao (escola_id, requisicao_responsavel_id, requisicao_aluno_id, tipo_requisicao, observacao) VALUES (1, 1, 1, 'Liberacao', 'Liberação do meu filho mais cedo');
INSERT INTO requisicao (escola_id, requisicao_responsavel_id, requisicao_aluno_id, tipo_requisicao, observacao) VALUES (1, 2, 2, 'Autorizacao', 'Autorização para meu filho ser liberado da aula de artes');
INSERT INTO requisicao (escola_id, requisicao_responsavel_id, requisicao_aluno_id, tipo_requisicao, observacao) VALUES (1, 3, 3, 'Declaracao', 'Declaração de matrícula para o meu filho');

/* INSERTs BOLETIM */
INSERT INTO boletim (id_classe_aluno, bimestre, data_boletim) VALUES (1, '2', '15/06/2019');
INSERT INTO boletim (id_classe_aluno, bimestre, data_boletim) VALUES (2, '2', '15/06/2019');
INSERT INTO boletim (id_classe_aluno, bimestre, data_boletim) VALUES (3, '2', '15/06/2019');

/* INSERTs BOLETIM_COMPONENTE */
INSERT INTO boletim_componente (componente_id, boletim_id, faltas, conceito) VALUES (1, 1, 1, '15');
INSERT INTO boletim_componente (componente_id, boletim_id, faltas, conceito) VALUES (2, 1, 1, '18');
INSERT INTO boletim_componente (componente_id, boletim_id, faltas, conceito) VALUES (3, 1, 1, '17');

select sleep(10);

/* UPDATEs ESCOLA */
UPDATE escola SET escola_nome = 'Escola Municipal 01', senha = '87654321', confirmacao = '87654321' WHERE id = 1;
UPDATE escola SET diretor = 'Benedito', senha = '123789456', confirmacao = '123789456' WHERE id = 2;
UPDATE escola SET telefone = '(35)3471-0000', senha = '789456123', confirmacao = '789456123' WHERE id = 3;

/* UPDATEs PESSOA */
UPDATE pessoa SET cep = '37.545-000', municipio = 'Cachoeira de Minas' WHERE id = 1;
UPDATE pessoa SET cep = '37.545-000', municipio = 'Cachoeira de Minas' WHERE id = 2;
UPDATE pessoa SET cep = '37.545-000', municipio = 'Cachoeira de Minas' WHERE id = 3;
UPDATE pessoa SET cep = '37.550-000', municipio = 'Pouso Alegre' WHERE id = 4;
UPDATE pessoa SET cep = '37.550-000', municipio = 'Pouso Alegre' WHERE id = 5;
UPDATE pessoa SET cep = '37.550-000', municipio = 'Pouso Alegre' WHERE id = 6;
UPDATE pessoa SET nome = 'Judas Tadeu', senha = '123987456' WHERE id = 7;
UPDATE pessoa SET nome = 'Levy', senha = '98765432' WHERE id = 8;
UPDATE pessoa SET nome = 'Abraão', senha = '15975314' WHERE id = 9;

/* UPDATEs ALUNO */
UPDATE aluno SET matricula = '2019-01', nome_pai = 'Altair' WHERE id = 1;
UPDATE aluno SET matricula = '00-2019', nome_mae = 'Eliana' WHERE id = 2;
UPDATE aluno SET data_nascimento = '19/08/2018', nome_responsavel = 'Jéssica' WHERE id = 3;

/* UPDATEs PROFESSOR */
UPDATE professor SET senha_gerada = '15975364' WHERE id = 1;
UPDATE professor SET senha_gerada = '45678912' WHERE id = 2;
UPDATE professor SET senha_gerada = '12345678' WHERE id = 3;

/* UPDATEs RESPONSAVEL */
UPDATE responsavel SET pessoa_id = 9 WHERE id = 1;
UPDATE responsavel SET pessoa_id = 8 WHERE id = 2;
UPDATE responsavel SET pessoa_id = 7 WHERE id = 3;

/* UPDATEs RESPONSAVEL_ALUNO */
UPDATE responsavel_aluno SET codigo_aluno = '12348765' WHERE responsavel_id = 1;
UPDATE responsavel_aluno SET codigo_aluno = '95175331' WHERE responsavel_id = 2;
UPDATE responsavel_aluno SET codigo_aluno = '22223333' WHERE responsavel_id = 3;

/* UPDATEs EVENTO */
UPDATE evento SET horario_inicio = '18:30' WHERE id = 1;
UPDATE evento SET nome_evento = 'Viagem de fim de ano' WHERE id = 2;
UPDATE evento SET data_inicio = '06/12/2019', data_fim = '06/12/2019' WHERE id = 3;

/* UPDATEs COMPONENTE */
UPDATE componente SET tipo_avaliacao = 'Prova e trabalho' WHERE id = 1;
UPDATE componente SET carga_horaria = '180hrs' WHERE id = 2;
UPDATE componente SET nome = 'Geografia' WHERE id = 3;

/* UPDATEs CLASSE */
UPDATE classe SET nome = 'Diamente 1', ano_classe = '3 ano' WHERE id = 1;
UPDATE classe SET turno = 'Vespertino', professor_id = 3 WHERE id = 2;
UPDATE classe SET nome = 'Esmeralda 1', professor_id = 2 WHERE id = 3;

/* UPDATEs ALUNO_CLASSE */
UPDATE aluno_classe SET classe_id = 2 WHERE id = 1;
UPDATE aluno_classe SET classe_id = 2 WHERE id = 2;
UPDATE aluno_classe SET classe_id = 3 WHERE id = 3;

/* UPDATEs CHAMADA */
UPDATE chamada SET data_chamada = '16/08/2019' WHERE id = 1;
UPDATE chamada SET data_chamada = '17/08/2019' WHERE id = 2;
UPDATE chamada SET data_chamada = '18/08/2019' WHERE id = 3;

/* UPDATEs ALUNO_CHAMADA */
UPDATE aluno_chamada SET presenca = 0 WHERE id = 1;
UPDATE aluno_chamada SET presenca = 1 WHERE id = 2;
UPDATE aluno_chamada SET presenca = 0 WHERE id = 3;

/* UPDATEs AVISO */
UPDATE aviso SET data_aviso = '20/08/2019' WHERE id = 1;
UPDATE aviso SET ano_classe = '5 ano' WHERE id = 2;
UPDATE aviso SET data_aviso = '21/08/2019' WHERE id = 3;

/* UPDATEs REQUISICAO */
UPDATE requisicao SET tipo_requisicao = 'Declaracao', observacao = 'Declaração de matrícula para o meu filho' WHERE id = 1;
UPDATE requisicao SET tipo_requisicao = 'Liberacao', observacao = 'Liberação do meu filho mais cedo' WHERE id = 2;
UPDATE requisicao SET tipo_requisicao = 'Autorizacao', observacao = 'Autorização para meu filho ser liberado da aula de artes' WHERE id = 3;

/* UPDATEs BOLETIM */
UPDATE boletim SET bimestre = '3', data_boletim = '15/08/2019' WHERE id = 1;
UPDATE boletim SET bimestre = '3', data_boletim = '15/08/2019' WHERE id = 2;
UPDATE boletim SET bimestre = '3', data_boletim = '15/08/2019' WHERE id = 3;

/* UPDATEs BOLETIM_COMPONENTE */
UPDATE boletim_componente SET faltas = 2 WHERE id = 1;
UPDATE boletim_componente SET faltas = 2 WHERE id = 2;
UPDATE boletim_componente SET faltas = 4 WHERE id = 3;

select sleep(10) ;

/* DELETEs BOLETIM_COMPONENTE */
DELETE FROM boletim_componente WHERE id = 1;
DELETE FROM boletim_componente WHERE id = 2;
DELETE FROM boletim_componente WHERE id = 3;

/* DELETEs BOLETIM */
DELETE FROM boletim WHERE id = 1;
DELETE FROM boletim WHERE id = 2;
DELETE FROM boletim WHERE id = 3;

/* DELETEs REQUISICAO */
DELETE FROM requisicao WHERE id = 1;
DELETE FROM requisicao WHERE id = 2;
DELETE FROM requisicao WHERE id = 3;

/* DELETEs AVISO */
DELETE FROM aviso WHERE id = 1;
DELETE FROM aviso WHERE id = 2;
DELETE FROM aviso WHERE id = 3;

/* DELETEs ALUNO_CHAMADA */
DELETE FROM aluno_chamada WHERE id = 1;
DELETE FROM aluno_chamada WHERE id = 2;
DELETE FROM aluno_chamada WHERE id = 3;

/* DELETEs CHAMADA */
DELETE FROM chamada WHERE id = 1;
DELETE FROM chamada WHERE id = 2;
DELETE FROM chamada WHERE id = 3;

/* DELETEs ALUNO_CLASSE */
DELETE FROM aluno_classe WHERE id = 1;
DELETE FROM aluno_classe WHERE id = 2;
DELETE FROM aluno_classe WHERE id = 3;

/* DELETEs CLASSE */
DELETE FROM classe WHERE id = 1;
DELETE FROM classe WHERE id = 2;
DELETE FROM classe WHERE id = 3;

/* DELETEs COMPONENTE */
DELETE FROM componente WHERE id = 1;
DELETE FROM componente WHERE id = 2;
DELETE FROM componente WHERE id = 3;

/* DELETEs EVENTO */
DELETE FROM evento WHERE id = 1;
DELETE FROM evento WHERE id = 2;
DELETE FROM evento WHERE id = 3;

/* DELETEs RESPONSAVEL_ALUNO */
DELETE FROM responsavel_aluno WHERE responsavel_id = 1;
DELETE FROM responsavel_aluno WHERE responsavel_id = 2;
DELETE FROM responsavel_aluno WHERE responsavel_id = 3;

/* DELETEs RESPONSAVEL */
DELETE FROM responsavel WHERE id = 1;
DELETE FROM responsavel WHERE id = 2;
DELETE FROM responsavel WHERE id = 3;

/* DELETEs PROFESSOR */
DELETE FROM professor WHERE id = 1;
DELETE FROM professor WHERE id = 2;
DELETE FROM professor WHERE id = 3;

/* DELETEs ALUNO */
DELETE FROM aluno WHERE id = 1;
DELETE FROM aluno WHERE id = 2;
DELETE FROM aluno WHERE id = 3;

/* DELETEs PESSOA */
DELETE FROM pessoa WHERE id = 1;
DELETE FROM pessoa WHERE id = 2;
DELETE FROM pessoa WHERE id = 3;
DELETE FROM pessoa WHERE id = 4;
DELETE FROM pessoa WHERE id = 5;
DELETE FROM pessoa WHERE id = 6;
DELETE FROM pessoa WHERE id = 7;
DELETE FROM pessoa WHERE id = 8;
DELETE FROM pessoa WHERE id = 9;

/* DELETEs ESCOLA */
DELETE FROM escola WHERE id = 1;
DELETE FROM escola WHERE id = 2;
DELETE FROM escola WHERE id = 3;