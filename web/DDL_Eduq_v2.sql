CREATE DATABASE eduq_v2 WITH ENCODING = 'UTF8';

\connect eduq_v2

CREATE TABLE pessoa (
    id serial PRIMARY KEY NOT NULL,
    cep character varying(10),
    cpf character varying(15) NOT NULL,
    email character varying(50),
    endereco character varying(100),
    municipio character varying(50),
    nome character varying(50) NOT NULL,
    senha character varying(50),
    telefone character varying(15),
    uf character varying(2)
);

CREATE TABLE escola (
    id serial PRIMARY KEY NOT NULL,
    cep character varying(10),
    codigo_mec integer UNIQUE NOT NULL,
    confirmacao character varying(50) NOT NULL,
    diretor character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    endereco character varying(100),
    logo character varying(100),
    municipio character varying(50),
    escola_nome character varying(150) NOT NULL,
    senha character varying(50) NOT NULL,
    telefone character varying(15),
    uf character varying(2)
);

CREATE TABLE aluno (
    id serial PRIMARY KEY NOT NULL,
    codigo_gerado_escola character varying(8) UNIQUE NOT NULL,
    escola_id integer NOT NULL,
    cpf_responsavel character varying(15) NOT NULL,
    data_matricula character varying(10) NOT NULL,
    data_nascimento character varying(10) NOT NULL,
    justificativa character varying(255),
    matricula character varying(10) NOT NULL,
    nome_mae character varying(50) NOT NULL,
    nome_pai character varying(50),
    nome_responsavel character varying(50),
    situacao character varying(255) NOT NULL,
    pessoa_id integer NOT NULL
);

ALTER TABLE aluno ADD CONSTRAINT fk_pessoa_id_aluno FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE aluno ADD CONSTRAINT fk_escola_id_aluno FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE professor (
    id serial PRIMARY KEY NOT NULL,
    codigo_gerado_escola character varying(8) NOT NULL,
    escola_id integer NOT NULL,
    senha_gerada character varying(255) NOT NULL,
    pessoa_id integer NOT NULL
);

ALTER TABLE professor ADD CONSTRAINT fk_pessoa_id_professor FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE professor ADD CONSTRAINT fk_escola_id_professor FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE responsavel (
    id serial PRIMARY KEY NOT NULL,
    pessoa_id integer NOT NULL,
    foto character varying(255)
);

ALTER TABLE responsavel ADD CONSTRAINT fk_pessoa_id_responsavel FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE responsavel_aluno (
    responsavel_id integer NOT NULL,
    codigo_aluno character varying(8) NOT NULL
);

ALTER TABLE responsavel_aluno ADD CONSTRAINT fk_responsavel_id_aluno FOREIGN KEY (responsavel_id) REFERENCES responsavel(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE responsavel_aluno ADD CONSTRAINT fk_codigo_aluno_responsavel_aluno FOREIGN KEY (codigo_aluno) REFERENCES aluno(codigo_gerado_escola) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE evento (
    id serial PRIMARY KEY NOT NULL,
    escola_id integer NOT NULL,
    data_fim character varying(10),
    data_inicio character varying(10) NOT NULL,
    descricao_evento character varying(255) NOT NULL,
    horario_inicio character varying(5) NOT NULL,
    nome_evento character varying(50) NOT NULL
);

ALTER TABLE evento ADD CONSTRAINT fk_escola_id_evento FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE componente (
    id serial PRIMARY KEY NOT NULL,
    carga_horaria character varying(6) NOT NULL,
    escola_id integer NOT NULL,
    nome character varying(50) NOT NULL,
    tipo_avaliacao character varying(16) NOT NULL,
    media_aprovacao character varying(3) NOT NULL
);

ALTER TABLE componente ADD CONSTRAINT fk_escola_id_componente FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE classe (
    id serial PRIMARY KEY NOT NULL,
    ano_classe character varying(8) NOT NULL,
    ano_letivo character varying(4) NOT NULL,
    escola_id integer NOT NULL,
    professor_id integer,
    descricao character varying(255),
    nome character varying(50) NOT NULL,
    turno character varying(15) NOT NULL
);

ALTER TABLE classe ADD CONSTRAINT fk_escola_id_classe FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE classe ADD CONSTRAINT fk_professor_id_classe FOREIGN KEY (professor_id) REFERENCES professor(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE aluno_classe (
    id serial PRIMARY KEY NOT NULL,
    classe_id integer NOT NULL,
    aluno_id integer NOT NULL
);

ALTER TABLE aluno_classe ADD CONSTRAINT fk_classe_id_alunos_classe FOREIGN KEY (classe_id) REFERENCES classe(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE aluno_classe ADD CONSTRAINT fk_aluno_id_classe FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE chamada (
    id serial PRIMARY KEY NOT NULL,
    data_chamada character varying(10) NOT NULL,
    classe_id integer NOT NULL,
    escola_id integer,
    realizada character varying(3) DEFAULT('Não')
);

ALTER TABLE chamada ADD CONSTRAINT fk_escola_id_chamada FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE chamada ADD CONSTRAINT fk_classe_id_chamada FOREIGN KEY (classe_id) REFERENCES classe(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE aluno_chamada (
    id serial PRIMARY KEY NOT NULL,
    aluno_id integer NOT NULL,
    chamada_id integer NOT NULL,
    status character varying(8) DEFAULT('Presente'),
    falta integer NOT NULL
);

ALTER TABLE aluno_chamada ADD CONSTRAINT fk_aluno_id_classe FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE aluno_chamada ADD CONSTRAINT fk_chamada_id_classe FOREIGN KEY (chamada_id) REFERENCES chamada(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE aviso (
    id serial PRIMARY KEY NOT NULL,
    ano_classe character varying(8) NOT NULL,
    conteudo_aviso character varying(255),
    data_aviso character varying(10),
    classe_id integer,
    escola_id integer NOT NULL,
    tipo_aviso character varying(25),
    titulo_aviso character varying(50) NOT NULL
);

ALTER TABLE aviso ADD CONSTRAINT fk_escola_id_aviso FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE aviso ADD CONSTRAINT fk_classe_id_aviso FOREIGN KEY (classe_id) REFERENCES classe(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE requisicao (
    id serial PRIMARY KEY NOT NULL,
    escola_id integer NOT NULL,
    requisicao_responsavel_id integer NOT NULL,
    requisicao_aluno_id integer NOT NULL,
    tipo_requisicao character varying(100) NOT NULL,
    observacao character varying(255)
);

ALTER TABLE requisicao ADD CONSTRAINT fk_escola_id_requisicao FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE requisicao ADD CONSTRAINT fk_responsavel_id_requisicao FOREIGN KEY (requisicao_responsavel_id) REFERENCES responsavel(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE requisicao ADD CONSTRAINT fk_aluno_id_requisicao FOREIGN KEY (requisicao_aluno_id) REFERENCES aluno(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE boletim (
    id serial PRIMARY KEY NOT NULL,
    classe_id integer NOT NULL,
    escola_id integer NOT NULL,
    bimestre character varying(3) NOT NULL,
    situacao character varying(20) NOT NULL DEFAULT('Cursando'),
    individual_criado character varying(3) NOT NULL DEFAULT('Não'),
    observacao character varying(255),
    data_boletim character varying(10) NOT NULL
);

ALTER TABLE boletim ADD CONSTRAINT fk_id_classe_boletim FOREIGN KEY (classe_id) REFERENCES classe(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE boletim ADD CONSTRAINT fk_id_escola_boletim FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE boletim_componente (
    id serial PRIMARY KEY NOT NULL,
    componente_id integer NOT NULL,
    boletim_id integer NOT NULL
);

ALTER TABLE boletim_componente ADD CONSTRAINT fk_id_componente FOREIGN KEY (componente_id) REFERENCES componente(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE boletim_componente ADD CONSTRAINT fk_id_boletim FOREIGN KEY (boletim_id) REFERENCES boletim(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE boletim_aluno (
    id serial PRIMARY KEY NOT NULL,
    boletim_id integer NOT NULL,
    aluno_id integer NOT NULL
);

ALTER TABLE boletim_aluno ADD CONSTRAINT fk_id_boletim_aluno FOREIGN KEY (boletim_id) REFERENCES boletim(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE boletim_aluno ADD CONSTRAINT fk_id_boletim_id_aluno FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE boletim_aluno_componente (
	id serial PRIMARY KEY NOT NULL,
	boletim_aluno_id integer NOT NULL,
	boletim_componente_id integer NOT NULL,
    faltas integer NOT NULL DEFAULT(0),
    conceito character varying(4)
);

ALTER TABLE boletim_aluno_componente ADD CONSTRAINT fk_boletim_aluno_componente_id FOREIGN KEY (boletim_aluno_id) REFERENCES boletim_aluno(id) ON UPDATE CASCADE ON DELETE NO ACTION;
ALTER TABLE boletim_aluno_componente ADD CONSTRAINT fk_boletim_componente_aluno_id FOREIGN KEY (boletim_componente_id) REFERENCES boletim_componente(id) ON UPDATE CASCADE ON DELETE NO ACTION;

CREATE TABLE email (
    id serial PRIMARY KEY NOT NULL,
    caracteristica character varying(30),
    codigo_mec_escola integer,
    codigo_professor character varying(8),
    descricao_duvida character varying(255),
    email_escola character varying(100),
    email_professor character varying(100),
    enviado character varying(3),
    nome_escola character varying(150),
    nome_professor character varying(150),
    senha_escola character varying(20),
    senha_professor character varying(20),
    titulo_duvida character varying(100)
);

CREATE TABLE dia_letivo(
	id serial PRIMARY KEY NOT NULL,
	escola_id integer NOT NULL,
	dia character varying(11) NOT NULL
);

ALTER TABLE dia_letivo ADD CONSTRAINT fk_escola_id_dia_letivo FOREIGN KEY (escola_id) REFERENCES escola(id) ON UPDATE CASCADE ON DELETE NO ACTION;